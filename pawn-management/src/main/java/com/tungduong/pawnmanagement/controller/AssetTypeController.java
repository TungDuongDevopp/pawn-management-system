package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.service.AssetTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AssetTypeController {
    private final AssetTypeService assetTypeService;
}
