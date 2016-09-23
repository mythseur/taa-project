package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.Diplome;
import fr.istic.taa.repository.DiplomeRepository;
import fr.istic.taa.repository.search.DiplomeSearchRepository;
import fr.istic.taa.service.DiplomeService;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DiplomeResource REST controller.
 *
 * @see DiplomeResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class DiplomeResourceIntTest {
    private static final String DEFAULT_LIBELLE = "AAAAA";
    private static final String UPDATED_LIBELLE = "BBBBB";

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;
    private static final String DEFAULT_LIBELLECOURT = "AAAAA";
    private static final String UPDATED_LIBELLECOURT = "BBBBB";

    @Inject
    private DiplomeRepository diplomeRepository;

    @Inject
    private DiplomeService diplomeService;

    @Inject
    private DiplomeSearchRepository diplomeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restDiplomeMockMvc;

    private Diplome diplome;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DiplomeResource diplomeResource = new DiplomeResource();
        ReflectionTestUtils.setField(diplomeResource, "diplomeService", diplomeService);
        this.restDiplomeMockMvc = MockMvcBuilders.standaloneSetup(diplomeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Diplome createEntity(EntityManager em) {
        Diplome diplome = new Diplome();
        diplome.setLibelle(DEFAULT_LIBELLE);
        diplome.setDuree(DEFAULT_DUREE);
        diplome.setLibellecourt(DEFAULT_LIBELLECOURT);
        return diplome;
    }

    @Before
    public void initTest() {
        diplomeSearchRepository.deleteAll();
        diplome = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiplome() throws Exception {
        int databaseSizeBeforeCreate = diplomeRepository.findAll().size();

        // Create the Diplome

        restDiplomeMockMvc.perform(post("/api/diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(diplome)))
            .andExpect(status().isCreated());

        // Validate the Diplome in the database
        List<Diplome> diplomes = diplomeRepository.findAll();
        assertThat(diplomes).hasSize(databaseSizeBeforeCreate + 1);
        Diplome testDiplome = diplomes.get(diplomes.size() - 1);
        assertThat(testDiplome.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testDiplome.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testDiplome.getLibellecourt()).isEqualTo(DEFAULT_LIBELLECOURT);

        // Validate the Diplome in ElasticSearch
        Diplome diplomeEs = diplomeSearchRepository.findOne(testDiplome.getId());
        assertThat(diplomeEs).isEqualToComparingFieldByField(testDiplome);
    }

    @Test
    @Transactional
    public void getAllDiplomes() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get all the diplomes
        restDiplomeMockMvc.perform(get("/api/diplomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diplome.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].libellecourt").value(hasItem(DEFAULT_LIBELLECOURT.toString())));
    }

    @Test
    @Transactional
    public void getDiplome() throws Exception {
        // Initialize the database
        diplomeRepository.saveAndFlush(diplome);

        // Get the diplome
        restDiplomeMockMvc.perform(get("/api/diplomes/{id}", diplome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(diplome.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.libellecourt").value(DEFAULT_LIBELLECOURT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDiplome() throws Exception {
        // Get the diplome
        restDiplomeMockMvc.perform(get("/api/diplomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiplome() throws Exception {
        // Initialize the database
        diplomeService.save(diplome);

        int databaseSizeBeforeUpdate = diplomeRepository.findAll().size();

        // Update the diplome
        Diplome updatedDiplome = diplomeRepository.findOne(diplome.getId());
        updatedDiplome.setLibelle(UPDATED_LIBELLE);
        updatedDiplome.setDuree(UPDATED_DUREE);
        updatedDiplome.setLibellecourt(UPDATED_LIBELLECOURT);

        restDiplomeMockMvc.perform(put("/api/diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiplome)))
            .andExpect(status().isOk());

        // Validate the Diplome in the database
        List<Diplome> diplomes = diplomeRepository.findAll();
        assertThat(diplomes).hasSize(databaseSizeBeforeUpdate);
        Diplome testDiplome = diplomes.get(diplomes.size() - 1);
        assertThat(testDiplome.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testDiplome.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testDiplome.getLibellecourt()).isEqualTo(UPDATED_LIBELLECOURT);

        // Validate the Diplome in ElasticSearch
        Diplome diplomeEs = diplomeSearchRepository.findOne(testDiplome.getId());
        assertThat(diplomeEs).isEqualToComparingFieldByField(testDiplome);
    }

    @Test
    @Transactional
    public void deleteDiplome() throws Exception {
        // Initialize the database
        diplomeService.save(diplome);

        int databaseSizeBeforeDelete = diplomeRepository.findAll().size();

        // Get the diplome
        restDiplomeMockMvc.perform(delete("/api/diplomes/{id}", diplome.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean diplomeExistsInEs = diplomeSearchRepository.exists(diplome.getId());
        assertThat(diplomeExistsInEs).isFalse();

        // Validate the database is empty
        List<Diplome> diplomes = diplomeRepository.findAll();
        assertThat(diplomes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDiplome() throws Exception {
        // Initialize the database
        diplomeService.save(diplome);

        // Search the diplome
        restDiplomeMockMvc.perform(get("/api/_search/diplomes?query=id:" + diplome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(diplome.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].libellecourt").value(hasItem(DEFAULT_LIBELLECOURT.toString())));
    }
}
