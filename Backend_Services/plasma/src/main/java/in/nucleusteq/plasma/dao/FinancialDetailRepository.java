package in.nucleusteq.plasma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.entity.FinancialDetail;

public interface FinancialDetailRepository
        extends
            JpaRepository<FinancialDetail, Integer> {
}
