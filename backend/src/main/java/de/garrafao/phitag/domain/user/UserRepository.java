package de.garrafao.phitag.domain.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;

public interface UserRepository {

    List<User> findByQuery(Query query);

    Page<User> findByQueryPaged(Query query, PageRequestWraper page); 
    
    Optional<User> findByUsername(final String username);

    Optional<User> findByEmail(final String email);
    
    User save(final User user);

}
