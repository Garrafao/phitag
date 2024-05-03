package de.garrafao.phitag.infrastructure.persistence.jpa.user;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.user.User;
import de.garrafao.phitag.domain.user.UserRepository;
import de.garrafao.phitag.infrastructure.persistence.jpa.user.query.UserQueryJpa;

@Repository
public class UserRepositoryBridge implements UserRepository {
    private final UserRepositoryJpa userRepositoryJpa;

    @Autowired
    public UserRepositoryBridge(UserRepositoryJpa userRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public List<User> findByQuery(Query query) {
        UserQueryJpa userQueryJpa = new UserQueryJpa(query);

        return userRepositoryJpa.findAll(userQueryJpa);
    }

    @Override
    public Page<User> findByQueryPaged(Query query, PageRequestWraper page) {
        UserQueryJpa userQueryJpa = new UserQueryJpa(query);

        return userRepositoryJpa.findAll(userQueryJpa, page.getPageRequest());
    }

    @Override
    public User save(User user) {
        return userRepositoryJpa.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepositoryJpa.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepositoryJpa.findByEmail(email);
    }

}
