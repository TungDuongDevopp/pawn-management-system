package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.base.BaseEntityFile;
import com.tungduong.pawnmanagement.model.enums.CustomerDocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_documents")
@Getter
@Setter
@NoArgsConstructor
public class CustomerDocument extends BaseEntityFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull( message = "CustomerDocumentType can not be null")
    private CustomerDocumentType customerDocumentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
