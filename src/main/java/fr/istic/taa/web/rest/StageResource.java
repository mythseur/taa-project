package fr.istic.taa.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.taa.domain.*;
import fr.istic.taa.dto.EntrepriseIHM;
import fr.istic.taa.dto.EtudiantIHM;
import fr.istic.taa.dto.StageIHM;
import fr.istic.taa.service.*;
import fr.istic.taa.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Stage.
 */
@RestController
@RequestMapping("/api")
public class StageResource {

    private final Logger log = LoggerFactory.getLogger(StageResource.class);

    @Inject
    private StageService stageService;

    @Inject
    private EtudiantService etudiantService;

    @Inject
    private DonneesEtudiantService donneesEtudiantService;

    @Inject
    private EntrepriseService entrepriseService;

    @Inject
    private DonneesEntrepriseService donneesEntrepriseService;

    /**
     * POST  /stages : Create a new stage.
     *
     * @param stage the stage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new stage, or with status 400 (Bad Request) if the stage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stages",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StageIHM> createStage(@RequestBody StageIHM stage) throws URISyntaxException {
        log.debug("REST request to save Stage : {}", stage);
        if (stage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("stage", "idexists", "A new stage cannot already have an ID")).body(null);
        }
        Stage result = stageService.save(stage.createStage());
        return ResponseEntity.created(new URI("/api/stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stage", result.getId().toString()))
            .body(createStageIHMfromStage(result));
    }

    /**
     * PUT  /stages : Updates an existing stage.
     *
     * @param stage the stage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated stage,
     * or with status 400 (Bad Request) if the stage is not valid,
     * or with status 500 (Internal Server Error) if the stage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/stages",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StageIHM> updateStage(@RequestBody StageIHM stage) throws URISyntaxException {
        log.debug("REST request to update Stage : {}", stage);
        if (stage.getId() == null) {
            return createStage(stage);
        }
        Stage result = stageService.save(stage.createStage());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stage", stage.getId().toString()))
            .body(createStageIHMfromStage(result));
    }

    /**
     * GET  /stages : get all the stages.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @RequestMapping(value = "/stages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StageIHM> getAllStages() {
        log.debug("REST request to get all Stages");
        return stageService.findAll().stream()
            .map(this::createStageIHMfromStage)
            .collect(Collectors.toList());
    }

    /**
     * GET  /stages/:id : get the "id" stage.
     *
     * @param id the id of the stage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the stage, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/stages/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StageIHM> getStage(@PathVariable Long id) {
        log.debug("REST request to get Stage : {}", id);
        Stage stage = stageService.findOne(id);
        return Optional.ofNullable(createStageIHMfromStage(stage))
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stages/:id : delete the "id" stage.
     *
     * @param id the id of the stage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/stages/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStage(@PathVariable Long id) {
        log.debug("REST request to delete Stage : {}", id);
        stageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("stage", id.toString())).build();
    }

    /**
     * SEARCH  /_search/stages?query=:query : search for the stage corresponding
     * to the query.
     *
     * @param query the query of the stage search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/stages",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StageIHM> searchStages(@RequestParam String query) {
        log.debug("REST request to search Stages for query {}", query);
        return stageService.search(query).stream()
            .map(this::createStageIHMfromStage)
            .collect(Collectors.toList());
    }

    /**
     * GET  /stages/etudiant/:id : get all the stages of etudiant "id".
     *
     * @param id the id of the Etudiant
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @RequestMapping(value = "/stages/etudiant/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StageIHM> getAllStagesByEtudiant(@PathVariable Long id) {
        log.debug("REST request to get all Stages for Etudiant : {}",id);
        return stageService.findAllByEtudiant(id).stream()
            .map(this::createStageIHMfromStage)
            .collect(Collectors.toList());
    }

    /**
     * GET  /stages/entreprise/:id : get all the stages of entreprise "id".
     *
     * @param id the id of the Entreprise
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @RequestMapping(value = "/stages/entreprise/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StageIHM> getAllStagesByEntreprise(@PathVariable Long id) {
        log.debug("REST request to get all Stages for Entreprise : {}",id);
        return stageService.findAllByEntreprise(id).stream()
            .map(this::createStageIHMfromStage)
            .collect(Collectors.toList());
    }

    /**
     * GET  /stages/enseignant/:id : get all the stages of enseignant "id".
     *
     * @param id the id of the Enseignant
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @RequestMapping(value = "/stages/enseignant/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StageIHM> getAllStagesByEnseignant(@PathVariable Long id) {
        log.debug("REST request to get all Stages for Enseignant : {}",id);
        return stageService.findAllByEnseignant(id).stream()
            .map(this::createStageIHMfromStage)
            .collect(Collectors.toList());
    }

    /**
     * GET  /stages/contact/:id : get all the stages of contact "id".
     *
     * @param id the id of the Contact
     * @return the ResponseEntity with status 200 (OK) and the list of stages in body
     */
    @RequestMapping(value = "/stages/contact/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StageIHM> getAllStagesByContact(@PathVariable Long id) {
        log.debug("REST request to get all Stages for Contact : {}",id);
        return stageService.findAllByContact(id).stream()
            .map(this::createStageIHMfromStage)
            .collect(Collectors.toList());
    }


    private StageIHM createStageIHMfromStage(Stage stage) {

        if (stage == null)
            return null;

        ZonedDateTime date = stage.getDatedebut().atStartOfDay(ZoneId.systemDefault());
        StageIHM stageIHM = StageIHM.create(stage);

        if (stage.getEtudiant() != null) {

            Etudiant etu = etudiantService.findOne(stage.getEtudiant().getId());
            DonneesEtudiant don = donneesEtudiantService.findLastByIdEtudiantAndDate(etu.getId(), date);
            EtudiantIHM etudiant = EtudiantIHM.create(etu, don);
            stageIHM.setEtudiant(etudiant);

        }

        if (stage.getEntreprise() != null) {
            Entreprise entr = entrepriseService.findOne(stage.getEntreprise().getId());
            DonneesEntreprise donEntr = donneesEntrepriseService.findLastByIdEntrepriseAndDate(stage.getEntreprise().getId(),
                date);
            EntrepriseIHM entreprise = EntrepriseIHM.create(entr, donEntr);
            stageIHM.setEntreprise(entreprise);
        }
        return stageIHM;
    }


}
