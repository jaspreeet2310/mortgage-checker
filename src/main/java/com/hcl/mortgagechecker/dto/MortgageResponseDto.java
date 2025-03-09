package com.hcl.mortgagechecker.dto;

import java.math.BigDecimal;

/**
 * Record to represent the mortgage-check response object structure.
 * @author Jaspreet.Singh
 */
public record MortgageResponseDto(boolean feasible, BigDecimal monthlyInstallment) {}

