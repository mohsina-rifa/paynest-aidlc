package com.example.paynest.dto;

import java.math.BigDecimal;
import java.util.List;

public record TransactionResponse(
        Integer id,
        BigDecimal amount,
        List<String> categoryNames
) {}
