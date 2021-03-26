package com.example.alert_system.repository;

import com.example.alert_system.model.DeveloperDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface DeveloperDetailsRepository extends JpaRepository<DeveloperDetailsEntity, Long> {
    DeveloperDetailsEntity findById(long id);
    DeveloperDetailsEntity findByPhoneNumber(String phoneNumber);
}
