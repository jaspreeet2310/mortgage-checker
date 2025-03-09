package com.hcl.mortgagechecker.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *  InterestRate entity class
 */
@Entity
@Table(name = "INTEREST_RATES")
public class InterestRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double interestRate;
    private int maturityPeriod;
    private LocalDateTime lastUpdate;

    public InterestRate() {}

    public InterestRate(long id, double interestRate, int maturityPeriod, LocalDateTime lastUpdate) {
        this.id = id;
        this.interestRate = interestRate;
        this.maturityPeriod = maturityPeriod;
        this.lastUpdate = lastUpdate;
    }

    public long getId() {
        return id;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getMaturityPeriod() {
        return maturityPeriod;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
