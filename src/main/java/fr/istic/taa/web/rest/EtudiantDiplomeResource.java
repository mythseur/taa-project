package fr.istic.taa.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.taa.domain.EtudiantDiplome;
import fr.istic.taa.service.EtudiantDiplomeService;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EtudiantDiplome.
 */
@RestController
@RequestMapping("/api")
public class EtudiantDiplomeResource {

    private final Logger log = LoggerFactory.getLogger(EtudiantDiplomeResource.class);

    @Inject
    private EtudiantDiplomeService etudiantDiplomeService;

    /**
     * POST  /etudiant-diplomes : Create a new etudiantDiplome.
     *
     * @param etudiantDiplome the etudiantDiplome to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etudiantDiplome, or with status 400 (Bad Request) if the etudiantDiplome has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/etudiant-diplomes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EtudiantDiplome> createEtudiantDiplome(@RequestBody EtudiantDiplome etudiantDiplome) throws URISyntaxException {
        log.debug("REST request to save EtudiantDiplome : {}", etudiantDiplome);
        if (etudiantDiplome.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("etudiantDiplome", "idexists", "A new etudiantDiplome cannot already have an ID")).body(null);
        }
        EtudiantDiplome result = etudiantDiplomeService.save(etudiantDiplome);
        return ResponseEntity.created(new URI("/api/etudiant-diplomes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("etudiantDiplome", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etudiant-diplomes : Updates an existing etudiantDiplome.
     *
     * @param etudiantDiplome the etudiantDiplome to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etudiantDiplome,
     * or with status 400 (Bad Request) if the etudiantDiplome is not valid,
     * or with status 500 (Internal Server Error) if the etudiantDiplome couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/etudiant-diplomes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EtudiantDiplome> updateEtudiantDiplome(@RequestBody EtudiantDiplome etudiantDiplome) throws URISyntaxException {
        log.debug("REST request to update EtudiantDiplome : {}", etudiantDiplome);
        if (etudiantDiplome.getId() == null) {
            return createEtudiantDiplome(etudiantDiplome);
        }
        EtudiantDiplome result = etudiantDiplomeService.save(etudiantDiplome);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("etudiantDiplome", etudiantDiplome.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etudiant-diplomes : get all the etudiantDiplomes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of etudiantDiplomes in body
     */
    @RequestMapping(value = "/etudiant-diplomes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EtudiantDiplome> getAllEtudiantDiplomes() {
        log.debug("REST request to get all EtudiantDiplomes");
        return etudiantDiplomeService.findAll();
    }

    /**
     * GET  /etudiant-diplomes/:id : get the "id" etudiantDiplome.
     *
     * @param id the id of the etudiantDiplome to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etudiantDiplome, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/etudiant-diplomes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EtudiantDiplome> getEtudiantDiplome(@PathVariable Long id) {
        log.debug("REST request to get EtudiantDiplome : {}", id);
        EtudiantDiplome etudiantDiplome = etudiantDiplomeService.findOne(id);
        return Optional.ofNullable(etudiantDiplome)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /etudiant-diplomes/:id : delete the "id" etudiantDiplome.
     *
     * @param id the id of the etudiantDiplome to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/etudiant-diplomes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEtudiantDiplome(@PathVariable Long id) {
        log.debug("REST request to delete EtudiantDiplome : {}", id);
        etudiantDiplomeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("etudiantDiplome", id.toString())).build();
    }

    /**
     * SEARCH  /_search/etudiant-diplomes?query=:query : search for the etudiantDiplome corresponding
     * to the query.
     *
     * @param query the query of the etudiantDiplome search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/etudiant-diplomes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EtudiantDiplome> searchEtudiantDiplomes(@RequestParam String query) {
        log.debug("REST request to search EtudiantDiplomes for query {}", query);
        return etudiantDiplomeService.search(query);
    }


}
