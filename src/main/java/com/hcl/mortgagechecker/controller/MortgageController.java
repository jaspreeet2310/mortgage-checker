package com.hcl.mortgagechecker.controller;

import com.hcl.mortgagechecker.dto.MortgageRequestDto;
import com.hcl.mortgagechecker.dto.MortgageResponseDto;
import com.hcl.mortgagechecker.model.InterestRate;
import com.hcl.mortgagechecker.service.InterestRateService;
import com.hcl.mortgagechecker.service.MortgageCheckService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling mortgage requests.
 * The controller provides endpoints for checking mortgage feasibility and retrieving all interest rates.
 * 1. GET /api/interest-rates
 * 2. POST /api/mortgage-check
 * @author Jaspreet.Singh
 */
@RestController
@RequestMapping("/api")
@Validated
public class MortgageController {
    InterestRateService interestRateService;
    MortgageCheckService mortgageCheckService;

    public MortgageController(InterestRateService interestRateService, MortgageCheckService mortgageCheckService){
        this.interestRateService = interestRateService;
        this.mortgageCheckService = mortgageCheckService;
    }

    /**
     * GET /api/interest-rates
     * Returns all the interest rates
     */
    @GetMapping("/interest-rates")
    public ResponseEntity<List<InterestRate>> getInterestRates(){
        return ResponseEntity.ok(interestRateService.getAllInterestRates());
    }

    /**
     * POST /api/mortgage-check
     * Checks if the mortgage is feasible and returns the monthly installment and feasibility.
     * Exceptions handled/thrown in MortgageCheckService class.
     */
    @PostMapping("/mortgage-check")
    public ResponseEntity<MortgageResponseDto> checkMortgageFeasibility(@Valid @RequestBody MortgageRequestDto mortgageRequest){
        return ResponseEntity.ok(mortgageCheckService.checkMortgage(mortgageRequest));
    }
}
