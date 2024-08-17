package com.hana.amerikorea.asset.service;

import com.hana.amerikorea.api.service.ApiCompromisedService;
import com.hana.amerikorea.asset.domain.StockInfo;
import com.hana.amerikorea.asset.dto.request.AssetRequest;
import com.hana.amerikorea.asset.dto.response.AssetResponse;
import com.hana.amerikorea.asset.domain.Asset;
import com.hana.amerikorea.asset.domain.Dividend;
import com.hana.amerikorea.asset.repository.AssetRepository;
import com.hana.amerikorea.asset.repository.DividendRepository;
import com.hana.amerikorea.asset.repository.StockInfoRepository;
import com.hana.amerikorea.member.domain.Member;
import com.hana.amerikorea.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class AssetServiceImpl implements AssetService {

    @Autowired
    private AssetRepository assetRepo;

    @Autowired
    private DividendRepository dividendRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StockInfoRepository stockInfoRepository;

    @Autowired
    private TradingViewService tradingViewService;

    @Autowired
    private ApiCompromisedService apiCompromisedService;


    @Override
    public List<AssetResponse> getAllAssets(String email) {

        List<AssetResponse> assetResponseList = new ArrayList<>();

        assetRepo.findByMemberEmail(email,Sort.by(Sort.Direction.DESC, "quantity")).forEach(asset -> {
            try {
                System.out.println("쿼리 파라미터 : " + asset.getStockInfo().getStockName() + asset.getQuantity() + asset.getPurchasePrice());
                AssetResponse apiResponse = apiCompromisedService.createAssetDTO(
                        asset.getStockInfo().getStockName(),
                        asset.getQuantity(),
                        asset.getPurchasePrice(),
                        true
                );

                Map<LocalDate, Double> dividends = dividendRepository.findByStockInfoTickerSymbol(asset.getStockInfo().getTickerSymbol()).stream()
                        .collect(Collectors.toMap(
                                Dividend::getDividendDate,
                                Dividend::getDividendAmount
                        ));

                apiResponse.setDividends(dividends);

                assetResponseList.add(apiResponse);

            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException("Error fetching asset data", e);
            }
        });

        return assetResponseList;
    }

    @Override
    @Transactional
    public void saveAsset(AssetRequest assetRequest, String userEmail) throws ExecutionException, InterruptedException {
        ////////////////////////////// ticker를 이용해 api로 데이터 가져와서 저장하기 /////////////////////////////////////////////////

        // 1. API를 호출하여 AssetResponse 객체 생성
        AssetResponse response = apiCompromisedService.createAssetDTO(
                assetRequest.getStockName(),
                assetRequest.getQuantity(),
                assetRequest.getPurchasePrice(),
                assetRequest.isKorea()
        );

        // 2. StockInfo 테이블에서 주식 정보를 조회
        Optional<StockInfo> stockInfoOptional = stockInfoRepository.findByStockName(assetRequest.getStockName());

        if (stockInfoOptional.isEmpty()) {
            throw new RuntimeException("Stock information not found for asset name: " + assetRequest.getStockName());
        }

        StockInfo stockInfo = stockInfoOptional.get();

        // 3. Member 정보 조회
        Member member = memberRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        // 4. Asset 객체 생성
        Asset asset = new Asset(
                stockInfo,
                assetRequest.getQuantity(),
                assetRequest.getPurchasePrice(),
                response.getAnnualDividend()
        );

        // Member와 연관 설정
        asset.setMember(member);

        // 5. Asset 객체를 데이터베이스에 저장
        assetRepo.save(asset);
    }


    @Override
    public List<String> getAllStocks() {
        List<String> stocksNames = new ArrayList<>();
        stockInfoRepository.findAll().forEach(stock -> stocksNames.add(stock.getStockName()));
        return stocksNames;
    }

    @Override
    public AssetResponse getAssetById(String tickerSymbol) {
        Optional<Asset> optionalAsset = assetRepo.findByStockInfoTickerSymbol(tickerSymbol);

        // 존재하지 않으면 예외처리
        if (optionalAsset.isEmpty()) {
            System.out.println("Asset not found");
            return null; // 예시로 null 반환
        }

        Asset asset = optionalAsset.get();

        try {
            System.out.println("쿼리 파라미터 : " + asset.getStockInfo() + asset.getQuantity() + asset.getPurchasePrice());

            // API를 사용하여 추가 데이터를 가져옴
            AssetResponse apiResponse = apiCompromisedService.createAssetDTO(
                    asset.getStockInfo().getStockName(),
                    asset.getQuantity(),
                    asset.getPurchasePrice(),
                    true
            );

            // 배당 정보를 가져와서 Map에 저장
            Map<LocalDate, Double> dividends = dividendRepository.findByStockInfoTickerSymbol(asset.getStockInfo().getTickerSymbol()).stream()
                    .collect(Collectors.toMap(
                            Dividend::getDividendDate,
                            Dividend::getDividendAmount
                    ));

            // apiResponse에 배당 정보를 설정
            apiResponse.setDividends(dividends);

            return apiResponse;

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException("Error fetching asset data", e);
        }
    }


    @Override
    @Transactional
    public boolean editAsset(AssetResponse assetDTO, AssetResponse pastAssetDTO) {

        boolean checkChange = false;

        // 주식 수량과 평균 단가 중 하나라도 바뀐 경우
        if (assetDTO.getQuantity() != pastAssetDTO.getQuantity() || assetDTO.getPurchasePrice() != pastAssetDTO.getPurchasePrice()) {
            assetDTO.setQuantity(pastAssetDTO.getQuantity());
            assetDTO.setPurchasePrice(pastAssetDTO.getPurchasePrice());

            checkChange = true;
        }

        // 값을 변경
        if (checkChange) {
            // 기존의 asset 객체를 가져와 수정한 후 저장
            Optional<Asset> existingAsset = assetRepo.findById(assetDTO.getTickerSymbol());

            Asset asset = existingAsset.get();

            asset.setQuantity(assetDTO.getQuantity());
            asset.setPurchasePrice(assetDTO.getPurchasePrice());
            assetRepo.save(asset);
        }

        return checkChange;
    }

    /////////////////////////////////// api 호출로 수정/////////////////////////////////////////////////////////////
    private double getCurrentPrice(String assetName) {
        return 10000;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public Mono<String> getTradingViewChartScript() {
        return tradingViewService.getTradingViewChartScript();
    }

    @Override
    @Transactional
    public void deleteAsset(String tickerSymbol) {
        if (assetRepo.existsById(tickerSymbol)) {
            assetRepo.deleteById(tickerSymbol);
        } else {
            System.out.println("Asset with ID " + tickerSymbol + " does not exist.");
        }
    }


}
