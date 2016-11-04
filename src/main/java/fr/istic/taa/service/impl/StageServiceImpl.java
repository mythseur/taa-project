package fr.istic.taa.service.impl;

import fr.istic.taa.domain.Stage;
import fr.istic.taa.repository.DonneesEntrepriseRepository;
import fr.istic.taa.repository.DonneesEtudiantRepository;
import fr.istic.taa.repository.EtudiantRepository;
import fr.istic.taa.repository.StageRepository;
import fr.istic.taa.repository.search.StageSearchRepository;
import fr.istic.taa.service.EntrepriseService;
import fr.istic.taa.service.EtudiantService;
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

    @Inject
    private EtudiantService etudiantService;

    @Inject
    private EntrepriseService entrepriseService;

    @Inject
    private EtudiantRepository etudiantRepository;

    @Inject
    private DonneesEtudiantRepository donneesEtudiantRepository;

    @Inject
    private DonneesEntrepriseRepository donneesEntrepriseRepository;


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
        return stageRepository.findAll();
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
        return stageRepository.findOne(id);
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
        return stageRepository.findAllByEtudiant(id);
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
        return stageRepository.findAllByEntreprise(id);
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
        return stageRepository.findAllByEnseignant(id);
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
        return stageRepository.findAllByContact(id);
    }
}
