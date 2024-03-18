package com.example.demo.src.subscription.entity;


import com.example.demo.common.entity.BaseEntity;
import com.example.demo.src.test.entity.CollectionDetail;
import com.example.demo.src.test.entity.CollectionUser;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.user.entity.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
@Getter
@Entity
@Table(name = "PAYMENT")
public class Payment extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriptionId")
    private Subscription subscription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User paidUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "paymentMethod", nullable = false, length = 20)
    private PaymentMethod paymentMethod;

    @Column(nullable = false)
    private Float receivedAmount;

    @Column(nullable = false)
    private Float effectiveAmount;

    @Column(nullable = false)
    private LocalDateTime paidAt;

    public enum PaymentMethod {
        CREDIT_CARD;
    }
    @Builder
    public Payment(Long id, Subscription subscription, User paidUser, PaymentMethod paymentMethod,
                   Float receivedAmount, Float effectiveAmount, LocalDateTime paidAt) {
        this.id = id;
        this.subscription = subscription;
        this.paidUser = paidUser;
        this.paymentMethod = paymentMethod;
        this.receivedAmount = receivedAmount;
        this.effectiveAmount = effectiveAmount;
        this.paidAt = paidAt;
    }


}
