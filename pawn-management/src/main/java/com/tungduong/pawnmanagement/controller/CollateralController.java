package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.service.CollateralService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CollateralController {
    private final CollateralService collateralService;
}
