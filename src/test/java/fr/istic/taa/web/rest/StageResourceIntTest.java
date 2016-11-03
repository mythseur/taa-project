package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.Stage;
import fr.istic.taa.repository.StageRepository;
import fr.istic.taa.repository.search.StageSearchRepository;
import fr.istic.taa.service.StageService;
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
 * Test class for the StageResource REST controller.
 *
 * @see StageResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class StageResourceIntTest {

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
    private StageRepository stageRepository;

    @Inject
    private StageService stageService;

    @Inject
    private StageSearchRepository stageSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restStageMockMvc;

    private Stage stage;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StageResource stageResource = new StageResource();
        ReflectionTestUtils.setField(stageResource, "stageService", stageService);
        this.restStageMockMvc = MockMvcBuilders.standaloneSetup(stageResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Stage createEntity(EntityManager em) {
        Stage stage = new Stage();
        stage.setDatedebut(DEFAULT_DATEDEBUT);
        stage.setDatefin(DEFAULT_DATEFIN);
        stage.setSujet(DEFAULT_SUJET);
        stage.setService(DEFAULT_SERVICE);
        stage.setDetails(DEFAULT_DETAILS);
        stage.setJours(DEFAULT_JOURS);
        stage.setHeures(DEFAULT_HEURES);
        stage.setVersement(DEFAULT_VERSEMENT);
        return stage;
    }

    @Before
    public void initTest() {
        stageSearchRepository.deleteAll();
        stage = createEntity(em);
    }

    @Test
    @Transactional
    public void createStage() throws Exception {
        int databaseSizeBeforeCreate = stageRepository.findAll().size();

        // Create the Stage

        restStageMockMvc.perform(post("/api/stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stage)))
            .andExpect(status().isCreated());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeCreate + 1);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getDatedebut()).isEqualTo(DEFAULT_DATEDEBUT);
        assertThat(testStage.getDatefin()).isEqualTo(DEFAULT_DATEFIN);
        assertThat(testStage.getSujet()).isEqualTo(DEFAULT_SUJET);
        assertThat(testStage.getService()).isEqualTo(DEFAULT_SERVICE);
        assertThat(testStage.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testStage.getJours()).isEqualTo(DEFAULT_JOURS);
        assertThat(testStage.getHeures()).isEqualTo(DEFAULT_HEURES);
        assertThat(testStage.getVersement()).isEqualTo(DEFAULT_VERSEMENT);

        // Validate the Stage in ElasticSearch
        Stage stageEs = stageSearchRepository.findOne(testStage.getId());
        assertThat(stageEs).isEqualToComparingFieldByField(testStage);
    }

    @Test
    @Transactional
    public void getAllStages() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get all the stages
        restStageMockMvc.perform(get("/api/stages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
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
    public void getStage() throws Exception {
        // Initialize the database
        stageRepository.saveAndFlush(stage);

        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stage.getId().intValue()))
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
    public void getNonExistingStage() throws Exception {
        // Get the stage
        restStageMockMvc.perform(get("/api/stages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStage() throws Exception {
        // Initialize the database
//        stageService.save(stage);

        int databaseSizeBeforeUpdate = stageRepository.findAll().size();

        // Update the stage
        Stage updatedStage = stageRepository.findOne(stage.getId());
        updatedStage.setDatedebut(UPDATED_DATEDEBUT);
        updatedStage.setDatefin(UPDATED_DATEFIN);
        updatedStage.setSujet(UPDATED_SUJET);
        updatedStage.setService(UPDATED_SERVICE);
        updatedStage.setDetails(UPDATED_DETAILS);
        updatedStage.setJours(UPDATED_JOURS);
        updatedStage.setHeures(UPDATED_HEURES);
        updatedStage.setVersement(UPDATED_VERSEMENT);

        restStageMockMvc.perform(put("/api/stages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStage)))
            .andExpect(status().isOk());

        // Validate the Stage in the database
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeUpdate);
        Stage testStage = stages.get(stages.size() - 1);
        assertThat(testStage.getDatedebut()).isEqualTo(UPDATED_DATEDEBUT);
        assertThat(testStage.getDatefin()).isEqualTo(UPDATED_DATEFIN);
        assertThat(testStage.getSujet()).isEqualTo(UPDATED_SUJET);
        assertThat(testStage.getService()).isEqualTo(UPDATED_SERVICE);
        assertThat(testStage.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testStage.getJours()).isEqualTo(UPDATED_JOURS);
        assertThat(testStage.getHeures()).isEqualTo(UPDATED_HEURES);
        assertThat(testStage.getVersement()).isEqualTo(UPDATED_VERSEMENT);

        // Validate the Stage in ElasticSearch
        Stage stageEs = stageSearchRepository.findOne(testStage.getId());
        assertThat(stageEs).isEqualToComparingFieldByField(testStage);
    }

    @Test
    @Transactional
    public void deleteStage() throws Exception {
        // Initialize the database
//        stageService.save(stage);

        int databaseSizeBeforeDelete = stageRepository.findAll().size();

        // Get the stage
        restStageMockMvc.perform(delete("/api/stages/{id}", stage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean stageExistsInEs = stageSearchRepository.exists(stage.getId());
        assertThat(stageExistsInEs).isFalse();

        // Validate the database is empty
        List<Stage> stages = stageRepository.findAll();
        assertThat(stages).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchStage() throws Exception {
        // Initialize the database
//        stageService.save(stage);

        // Search the stage
        restStageMockMvc.perform(get("/api/_search/stages?query=id:" + stage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stage.getId().intValue())))
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
