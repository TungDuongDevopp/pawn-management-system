package com.tungduong.pawnmanagement.service;

import com.tungduong.pawnmanagement.mapper.CustomerMapper;
import com.tungduong.pawnmanagement.repository.CustomerDocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerDocumentService {
    private final CustomerDocumentRepository customerDocumentRepository;
    private final CustomerMapper customerMapper;
}
