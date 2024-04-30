package com.ra.project5.repository;

import com.ra.project5.model.entity.AddressesEntity;
import com.ra.project5.model.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressesEntity, Long> {
    AddressesEntity findByAddressIdAndUsersByUserId(Long addressId, UsersEntity userId);
}
