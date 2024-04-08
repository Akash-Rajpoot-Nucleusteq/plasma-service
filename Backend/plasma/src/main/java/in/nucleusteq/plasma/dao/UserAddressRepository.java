package in.nucleusteq.plasma.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import in.nucleusteq.plasma.entity.UserAddress;

public interface UserAddressRepository
        extends
            JpaRepository<UserAddress, Integer> {
}
