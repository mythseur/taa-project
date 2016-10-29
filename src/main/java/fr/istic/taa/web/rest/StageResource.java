package fr.istic.taa.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import fr.istic.taa.dto.StageIHM;
import fr.istic.taa.service.StageService;
import fr.istic.taa.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Stage.
 */
@RestController
@RequestMapping("/api")
public class StageResource {

    private final Logger log = LoggerFactory.getLogger(StageResource.class);

    @Inject
    private StageService stageService;

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
        StageIHM result = stageService.save(stage);
        return ResponseEntity.created(new URI("/api/stages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("stage", result.getId().toString()))
            .body(result);
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
        StageIHM result = stageService.save(stage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("stage", stage.getId().toString()))
            .body(result);
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
        return stageService.findAll();
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
        StageIHM stage = stageService.findOne(id);
        return Optional.ofNullable(stage)
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
        return stageService.search(query);
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
        return stageService.findAllByEtudiant(id);
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
        return stageService.findAllByEntreprise(id);
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
        return stageService.findAllByEnseignant(id);
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
        return stageService.findAllByContact(id);
    }


}
