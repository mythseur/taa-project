package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.Alternance;
import fr.istic.taa.repository.AlternanceRepository;
import fr.istic.taa.repository.search.AlternanceSearchRepository;
import fr.istic.taa.service.AlternanceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AlternanceResource REST controller.
 *
 * @see AlternanceResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class AlternanceResourceIntTest {

    private static final LocalDate DEFAULT_DATEDEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEDEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATEFIN = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATEFIN = LocalDate.now(ZoneId.systemDefault());
    private static final String DEFAULT_SUJET = "AAAAA";
    private static final String UPDATED_SUJET = "BBBBB";
    private static final String DEFAULT_SERVICE = "AAAAA";
    private static final String UPDATED_SERVICE = "BBBBB";
    private static final String DEFAULT_DETAILS = "AAAAA";
    private static final String UPDATED_DETAILS = "BBBBB";

    private static final Integer DEFAULT_JOURS = 1;
    private static final Integer UPDATED_JOURS = 2;

    private static final Integer DEFAULT_HEURES = 1;
    private static final Integer UPDATED_HEURES = 2;

    private static final Integer DEFAULT_VERSEMENT = 1;
    private static final Integer UPDATED_VERSEMENT = 2;

    @Inject
    private AlternanceRepository alternanceRepository;

    @Inject
    private AlternanceService alternanceService;

    @Inject
    private AlternanceSearchRepository alternanceSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAlternanceMockMvc;

    private Alternance alternance;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlternanceResource alternanceResource = new AlternanceResource();
        ReflectionTestUtils.setField(alternanceResource, "alternanceService", alternanceService);
        this.restAlternanceMockMvc = MockMvcBuilders.standaloneSetup(alternanceResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Alternance createEntity(EntityManager em) {
        Alternance alternance = new Alternance();
        alternance.setDatedebut(DEFAULT_DATEDEBUT);
        alternance.setDatefin(DEFAULT_DATEFIN);
        alternance.setSujet(DEFAULT_SUJET);
        alternance.setService(DEFAULT_SERVICE);
        alternance.setDetails(DEFAULT_DETAILS);
        alternance.setJours(DEFAULT_JOURS);
        alternance.setHeures(DEFAULT_HEURES);
        alternance.setVersement(DEFAULT_VERSEMENT);
        return alternance;
    }

    @Before
    public void initTest() {
        alternanceSearchRepository.deleteAll();
        alternance = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlternance() throws Exception {
        int databaseSizeBeforeCreate = alternanceRepository.findAll().size();

        // Create the Alternance

        restAlternanceMockMvc.perform(post("/api/alternances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alternance)))
            .andExpect(status().isCreated());

        // Validate the Alternance in the database
        List<Alternance> alternances = alternanceRepository.findAll();
        assertThat(alternances).hasSize(databaseSizeBeforeCreate + 1);
        Alternance testAlternance = alternances.get(alternances.size() - 1);
        assertThat(testAlternance.getDatedebut()).isEqualTo(DEFAULT_DATEDEBUT);
        assertThat(testAlternance.getDatefin()).isEqualTo(DEFAULT_DATEFIN);
        assertThat(testAlternance.getSujet()).isEqualTo(DEFAULT_SUJET);
        assertThat(testAlternance.getService()).isEqualTo(DEFAULT_SERVICE);
        assertThat(testAlternance.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testAlternance.getJours()).isEqualTo(DEFAULT_JOURS);
        assertThat(testAlternance.getHeures()).isEqualTo(DEFAULT_HEURES);
        assertThat(testAlternance.getVersement()).isEqualTo(DEFAULT_VERSEMENT);

        // Validate the Alternance in ElasticSearch
        Alternance alternanceEs = alternanceSearchRepository.findOne(testAlternance.getId());
        assertThat(alternanceEs).isEqualToComparingFieldByField(testAlternance);
    }

    @Test
    @Transactional
    public void getAllAlternances() throws Exception {
        // Initialize the database
        alternanceRepository.saveAndFlush(alternance);

        // Get all the alternances
        restAlternanceMockMvc.perform(get("/api/alternances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alternance.getId().intValue())))
            .andExpect(jsonPath("$.[*].datedebut").value(hasItem(DEFAULT_DATEDEBUT.toString())))
            .andExpect(jsonPath("$.[*].datefin").value(hasItem(DEFAULT_DATEFIN.toString())))
            .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET.toString())))
            .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].jours").value(hasItem(DEFAULT_JOURS)))
            .andExpect(jsonPath("$.[*].heures").value(hasItem(DEFAULT_HEURES)))
            .andExpect(jsonPath("$.[*].versement").value(hasItem(DEFAULT_VERSEMENT)));
    }

    @Test
    @Transactional
    public void getAlternance() throws Exception {
        // Initialize the database
        alternanceRepository.saveAndFlush(alternance);

        // Get the alternance
        restAlternanceMockMvc.perform(get("/api/alternances/{id}", alternance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alternance.getId().intValue()))
            .andExpect(jsonPath("$.datedebut").value(DEFAULT_DATEDEBUT.toString()))
            .andExpect(jsonPath("$.datefin").value(DEFAULT_DATEFIN.toString()))
            .andExpect(jsonPath("$.sujet").value(DEFAULT_SUJET.toString()))
            .andExpect(jsonPath("$.service").value(DEFAULT_SERVICE.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.jours").value(DEFAULT_JOURS))
            .andExpect(jsonPath("$.heures").value(DEFAULT_HEURES))
            .andExpect(jsonPath("$.versement").value(DEFAULT_VERSEMENT));
    }

    @Test
    @Transactional
    public void getNonExistingAlternance() throws Exception {
        // Get the alternance
        restAlternanceMockMvc.perform(get("/api/alternances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlternance() throws Exception {
        // Initialize the database
        alternanceService.save(alternance);

        int databaseSizeBeforeUpdate = alternanceRepository.findAll().size();

        // Update the alternance
        Alternance updatedAlternance = alternanceRepository.findOne(alternance.getId());
        updatedAlternance.setDatedebut(UPDATED_DATEDEBUT);
        updatedAlternance.setDatefin(UPDATED_DATEFIN);
        updatedAlternance.setSujet(UPDATED_SUJET);
        updatedAlternance.setService(UPDATED_SERVICE);
        updatedAlternance.setDetails(UPDATED_DETAILS);
        updatedAlternance.setJours(UPDATED_JOURS);
        updatedAlternance.setHeures(UPDATED_HEURES);
        updatedAlternance.setVersement(UPDATED_VERSEMENT);

        restAlternanceMockMvc.perform(put("/api/alternances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlternance)))
            .andExpect(status().isOk());

        // Validate the Alternance in the database
        List<Alternance> alternances = alternanceRepository.findAll();
        assertThat(alternances).hasSize(databaseSizeBeforeUpdate);
        Alternance testAlternance = alternances.get(alternances.size() - 1);
        assertThat(testAlternance.getDatedebut()).isEqualTo(UPDATED_DATEDEBUT);
        assertThat(testAlternance.getDatefin()).isEqualTo(UPDATED_DATEFIN);
        assertThat(testAlternance.getSujet()).isEqualTo(UPDATED_SUJET);
        assertThat(testAlternance.getService()).isEqualTo(UPDATED_SERVICE);
        assertThat(testAlternance.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testAlternance.getJours()).isEqualTo(UPDATED_JOURS);
        assertThat(testAlternance.getHeures()).isEqualTo(UPDATED_HEURES);
        assertThat(testAlternance.getVersement()).isEqualTo(UPDATED_VERSEMENT);

        // Validate the Alternance in ElasticSearch
        Alternance alternanceEs = alternanceSearchRepository.findOne(testAlternance.getId());
        assertThat(alternanceEs).isEqualToComparingFieldByField(testAlternance);
    }

    @Test
    @Transactional
    public void deleteAlternance() throws Exception {
        // Initialize the database
        alternanceService.save(alternance);

        int databaseSizeBeforeDelete = alternanceRepository.findAll().size();

        // Get the alternance
        restAlternanceMockMvc.perform(delete("/api/alternances/{id}", alternance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean alternanceExistsInEs = alternanceSearchRepository.exists(alternance.getId());
        assertThat(alternanceExistsInEs).isFalse();

        // Validate the database is empty
        List<Alternance> alternances = alternanceRepository.findAll();
        assertThat(alternances).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchAlternance() throws Exception {
        // Initialize the database
        alternanceService.save(alternance);

        // Search the alternance
        restAlternanceMockMvc.perform(get("/api/_search/alternances?query=id:" + alternance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alternance.getId().intValue())))
            .andExpect(jsonPath("$.[*].datedebut").value(hasItem(DEFAULT_DATEDEBUT.toString())))
            .andExpect(jsonPath("$.[*].datefin").value(hasItem(DEFAULT_DATEFIN.toString())))
            .andExpect(jsonPath("$.[*].sujet").value(hasItem(DEFAULT_SUJET.toString())))
            .andExpect(jsonPath("$.[*].service").value(hasItem(DEFAULT_SERVICE.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].jours").value(hasItem(DEFAULT_JOURS)))
            .andExpect(jsonPath("$.[*].heures").value(hasItem(DEFAULT_HEURES)))
            .andExpect(jsonPath("$.[*].versement").value(hasItem(DEFAULT_VERSEMENT)));
    }
}
