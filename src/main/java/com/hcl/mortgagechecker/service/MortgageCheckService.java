package com.hcl.mortgagechecker.service;

import com.hcl.mortgagechecker.dto.MortgageRequestDto;
import com.hcl.mortgagechecker.dto.MortgageResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Service class responsible for performing mortgage checks.
 * @author Jaspreet.Singh
 */
@Service
public class MortgageCheckService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MortgageCheckService.class);
    InterestRateService interestRateService;

    @Value("${custom.config.incomeMultiplier:4}")
    private BigDecimal incomeMultiplier;

    public MortgageCheckService(InterestRateService interestRateService) {
        this.interestRateService = interestRateService;
    }

    public void setIncomeMultiplier(BigDecimal incomeMultiplier) {
        this.incomeMultiplier = incomeMultiplier;
    }

    /**
     * Method to check if the mortgage is feasible and return the object containing the monthly installment and feasibility
     * One level of validation is done in the controller and second level of validation is done here
     * @param mortgageRequestDto
     * @return
     */
    public MortgageResponseDto checkMortgage(MortgageRequestDto mortgageRequestDto){
        if(LOGGER.isTraceEnabled()){
            LOGGER.trace("{}: {} method {} with params {} }", this.getClass().getName(), "entering", "checkMortgage", mortgageRequestDto);
        }
        int maturityPeriod = mortgageRequestDto.maturityPeriod();
        BigDecimal homeValue = mortgageRequestDto.homeValue();
        BigDecimal loanValue = mortgageRequestDto.loanValue();
        BigDecimal income = mortgageRequestDto.income();

        if(homeValue == null || loanValue == null || income == null)
            throw new IllegalArgumentException("validation.allFields.NotNull");
        if(!isFeasible(loanValue, homeValue, income))
            return new MortgageResponseDto(false, BigDecimal.ZERO);

        BigDecimal monthlyInstallment = getMonthlyInstallment(maturityPeriod, loanValue);
        boolean feasible = monthlyInstallment.compareTo(BigDecimal.ZERO) > 0;

        if(LOGGER.isTraceEnabled()){
            LOGGER.trace("{}: {} method {} with result {} }", this.getClass().getName(), "exiting", "checkMortgage", monthlyInstallment);
        }
        return new MortgageResponseDto(feasible, monthlyInstallment);
    }

    /**
     * Method to calculate the monthly installment. The formula is: (loanValue * interestRate / 1200)
     * @param maturityPeriod
     * @param loanValue
     * @return
     */
    private BigDecimal getMonthlyInstallment(int maturityPeriod, BigDecimal loanValue) {
        double interestRate = interestRateService.getInterestRateByMaturityPeriod(maturityPeriod);
        LOGGER.debug("Interest rate for maturity period {} is {}", maturityPeriod, interestRate);
        return loanValue.multiply(BigDecimal.valueOf(interestRate))
                                                    .divide(BigDecimal.valueOf(1200), 2, RoundingMode.HALF_UP);
    }

    /**
     * Method to check if the mortgage is feasible based on the given conditions in the assignment.
     * loanValue <= homeValue && loanValue <= income * incomeMultiplier
     * incomeMultiplier = 4, is a custom configuration specified in application.yml
     * @param loanValue
     * @param homeValue
     * @param income
     * @return boolean
     */
    public boolean isFeasible(BigDecimal loanValue, BigDecimal homeValue, BigDecimal income){
        return loanValue.compareTo(homeValue) <= 0 && loanValue.compareTo(income.multiply(incomeMultiplier)) <= 0;
    }
}
