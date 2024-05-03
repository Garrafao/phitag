package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.sampling.SamplingApplicationService;
import de.garrafao.phitag.application.sampling.data.SamplingDto;

@RestController
@RequestMapping(value = "/api/v1/sampling")
public class SamplingResource {

    private final SamplingApplicationService samplingApplicationService;

    @Autowired
    public SamplingResource(SamplingApplicationService samplingApplicationService) {
        this.samplingApplicationService = samplingApplicationService;
    }

    @RequestMapping(value = "/all")
    public List<SamplingDto> all() {
        return this.samplingApplicationService.getSamplings();
    }
    
}
