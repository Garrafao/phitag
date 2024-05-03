package de.garrafao.phitag.application.status;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.status.data.StatusDto;
import de.garrafao.phitag.domain.status.StatusRepository;

@Service
public class StatusApplicationService {
    
    private final StatusRepository statusRepository;

    @Autowired
    public StatusApplicationService(final StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public List<StatusDto> getStatusDtos() {
        return this.statusRepository.findAll().stream().map(StatusDto::from).collect(Collectors.toList());
    }
}
