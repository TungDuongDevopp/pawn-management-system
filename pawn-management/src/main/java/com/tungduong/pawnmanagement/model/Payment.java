package com.tungduong.pawnmanagement.model;

import com.tungduong.pawnmanagement.model.base.BaseEntity;
import com.tungduong.pawnmanagement.model.enums.PaymentMethod;
import com.tungduong.pawnmanagement.model.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "amount can not be null")
    private BigDecimal amount;

    @NotNull(message = "paymentMethod can not be null")
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @NotNull(message = "localDate can not be null")
    private LocalDate paymentDate;

    @NotNull(message = "paymentStatus can not be null")
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_schedule_id")
    private PaymentSchedule paymentSchedule;
}

