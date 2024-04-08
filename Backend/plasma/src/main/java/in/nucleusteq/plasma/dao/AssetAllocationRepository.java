package in.nucleusteq.plasma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.entity.AssetAllocation;

public interface AssetAllocationRepository
        extends
            JpaRepository<AssetAllocation, Integer> {
}
