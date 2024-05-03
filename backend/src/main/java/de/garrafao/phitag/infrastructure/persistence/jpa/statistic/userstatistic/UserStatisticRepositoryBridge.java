package de.garrafao.phitag.infrastructure.persistence.jpa.statistic.userstatistic;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.statistic.userstatistic.UserStatistic;
import de.garrafao.phitag.domain.statistic.userstatistic.UserStatisticRepository;

@Repository
public class UserStatisticRepositoryBridge implements UserStatisticRepository {

    private final UserStatisticRepositoryJpa userStatisticRepositoryJpa;

    @Autowired
    public UserStatisticRepositoryBridge(UserStatisticRepositoryJpa userStatisticRepositoryJpa) {
        this.userStatisticRepositoryJpa = userStatisticRepositoryJpa;
    }

    @Override
    public Optional<UserStatistic> findByUsername(final String username) {
        return this.userStatisticRepositoryJpa.findByUsername(username);
    }

    @Override
    public UserStatistic save(UserStatistic userStatistic) {
        return this.userStatisticRepositoryJpa.save(userStatistic);
    }
    
}
