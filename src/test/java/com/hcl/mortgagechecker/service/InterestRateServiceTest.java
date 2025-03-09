package com.hcl.mortgagechecker.service;

import com.hcl.mortgagechecker.exception.InterestRateNotFoundException;
import com.hcl.mortgagechecker.model.InterestRate;
import com.hcl.mortgagechecker.repository.InterestRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterestRateServiceTest {
    @Mock
    InterestRateRepository interestRateRepository;
    @Mock
    MessageSource messageSource;

    @InjectMocks
    InterestRateService interestRateService;

    @Test
    void testGetAllInterestRates() {
        when(interestRateRepository.findAll()).thenReturn(List.of(
                        new InterestRate(201, 5.15, 1, LocalDateTime.now().minusDays(4)),
                        new InterestRate(202, 4.25, 5, LocalDateTime.now().minusMonths(1)),
                        new InterestRate(203, 3.50, 10, LocalDateTime.now().minusYears(1)),
                        new InterestRate(204, 2.25, 20, LocalDateTime.now())
        ));
        List<InterestRate> interestRatesResponse = interestRateService.getAllInterestRates();
        assertNotNull(interestRatesResponse);
        assertEquals(4, interestRatesResponse.size());
        assertEquals(5.15, interestRatesResponse.get(0).getInterestRate());
        assertEquals(202, interestRatesResponse.get(1).getId());

    }

    @Test
    void testGetInterestRateByMaturityPeriod(){
        when(interestRateRepository.findByMaturityPeriodOrderByLastUpdateDesc(10)).thenReturn(List.of(
                        new InterestRate(302, 4.25, 10, LocalDateTime.now().minusDays(4)),
                        new InterestRate(301, 5.15, 10, LocalDateTime.now().minusMonths(1)),
                        new InterestRate(303, 3.50, 10, LocalDateTime.now().minusYears(1))
        ));
        when(messageSource.getMessage("validation.maturityPeriod.NotFound", new Object[]{3}, Locale.getDefault())).thenReturn("Maturity period not found");
        when(interestRateRepository.findByMaturityPeriodOrderByLastUpdateDesc(3)).thenReturn(new ArrayList<>());

        assertEquals(4.25, interestRateService.getInterestRateByMaturityPeriod(10));
        assertNotEquals(5.15, interestRateService.getInterestRateByMaturityPeriod(10));
        assertNotEquals(3.50, interestRateService.getInterestRateByMaturityPeriod(10));
        assertThrows(InterestRateNotFoundException.class, () -> interestRateService.getInterestRateByMaturityPeriod(3));
    }
}
