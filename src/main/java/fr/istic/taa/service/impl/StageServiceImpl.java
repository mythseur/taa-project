package fr.istic.taa.service.impl;

import fr.istic.taa.domain.Stage;
import fr.istic.taa.repository.StageRepository;
import fr.istic.taa.repository.search.StageSearchRepository;
import fr.istic.taa.service.StageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * Service Implementation for managing Stage.
 */
@Service
@Transactional
public class StageServiceImpl implements StageService {

    private final Logger log = LoggerFactory.getLogger(StageServiceImpl.class);

    @Inject
    private StageRepository stageRepository;

    @Inject
    private StageSearchRepository stageSearchRepository;

    /**
     * Save a stage.
     *
     * @param stage the entity to save
     * @return the persisted entity
     */
    public Stage save(Stage stage) {
        log.debug("Request to save Stage : {}", stage);
        Stage result = stageRepository.save(stage);
        stageSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the stages.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Stage> findAll() {
        log.debug("Request to get all Stages");
        List<Stage> result = stageRepository.findAll();

        return result;
    }

    /**
     * Get one stage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Stage findOne(Long id) {
        log.debug("Request to get Stage : {}", id);
        Stage stage = stageRepository.findOne(id);
        return stage;
    }

    /**
     * Delete the  stage by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Stage : {}", id);
        stageRepository.delete(id);
        stageSearchRepository.delete(id);
    }

    /**
     * Search for the stage corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Stage> search(String query) {
        log.debug("Request to search Stages for query {}", query);
        return StreamSupport
            .stream(stageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
