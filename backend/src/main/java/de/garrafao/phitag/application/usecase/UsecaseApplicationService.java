package de.garrafao.phitag.application.usecase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.usecase.data.UsecaseDto;
import de.garrafao.phitag.domain.usecase.UsecaseRepository;

@Service
public class UsecaseApplicationService {
    
    private final UsecaseRepository usecaseRepository;

    @Autowired
    public UsecaseApplicationService(final UsecaseRepository usecaseRepository) {
        this.usecaseRepository = usecaseRepository;
    }

    public List<UsecaseDto> getUsecaseDtos() {
        return this.usecaseRepository.findAll().stream().map(UsecaseDto::from).collect(Collectors.toList());
    }
    
}
