package de.garrafao.phitag.domain.status;

import java.util.List;
import java.util.Optional;

public interface StatusRepository {

    public List<Status> findAll();

    public Optional<Status> findByName(String name);

}
