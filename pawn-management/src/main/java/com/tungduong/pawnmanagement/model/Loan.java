package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.base.BaseEntity;
import com.tungduong.pawnmanagement.model.enums.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
public class Loan extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "amount can not be null")
    private BigDecimal amount;

    @NotNull(message = "interestRate can not be null")
    private double interestRate;

    @NotNull(message = "disbursementDate can not be null")
    private LocalDate disbursementDate;

    @NotNull(message = "dueDate can not be null")
    private LocalDate dueDate;

    @NotNull(message = "loanStatus can not be null")
    @Enumerated(EnumType.STRING)
    private LoanStatus status;


    @OneToOne()
    @JoinColumn(name = "contract_id",unique = true)
    private Contract contract;

    @OneToMany(mappedBy = "loan")
    private List<PaymentSchedule> paymentSchedules;

    @OneToMany(mappedBy = "loan")
    private List<Collateral> collaterals;


}
