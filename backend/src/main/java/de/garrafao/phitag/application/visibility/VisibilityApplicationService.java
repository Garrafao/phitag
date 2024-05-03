package de.garrafao.phitag.application.visibility;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.visibility.data.VisibilityDto;
import de.garrafao.phitag.domain.visibility.VisibilityRepository;

@Service
public class VisibilityApplicationService {
    
    private final VisibilityRepository visibilityRepository;

    @Autowired
    public VisibilityApplicationService(final VisibilityRepository visibilityRepository) {
        this.visibilityRepository = visibilityRepository;
    }

    public Set<VisibilityDto> getVisibilityDtos() {
        return this.visibilityRepository.findAll().stream().map(VisibilityDto::from).collect(Collectors.toSet());
    }



}
