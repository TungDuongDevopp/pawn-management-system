package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.enums.PaymentScheduleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "payment_schedules")
@Getter
@Setter
@NoArgsConstructor
public class PaymentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "periodNum can not be null")
    private Integer periodNum;

    @NotNull(message = "dueDate can not be null")
    private LocalDate dueDate;

    @NotNull(message = "pricipalDue can not be null")
    private BigDecimal pricipalDue;

    @NotNull(message = "interestDue can not be null")
    private BigDecimal interestDue;

    @NotNull(message = "paymentScheduleStatus can not be null")
    @Enumerated(EnumType.STRING)
    private PaymentScheduleStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private Instant createdAt;

    private Instant updatedAt;

    @OneToMany(mappedBy = "paymentSchedule")
    private List<Payment> payments;


}
