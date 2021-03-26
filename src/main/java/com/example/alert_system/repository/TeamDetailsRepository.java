package com.example.alert_system.repository;

import com.example.alert_system.model.TeamDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface TeamDetailsRepository extends JpaRepository<TeamDetailsEntity, Long> {
    Optional<TeamDetailsEntity> findById(Long aLong);
    TeamDetailsEntity findByName(String name);
}
