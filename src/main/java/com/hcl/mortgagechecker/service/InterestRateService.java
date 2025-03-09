package com.hcl.mortgagechecker.service;

import com.hcl.mortgagechecker.exception.InterestRateNotFoundException;
import com.hcl.mortgagechecker.model.InterestRate;
import com.hcl.mortgagechecker.repository.InterestRateRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

/**
 * Service class responsible for providing all interest rates or based on maturity periods.
 * @author Jaspreet.Singh
 */
@Service
public class InterestRateService {
    InterestRateRepository interestRateRepo;
    MessageSource messageSource;

    public InterestRateService(InterestRateRepository interestRateRepo, MessageSource messageSource) {
        this.messageSource = messageSource;
        this.interestRateRepo = interestRateRepo;
    }

    /**
     * Returns the list of all the interest rates.
     */
    public List<InterestRate> getAllInterestRates(){
        return interestRateRepo.findAll();
    }

    /**
     * Returns the interest rate for a given maturity period.
     * If there are multiple interest rates for a given maturity period, it returns the latest updated one due to sorting(order by) logic in the repository.
     * Throws an exception if the maturity period is not found
     * @param maturityPeriod
     * @return interest rate
     */
    public double getInterestRateByMaturityPeriod(int maturityPeriod){
        List<InterestRate> interestRates = interestRateRepo.findByMaturityPeriodOrderByLastUpdateDesc(maturityPeriod);
        if(!interestRates.isEmpty())
            return interestRates.get(0).getInterestRate();
        else
            throw new InterestRateNotFoundException(messageSource.getMessage("validation.maturityPeriod.NotFound", new Object[]{maturityPeriod}, Locale.getDefault()));
    }
}
