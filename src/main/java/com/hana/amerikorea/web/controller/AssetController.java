package com.hana.amerikorea.web.controller;

import com.hana.amerikorea.web.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/asset_list")
public class AssetController {

    @Autowired
    private AssetService assetService;


}
