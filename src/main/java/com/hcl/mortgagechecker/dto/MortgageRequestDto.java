package com.hcl.mortgagechecker.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * Record to represent the mortgage-check request object structure.
 * The error messages are defined in the messages.properties file
 * @param maturityPeriod
 * @param income
 * @param loanValue
 * @param homeValue
 *
 * @author Jaspreet.Singh
 */
public record MortgageRequestDto(
        @NotNull(message = "{validation.maturityPeriod.NotNull}")
        @Digits(integer = 2, fraction = 0, message = "{validation.maturityPeriod.Digits}")
        @Positive(message = "{validation.maturityPeriod.Digits}")
        @Max(value = 30, message = "{validation.maturityPeriod.Digits}")
        int maturityPeriod,

        @NotNull(message = "{validation.income.NotNull}")
        @Positive(message = "{validation.income.invalid}")
        BigDecimal income,

        @NotNull(message = "{validation.loanValue.NotNull}")
        @Positive(message = "{validation.loanValue.invalid}")
        BigDecimal loanValue,

        @NotNull(message = "{validation.homeValue.NotNull}")
        @Positive(message = "{validation.homeValue.invalid}")
        BigDecimal homeValue
) {}