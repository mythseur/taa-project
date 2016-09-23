package fr.istic.taa.web.rest;

import com.codahale.metrics.annotation.Timed;
import fr.istic.taa.domain.Alternance;
import fr.istic.taa.service.AlternanceService;
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
 * REST controller for managing Alternance.
 */
@RestController
@RequestMapping("/api")
public class AlternanceResource {

    private final Logger log = LoggerFactory.getLogger(AlternanceResource.class);

    @Inject
    private AlternanceService alternanceService;

    /**
     * POST  /alternances : Create a new alternance.
     *
     * @param alternance the alternance to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alternance, or with status 400 (Bad Request) if the alternance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/alternances",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Alternance> createAlternance(@RequestBody Alternance alternance) throws URISyntaxException {
        log.debug("REST request to save Alternance : {}", alternance);
        if (alternance.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alternance", "idexists", "A new alternance cannot already have an ID")).body(null);
        }
        Alternance result = alternanceService.save(alternance);
        return ResponseEntity.created(new URI("/api/alternances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alternance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alternances : Updates an existing alternance.
     *
     * @param alternance the alternance to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alternance,
     * or with status 400 (Bad Request) if the alternance is not valid,
     * or with status 500 (Internal Server Error) if the alternance couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/alternances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Alternance> updateAlternance(@RequestBody Alternance alternance) throws URISyntaxException {
        log.debug("REST request to update Alternance : {}", alternance);
        if (alternance.getId() == null) {
            return createAlternance(alternance);
        }
        Alternance result = alternanceService.save(alternance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alternance", alternance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alternances : get all the alternances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alternances in body
     */
    @RequestMapping(value = "/alternances",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Alternance> getAllAlternances() {
        log.debug("REST request to get all Alternances");
        return alternanceService.findAll();
    }

    /**
     * GET  /alternances/:id : get the "id" alternance.
     *
     * @param id the id of the alternance to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alternance, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/alternances/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Alternance> getAlternance(@PathVariable Long id) {
        log.debug("REST request to get Alternance : {}", id);
        Alternance alternance = alternanceService.findOne(id);
        return Optional.ofNullable(alternance)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alternances/:id : delete the "id" alternance.
     *
     * @param id the id of the alternance to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/alternances/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlternance(@PathVariable Long id) {
        log.debug("REST request to delete Alternance : {}", id);
        alternanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alternance", id.toString())).build();
    }

    /**
     * SEARCH  /_search/alternances?query=:query : search for the alternance corresponding
     * to the query.
     *
     * @param query the query of the alternance search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/alternances",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Alternance> searchAlternances(@RequestParam String query) {
        log.debug("REST request to search Alternances for query {}", query);
        return alternanceService.search(query);
    }


}
