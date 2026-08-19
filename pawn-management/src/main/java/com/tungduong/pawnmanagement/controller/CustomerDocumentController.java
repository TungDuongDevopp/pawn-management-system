package com.tungduong.pawnmanagement.controller;

import com.tungduong.pawnmanagement.service.CustomerDocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerDocumentController {
    private final CustomerDocumentService customerDocumentService;
}
