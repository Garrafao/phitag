package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.userstatistic;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.garrafao.phitag.domain.statistic.userstatistic.UserStatistic;

public interface UserStatisticRepositoryJpa extends JpaRepository<UserStatistic, String> {

    Optional<UserStatistic> findByUsername(String username);
    
}
