package fr.istic.taa.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.inject.Inject;

import fr.istic.taa.domain.Stage;
import fr.istic.taa.dto.EntrepriseIHM;
import fr.istic.taa.dto.EtudiantIHM;
import fr.istic.taa.repository.StageRepository;
import fr.istic.taa.repository.search.StageSearchRepository;
import fr.istic.taa.service.EntrepriseService;
import fr.istic.taa.service.EtudiantService;
import fr.istic.taa.service.StageService;

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

    @Inject
    private EtudiantService etudiantService;

    @Inject
    private EntrepriseService entrepriseService;

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

        ZonedDateTime date = stage.getDatedebut().atStartOfDay(ZoneId.systemDefault());

        EtudiantIHM etudiant = etudiantService.findOneByDate(stage.getEtudiant().getId(), date);
        EntrepriseIHM entreprise = entrepriseService.findOneByDate(stage.getEntreprise().getId(), date);

        if(etudiant != null) stage.setEtudiant(etudiant);
        if(entreprise != null) stage.setEntreprise(entreprise);

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

    /**
     * Get the stages for etudiant id
     *
     * @param id the id of the etudiant
     * @return the list of entities
     */
    @Override
    public List<Stage> findAllByEtudiant(Long id) {
        log.debug("Request to get all Stages for Etudiant : {}",id);
        List<Stage> result = stageRepository.findAllByEtudiant(id);

        return result;
    }

    /**
     * Get the stages for entreprise id
     *
     * @param id the id of the entreprise
     * @return the list of entities
     */
    @Override
    public List<Stage> findAllByEntreprise(Long id) {
        log.debug("Request to get all Stages for Entreprise : {}",id);
        List<Stage> result = stageRepository.findAllByEntreprise(id);

        return result;
    }

    /**
     * Get the stages for enseignant id
     *
     * @param id the id of the enseignant
     * @return the list of entities
     */
    @Override
    public List<Stage> findAllByEnseignant(Long id) {
        log.debug("Request to get all Stages for Enseignant : {}",id);
        List<Stage> result = stageRepository.findAllByEnseignant(id);

        return result;
    }

    /**
     * Get the stages for contact id
     *
     * @param id the id of the contact
     * @return the list of entities
     */
    @Override
    public List<Stage> findAllByContact(Long id) {
        log.debug("Request to get all Stages for Contact : {}",id);
        List<Stage> result = stageRepository.findAllByContact(id);

        return result;
    }
}
