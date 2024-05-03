package de.garrafao.phitag.domain.statistic.userstatistic;

import java.util.Optional;

public interface UserStatisticRepository {

    public Optional<UserStatistic> findByUsername(final String username);

    public UserStatistic save(final UserStatistic userStatistic);

}
