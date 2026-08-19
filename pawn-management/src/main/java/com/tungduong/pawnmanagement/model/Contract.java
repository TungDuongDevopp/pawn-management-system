package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.base.BaseEntity;
import com.tungduong.pawnmanagement.model.enums.ContractStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "contracts")
@Getter
@Setter
@NoArgsConstructor
public class Contract extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "code can not be null")
    private String code;

    @NotNull( message = "signedDate can not be null")
    private LocalDate signedDate;

    @NotNull( message = "expiredDate can not be null")
    private LocalDate expiredDate;

    @NotNull(message = "contractStatus can not be null")
    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;


    @NotBlank(message = "File name can not be null")
    private String fileName;




}
