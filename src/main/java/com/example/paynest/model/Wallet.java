package com.example.paynest.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @Id
    private Integer id;

    private String accountNo;

    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
