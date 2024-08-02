package com.hana.amerikorea.portfolio.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Table(name="stock")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long StockId;

    @Column(name="stock_name",nullable = false)
    private String stockName;

    @Column
    private  Sector sector;




}
