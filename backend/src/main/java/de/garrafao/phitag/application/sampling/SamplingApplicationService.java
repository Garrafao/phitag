package de.garrafao.phitag.application.sampling;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.sampling.data.SamplingDto;
import de.garrafao.phitag.domain.sampling.SamplingRepository;

@Service
public class SamplingApplicationService {
    
    private final SamplingRepository samplingRepository;

    @Autowired
    public SamplingApplicationService(final SamplingRepository samplingRepository) {
        this.samplingRepository = samplingRepository;
    }

    public List<SamplingDto> getSamplings() {
        return this.samplingRepository.findAll().stream().map(SamplingDto::from).collect(Collectors.toList());
    }

}
