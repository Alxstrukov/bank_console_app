package ru.clevertec.console.application.services;

import java.math.BigDecimal;

public interface PercentCalculatable {
    void plusBalancePercent(int balanceOwner);

    BigDecimal addPercentToBalance(BigDecimal value, BigDecimal percent);

    BigDecimal loadPercentFromConfig();
}
