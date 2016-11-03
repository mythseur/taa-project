package fr.istic.taa.service.impl;

import fr.istic.taa.domain.DonneesEtudiant;
import fr.istic.taa.domain.Etudiant;
import fr.istic.taa.domain.Stage;
import fr.istic.taa.dto.EntrepriseIHM;
import fr.istic.taa.dto.EtudiantIHM;
import fr.istic.taa.dto.StageIHM;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
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


    /**
     * Save a stage.
     *
     * @param stage the entity to save
     * @return the persisted entity
     */
    public StageIHM save(StageIHM stage) {
        log.debug("Request to save Stage : {}", stage);

        Stage st = stage.createStage();

        Stage result = stageRepository.save(st);
        stageSearchRepository.save(result);
        return StageIHM.create(result);
    }

    /**
     * Get all the stages.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StageIHM> findAll() {
        log.debug("Request to get all Stages");
        List<Stage> stages = stageRepository.findAll();
        List<StageIHM> result = new ArrayList<>();

        for (Stage stage : stages) {
            StageIHM stageIHM = createStageIHMfromStage(stage);
            result.add(stageIHM);
        }

        return result;
    }

    /**
     * Get one stage by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public StageIHM findOne(Long id) {
        log.debug("Request to get Stage : {}", id);
        Stage stage = stageRepository.findOne(id);
        return stage == null ? null : createStageIHMfromStage(stage);
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
    public List<StageIHM> search(String query) {
        log.debug("Request to search Stages for query {}", query);
        List<Stage> stages = StreamSupport
            .stream(stageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());

        List<StageIHM> stageIHMs = new ArrayList<>();
        for(Stage stage : stages){
            stageIHMs.add(createStageIHMfromStage(stage));
        }
        return stageIHMs;
    }

    /**
     * Get the stages for etudiant id
     *
     * @param id the id of the etudiant
     * @return the list of entities
     */
    @Override
    public List<StageIHM> findAllByEtudiant(Long id) {
        log.debug("Request to get all Stages for Etudiant : {}",id);
        List<Stage> stages = stageRepository.findAllByEtudiant(id);

        List<StageIHM> stageIHMs = new ArrayList<>();
        for(Stage stage : stages){
            stageIHMs.add(createStageIHMfromStage(stage));
        }
        return stageIHMs;
    }

    /**
     * Get the stages for entreprise id
     *
     * @param id the id of the entreprise
     * @return the list of entities
     */
    @Override
    public List<StageIHM> findAllByEntreprise(Long id) {
        log.debug("Request to get all Stages for Entreprise : {}",id);
        List<Stage> stages = stageRepository.findAllByEntreprise(id);

        List<StageIHM> stageIHMs = new ArrayList<>();
        for(Stage stage : stages){
            stageIHMs.add(createStageIHMfromStage(stage));
        }
        return stageIHMs;
    }

    /**
     * Get the stages for enseignant id
     *
     * @param id the id of the enseignant
     * @return the list of entities
     */
    @Override
    public List<StageIHM> findAllByEnseignant(Long id) {
        log.debug("Request to get all Stages for Enseignant : {}",id);
        List<Stage> stages = stageRepository.findAllByEnseignant(id);

        List<StageIHM> stageIHMs = new ArrayList<>();
        for(Stage stage : stages){
            stageIHMs.add(createStageIHMfromStage(stage));
        }
        return stageIHMs;
    }

    /**
     * Get the stages for contact id
     *
     * @param id the id of the contact
     * @return the list of entities
     */
    @Override
    public List<StageIHM> findAllByContact(Long id) {
        log.debug("Request to get all Stages for Contact : {}",id);
        List<Stage> stages = stageRepository.findAllByContact(id);

        List<StageIHM> stageIHMs = new ArrayList<>();
        for(Stage stage : stages){
            stageIHMs.add(createStageIHMfromStage(stage));
        }
        return stageIHMs;
    }


    private StageIHM createStageIHMfromStage(Stage stage){

        ZonedDateTime date = stage.getDatedebut().atStartOfDay(ZoneId.systemDefault());

        Etudiant etu = etudiantRepository.findOne(stage.getEtudiant().getId());
        DonneesEtudiant don = donneesEtudiantRepository.findLastByIdEtudiantAndDate(etu.getId(), date);
        EtudiantIHM etudiant = EtudiantIHM.create(etu, don);

        EntrepriseIHM entreprise = entrepriseService.findOneByDate(stage.getEntreprise().getId(), date);

        StageIHM stageIHM = StageIHM.create(stage);

        if(etudiant != null) stageIHM.setEtudiant(etudiant);
        if(entreprise != null) stageIHM.setEntreprise(entreprise);

        return stageIHM;
    }
}
