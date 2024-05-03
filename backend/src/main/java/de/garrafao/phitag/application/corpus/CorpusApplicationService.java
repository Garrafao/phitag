package de.garrafao.phitag.application.corpus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.garrafao.phitag.application.common.CommonService;
import de.garrafao.phitag.application.corpus.data.command.AddUsagesFromCorpusCommand;
import de.garrafao.phitag.application.corpus.data.command.CorpusQueryCommand;
import de.garrafao.phitag.application.corpus.data.dto.CorpusTextSmallDto;
import de.garrafao.phitag.application.corpus.data.dto.PagedCorpusTextDto;
import de.garrafao.phitag.application.validation.ValidationService;
import de.garrafao.phitag.domain.core.PageRequestWraper;
import de.garrafao.phitag.domain.core.Query;
import de.garrafao.phitag.domain.corpus.CorpusRepository;
import de.garrafao.phitag.domain.corpus.query.CorpusQueryBuilder;
import de.garrafao.phitag.domain.corpusinformation.CorpusInformationRepository;
import de.garrafao.phitag.domain.corpuslexicon.CorpusLexicon;
import de.garrafao.phitag.domain.corpuslexicon.CorpusLexiconRepository;
import de.garrafao.phitag.domain.corpuslexicon.query.CorpusLexiconQueryBuilder;
import de.garrafao.phitag.domain.corpustext.CorpusText;
import de.garrafao.phitag.domain.corpustext.CorpusTextRepository;
import de.garrafao.phitag.domain.corpustext.error.CorpusTextException;
import de.garrafao.phitag.domain.helper.Pair;
import de.garrafao.phitag.domain.phitagdata.usage.Usage;
import de.garrafao.phitag.domain.phitagdata.usage.UsageRepository;
import de.garrafao.phitag.domain.project.Project;
import de.garrafao.phitag.domain.user.User;

@Service
public class CorpusApplicationService {

    // Repository
    private final CorpusRepository corpusRepository;

    private final CorpusTextRepository corpusTextRepository;

    private final CorpusLexiconRepository corpusLexiconRepository;

    private final CorpusInformationRepository corpusInformationRepository;

    private final UsageRepository usageRepository;

    // Service
    private final CommonService commonService;

    private final ValidationService validationService;

    @Autowired
    public CorpusApplicationService(
            CorpusRepository corpusRepository,
            CorpusTextRepository corpusTextRepository,
            CorpusLexiconRepository corpusLexiconRepository,
            CorpusInformationRepository corpusInformationRepository,

            UsageRepository usageRepository,

            CommonService commonService,
            ValidationService validationService) {
        this.corpusRepository = corpusRepository;
        this.corpusTextRepository = corpusTextRepository;
        this.corpusLexiconRepository = corpusLexiconRepository;
        this.corpusInformationRepository = corpusInformationRepository;

        this.usageRepository = usageRepository;

        this.commonService = commonService;
        this.validationService = validationService;
    }

    /**
     * Query the corpus
     * 
     * @param command the query command
     * @return the corpus text as paged dto
     */
    public PagedCorpusTextDto query(final CorpusQueryCommand command) {
        if (command.getLemma() == null || command.getLemma().isBlank() || command.getLemma().length() < 3) {
            return PagedCorpusTextDto.empty();
        }

        // Get lexicon entries with lemma and pos
        final Query lexiconQuery = new CorpusLexiconQueryBuilder()
                .withLemma(command.getLemma())
                .withPoS(command.getPos())
                .build();

        final List<String> possibleIds = corpusLexiconRepository.findByQuery(lexiconQuery).stream()
                .map(CorpusLexicon::getId)
                .toList();

        // Check for unknown lemma and stop search early
        if (possibleIds.isEmpty()) {
            return PagedCorpusTextDto.empty();
        }

        // remove non unique ids
        final List<String> uniqueIds = possibleIds.stream().distinct().toList();

        // get corpus names from command and clean empty
        final List<String> corpusNames = Arrays.stream(command.getCorpus().split(",")) // split by comma
                .filter(name -> name != null && !name.isBlank())
                .toList();

        // Get Corpus Entries
        final Query corpusQuery = new CorpusQueryBuilder()
                .betweenDate(command.getFrom(), command.getTo())
                .withCorpusNames(corpusNames)
                .withLexiconIds(uniqueIds)
                .build();

        final PagedCorpusTextDto corpus = PagedCorpusTextDto
                .fromPagedCorpus(corpusRepository.findByQueryPaged(corpusQuery,
                        new PageRequestWraper(command.getSize(), command.getPage())));

        if (!command.getContext().booleanValue())
            return corpus;

        // Get Context
        corpus.getContent().stream().forEach(corpusTextDto -> {
            corpusTextDto.setPrevious(CorpusTextSmallDto
                    .from(corpusTextRepository.findById(corpusTextDto.getPrevious().getId()).orElse(null)));
            corpusTextDto.setNext(CorpusTextSmallDto
                    .from(corpusTextRepository.findById(corpusTextDto.getNext().getId()).orElse(null)));
        });

        return corpus;

    }

    /**
     * Get all possible lemmas for a lemma/token
     * 
     * @param lemma lemma to search for
     * @return list of possible lemmas
     */
    public List<String> getPossibleLemmas(final String lemma) {
        if (lemma.length() < 3) {
            return List.of();
        }

        final Query query = new CorpusLexiconQueryBuilder().withLikeToken(lemma).build();

        final List<String> lemmas = new ArrayList<>();

        // search, add if unique
        corpusLexiconRepository.findByQuery(query).stream().forEach(result -> {
            if (!lemmas.contains(result.getLemma())) {
                lemmas.add(result.getLemma());
            }
        });

        lemmas.sort((a, b) -> a.length() - b.length());

        return lemmas;

    }

    public List<String> getPossibleCorpora() {
        return corpusInformationRepository.findDistinctCorpusnamesShort();
    }

    /**
     * Get all possible pos for a lemma
     * 
     * @param lemma lemma to search for
     * @return list of possible pos
     */
    public List<String> getPossiblePos(final String lemma) {
        final Query query = new CorpusLexiconQueryBuilder().withLemma(lemma).build();
        var pos = corpusLexiconRepository.findByQuery(query).stream().map(CorpusLexicon::getPos).toList();
        return pos.stream().distinct().toList();
    }

    /**
     * Get all possible tokens for a lemma
     * 
     * @param lemma lemma to search for
     * @return list of possible tokens
     */
    public List<String> getPossibleTokens(final String lemma) {
        final Query query = new CorpusLexiconQueryBuilder().withLemma(lemma).build();
        return corpusLexiconRepository.findByQuery(query).stream().map(CorpusLexicon::getToken).toList();
    }

    /**
     * Get all possible tokens for a lemma and pos
     * 
     * @param lemma lemma to search for
     * @param pos   pos to search for
     * @return list of possible tokens
     */
    public List<String> getPossibleTokens(final String lemma, final String pos) {
        final Query query = new CorpusLexiconQueryBuilder().withLemma(lemma).withPoS(pos).build();
        return corpusLexiconRepository.findByQuery(query).stream().map(CorpusLexicon::getToken).toList();
    }

    /**
     * Add selected corpus text as usages to the project
     * 
     * @param authenticationToken authentication token of requesting user
     * @param command             command with project id and corpus text ids
     */
    @Transactional
    public void addNewUsages(final String authenticationToken, final AddUsagesFromCorpusCommand command) {
        final User requester = this.commonService.getUserByAuthenticationToken(authenticationToken);
        final Project projectEntity = this.commonService.getProject(requester.getUsername(), command.getProject());

        this.validationService.projectAdminAccess(requester, projectEntity);

        final List<String> possibleToken = this.getPossibleTokens(command.getLemma(), command.getPos());

        for (String id : command.getCorpusTextIds()) {
            final CorpusText corpusText = this.corpusTextRepository.findById(id)
                    .orElseThrow(() -> new CorpusTextException("Corpus Text not found"));

            // calculate start and end index of target lemma
            final List<Pair<Integer, Integer>> startAndEndIndex = this.getStartAndEndIndex(corpusText, possibleToken,
                    command.isNormalizeContext());
            // extend context if needed
            final Pair<Integer, String> extendedContext = this.getExtendedContext(corpusText,
                    command.isNormalizeContext(),
                    command.isIncludeContext());

            String indeciestoken = startAndEndIndex.stream()
                    .map(pair -> (pair.getLeft() + extendedContext.getLeft()) + ":"
                            + (pair.getRight() + extendedContext.getLeft()))
                    .collect(Collectors.joining(","));

            String indicessentence = String.format("%d:%d", extendedContext.getLeft(),
                    (command.isNormalizeContext() ? corpusText.getOrthography().length()
                            : corpusText.getText().length()) + extendedContext.getLeft());

            // create new usage
            this.usageRepository.save(
                    new Usage(
                            projectEntity,
                            UUID.randomUUID().toString(),
                            extendedContext.getRight(),
                            indeciestoken,
                            indicessentence,
                            command.getLemma(),
                            ""));

        }

    }

    private Pair<Integer, String> getExtendedContext(CorpusText corpusText, boolean normalizeContext,
            boolean includeContext) {
        if (!includeContext)
            return Pair.of(0, normalizeContext ? corpusText.getOrthography() : corpusText.getText());

        final CorpusText findPrevious = this.corpusTextRepository.findById(corpusText.getPreviousid()).orElse(null);
        final CorpusText findNext = this.corpusTextRepository.findById(corpusText.getNextid()).orElse(null);

        final String previous = findPrevious != null
                ? (normalizeContext ? findPrevious.getOrthography() : findPrevious.getText())
                : "";
        final String next = findNext != null ? (normalizeContext ? findNext.getOrthography() : findNext.getText()) : "";

        return Pair.of(previous.length() + 1,
                previous + " " + (normalizeContext ? corpusText.getOrthography() : corpusText.getText()) + " " + next);
    }

    private List<Pair<Integer, Integer>> getStartAndEndIndex(final CorpusText corpusText,
            final List<String> lemmaTokens, final boolean normalizeContext) {
        final List<String> tokens = Arrays.asList(corpusText.getText().split(" "));
        final List<String> normalized = Arrays.asList(corpusText.getOrthography().split(" "));
        final List<Pair<Integer, Integer>> startAndEndIndex = new ArrayList<>();

        int lengthCovered = 0;
        for (int i = 0; i < tokens.size(); i++) {
            if (lemmaTokens.contains(tokens.get(i))) {
                int startIndex = lengthCovered;
                int endIndex = lengthCovered + (normalizeContext ? normalized.get(i).length() : tokens.get(i).length());
                startAndEndIndex.add(Pair.of(startIndex, endIndex));
            }
            lengthCovered += (normalizeContext ? normalized.get(i).length() : tokens.get(i).length()) + 1;
        }

        return startAndEndIndex;
    }

}
