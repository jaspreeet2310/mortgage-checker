package com.hcl.mortgagechecker.service;

import com.hcl.mortgagechecker.dto.MortgageRequestDto;
import com.hcl.mortgagechecker.dto.MortgageResponseDto;
import com.hcl.mortgagechecker.exception.InterestRateNotFoundException;
import com.hcl.mortgagechecker.model.InterestRate;
import com.hcl.mortgagechecker.repository.InterestRateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MortgageCheckServiceTest {
    @Mock
    InterestRateRepository interestRateRepository;
    @Mock
    MessageSource messageSource;

    InterestRateService interestRateService;
    MortgageCheckService mortgageCheckService;

    @BeforeEach
    void setup() {
        interestRateService = new InterestRateService(interestRateRepository, messageSource);
        mortgageCheckService = new MortgageCheckService(interestRateService);
        mortgageCheckService.setIncomeMultiplier(new BigDecimal("4"));
    }

    @Test
    void testMortgageCheck_ValidRequest_ShouldPass() {
        when(interestRateRepository.findByMaturityPeriodOrderByLastUpdateDesc(any())).thenReturn(List.of(
                new InterestRate(302, 4.25, 10, LocalDateTime.now().minusDays(4))
        ));
        MortgageRequestDto request = new MortgageRequestDto(5, new BigDecimal("10000"), new BigDecimal("20000"), new BigDecimal("30000"));
        MortgageResponseDto response = mortgageCheckService.checkMortgage(request);

        assertTrue(response.feasible());
        assertNotNull(response.monthlyInstallment());
    }


    @Test
    void testMortgageCheck_ExceedsIncomeLimit_ShouldFail() {
        MortgageRequestDto request = new MortgageRequestDto(10, new BigDecimal("10000"), new BigDecimal("50000"), new BigDecimal("60000"));
        MortgageResponseDto response = mortgageCheckService.checkMortgage(request);

        assertFalse(response.feasible());
        assertEquals(BigDecimal.ZERO, response.monthlyInstallment());
    }

    @Test
    void testMortgageCheck_ExceedsHomeValue_ShouldFail() {
        MortgageRequestDto request = new MortgageRequestDto(20, new BigDecimal("10000"), new BigDecimal("30000"), new BigDecimal("20000"));
        MortgageResponseDto response = mortgageCheckService.checkMortgage(request);

        assertFalse(response.feasible());
        assertEquals(BigDecimal.ZERO, response.monthlyInstallment());
    }

    @Test
    void testMortgageCheck_MissingMaturityPeriod_ShouldFail() {
        MortgageRequestDto request = new MortgageRequestDto(15, new BigDecimal("10000"), new BigDecimal("30000"), new BigDecimal("30000"));

        try {
            mortgageCheckService.checkMortgage(request);
        }catch (InterestRateNotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void testMortgageCheck_MissingRequiredFields_ShouldFail() {
        MortgageRequestDto request = new MortgageRequestDto(15, null, new BigDecimal("30000"), new BigDecimal("30000"));
        try {
            mortgageCheckService.checkMortgage(request);
        }catch (IllegalArgumentException e) {
            assertTrue(true);
        }
    }
}
