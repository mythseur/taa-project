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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import fr.istic.taa.domain.DonneesEtudiant;
import fr.istic.taa.service.DonneesEtudiantService;
import fr.istic.taa.web.rest.util.HeaderUtil;

/**
 * REST controller for managing DonneesEtudiant.
 */
@RestController
@RequestMapping("/api")
public class DonneesEtudiantResource {

    private final Logger log = LoggerFactory.getLogger(DonneesEtudiantResource.class);

    @Inject
    private DonneesEtudiantService donneesEtudiantService;

    /**
     * POST  /donnees-etudiants : Create a new donneesEtudiant.
     *
     * @param donneesEtudiant the donneesEtudiant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new donneesEtudiant, or with status 400 (Bad Request) if the donneesEtudiant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/donnees-etudiants",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEtudiant> createDonneesEtudiant(@RequestBody DonneesEtudiant donneesEtudiant) throws URISyntaxException {
        log.debug("REST request to save DonneesEtudiant : {}", donneesEtudiant);
        if (donneesEtudiant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("donneesEtudiant", "idexists", "A new donneesEtudiant cannot already have an ID")).body(null);
        }
        DonneesEtudiant result = donneesEtudiantService.save(donneesEtudiant);
        return ResponseEntity.created(new URI("/api/donnees-etudiants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("donneesEtudiant", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /donnees-etudiants : Updates an existing donneesEtudiant.
     *
     * @param donneesEtudiant the donneesEtudiant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated donneesEtudiant,
     * or with status 400 (Bad Request) if the donneesEtudiant is not valid,
     * or with status 500 (Internal Server Error) if the donneesEtudiant couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/donnees-etudiants",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEtudiant> updateDonneesEtudiant(@RequestBody DonneesEtudiant donneesEtudiant) throws URISyntaxException {
        log.debug("REST request to update DonneesEtudiant : {}", donneesEtudiant);
        if (donneesEtudiant.getId() == null) {
            return createDonneesEtudiant(donneesEtudiant);
        }
        DonneesEtudiant result = donneesEtudiantService.save(donneesEtudiant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("donneesEtudiant", donneesEtudiant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /donnees-etudiants : get all the donneesEtudiants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of donneesEtudiants in body
     */
    @RequestMapping(value = "/donnees-etudiants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DonneesEtudiant> getAllDonneesEtudiants() {
        log.debug("REST request to get all DonneesEtudiants");
        return donneesEtudiantService.findAll();
    }

    /**
     * GET  /donnees-etudiants/:id : get the "id" donneesEtudiant.
     *
     * @param id the id of the donneesEtudiant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the donneesEtudiant, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/donnees-etudiants/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEtudiant> getDonneesEtudiant(@PathVariable Long id) {
        log.debug("REST request to get DonneesEtudiant : {}", id);
        DonneesEtudiant donneesEtudiant = donneesEtudiantService.findOne(id);
        return Optional.ofNullable(donneesEtudiant)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /donnees-etudiants/etudiant/:id : get the last donneesEtudiant for Etudiant id.
     *
     * @param id the id of the donneesEtudiant.Etudiant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the donneesEtudiant, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/donnees-etudiants/etudiant/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEtudiant> getDonneesEtudiantByIdEtudiant(@PathVariable Long id) {
        log.debug("REST request to get DonneesEtudiant of Etudiant : {}", id);
        DonneesEtudiant donneesEtudiant = donneesEtudiantService.findLastByIdEtudiant(id);
        return Optional.ofNullable(donneesEtudiant)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /donnees-etudiants/etudiant/:id/:date : get the last donneesEtudiant for Etudiant id at the date :date.
     *
     * @param id the id of the donneesEtudiant.Etudiant to retrieve
     * @param date the date with format yyyy-MM-dd
     * @return the ResponseEntity with status 200 (OK) and with body the donneesEtudiant, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/donnees-etudiants/etudiantdate/{id}/{date}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DonneesEtudiant> getDonneesEtudiantByIdEtudiant(@PathVariable Long id, @PathVariable("date") String date) {
        log.debug("REST request to get DonneesEtudiant of Etudiant by date : {}", date);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ldate = LocalDate.parse(date, formatter);
        ZonedDateTime dateTime = ldate.atStartOfDay(ZoneId.systemDefault());

        DonneesEtudiant donneesEtudiant = donneesEtudiantService.findLastByIdEtudiantAndDate(id, dateTime);
        return Optional.ofNullable(donneesEtudiant)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /donnees-etudiants/:id : delete the "id" donneesEtudiant.
     *
     * @param id the id of the donneesEtudiant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/donnees-etudiants/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDonneesEtudiant(@PathVariable Long id) {
        log.debug("REST request to delete DonneesEtudiant : {}", id);
        donneesEtudiantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("donneesEtudiant", id.toString())).build();
    }

    /**
     * SEARCH  /_search/donnees-etudiants?query=:query : search for the donneesEtudiant corresponding
     * to the query.
     *
     * @param query the query of the donneesEtudiant search
     * @return the result of the search
     */
    @RequestMapping(value = "/_search/donnees-etudiants",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DonneesEtudiant> searchDonneesEtudiants(@RequestParam String query) {
        log.debug("REST request to search DonneesEtudiants for query {}", query);
        return donneesEtudiantService.search(query);
    }


}
