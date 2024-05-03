package de.garrafao.phitag.infrastructure.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.garrafao.phitag.application.corpus.CorpusApplicationService;
import de.garrafao.phitag.application.corpus.data.command.AddUsagesFromCorpusCommand;
import de.garrafao.phitag.application.corpus.data.command.CorpusQueryCommand;
import de.garrafao.phitag.application.corpus.data.dto.PagedCorpusTextDto;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestController
@RequestMapping(value = "/api/v1/corpus")
public class CorpusResource {

    private final CorpusApplicationService corpusApplicationService;

    @Autowired
    public CorpusResource(CorpusApplicationService corpusApplicationService) {
        this.corpusApplicationService = corpusApplicationService;
    }

    @GetMapping()
    public PagedCorpusTextDto query(
            @RequestParam(value = "lemma") final String lemma,
            @RequestParam(value = "pos", required = false) final String pos,
            @RequestParam(value = "corpus", required = false) final String corpus,
            @RequestParam(value = "context", required = false, defaultValue = "false") final Boolean context,
            @RequestParam(value = "from", required = false) final Integer from,
            @RequestParam(value = "to", required = false) final Integer to,
            @RequestParam(value = "page", required = false, defaultValue = "0") final int page,
            @RequestParam(value = "size", required = false, defaultValue = "50") final int size) {
        // AXIOS does not allow for GET requests with a body
        final CorpusQueryCommand command = new CorpusQueryCommand(
                page, size,
                lemma, pos,
                corpus,
                context,
                from, to);
        return corpusApplicationService.query(command);
    }

    @GetMapping(path = "/lemma")
    public List<String> getPossibleLemmas(
            @RequestParam(value = "lemma", required = false) final String lemma) {
        return corpusApplicationService.getPossibleLemmas(lemma);
    }

    @GetMapping(path = "/lemma/pos")
    public List<String> getPossibleLemmasWithPos(
            @RequestParam(value = "lemma") final String lemma) {
        return corpusApplicationService.getPossiblePos(lemma);
    }

    @GetMapping(path = "/lemma/token")
    public List<String> getPossibleLemmasWithToken(
            @RequestParam(value = "lemma") final String lemma) {
        return corpusApplicationService.getPossibleTokens(lemma);
    }

    @GetMapping(path = "/corpus/names/short")
    public List<String> getPossibleCorpora() {
        return corpusApplicationService.getPossibleCorpora();
    }

    @PostMapping(value = "/usage")
    public void addUsages(
            @RequestHeader("Authorization") String authenticationToken,
            @RequestBody final AddUsagesFromCorpusCommand command) {
        corpusApplicationService.addNewUsages(authenticationToken, command);
    }

}
