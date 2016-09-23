package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.EtudiantDiplome;
import fr.istic.taa.repository.EtudiantDiplomeRepository;
import fr.istic.taa.repository.search.EtudiantDiplomeSearchRepository;
import fr.istic.taa.service.EtudiantDiplomeService;
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
 * Test class for the EtudiantDiplomeResource REST controller.
 *
 * @see EtudiantDiplomeResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class EtudiantDiplomeResourceIntTest {
    private static final String DEFAULT_ANNEE = "AAAAA";
    private static final String UPDATED_ANNEE = "BBBBB";

    private static final Integer DEFAULT_NOTE = 1;
    private static final Integer UPDATED_NOTE = 2;
    private static final String DEFAULT_MENTION = "AAAAA";
    private static final String UPDATED_MENTION = "BBBBB";

    @Inject
    private EtudiantDiplomeRepository etudiantDiplomeRepository;

    @Inject
    private EtudiantDiplomeService etudiantDiplomeService;

    @Inject
    private EtudiantDiplomeSearchRepository etudiantDiplomeSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEtudiantDiplomeMockMvc;

    private EtudiantDiplome etudiantDiplome;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EtudiantDiplomeResource etudiantDiplomeResource = new EtudiantDiplomeResource();
        ReflectionTestUtils.setField(etudiantDiplomeResource, "etudiantDiplomeService", etudiantDiplomeService);
        this.restEtudiantDiplomeMockMvc = MockMvcBuilders.standaloneSetup(etudiantDiplomeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EtudiantDiplome createEntity(EntityManager em) {
        EtudiantDiplome etudiantDiplome = new EtudiantDiplome();
        etudiantDiplome.setAnnee(DEFAULT_ANNEE);
        etudiantDiplome.setNote(DEFAULT_NOTE);
        etudiantDiplome.setMention(DEFAULT_MENTION);
        return etudiantDiplome;
    }

    @Before
    public void initTest() {
        etudiantDiplomeSearchRepository.deleteAll();
        etudiantDiplome = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtudiantDiplome() throws Exception {
        int databaseSizeBeforeCreate = etudiantDiplomeRepository.findAll().size();

        // Create the EtudiantDiplome

        restEtudiantDiplomeMockMvc.perform(post("/api/etudiant-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etudiantDiplome)))
            .andExpect(status().isCreated());

        // Validate the EtudiantDiplome in the database
        List<EtudiantDiplome> etudiantDiplomes = etudiantDiplomeRepository.findAll();
        assertThat(etudiantDiplomes).hasSize(databaseSizeBeforeCreate + 1);
        EtudiantDiplome testEtudiantDiplome = etudiantDiplomes.get(etudiantDiplomes.size() - 1);
        assertThat(testEtudiantDiplome.getAnnee()).isEqualTo(DEFAULT_ANNEE);
        assertThat(testEtudiantDiplome.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testEtudiantDiplome.getMention()).isEqualTo(DEFAULT_MENTION);

        // Validate the EtudiantDiplome in ElasticSearch
        EtudiantDiplome etudiantDiplomeEs = etudiantDiplomeSearchRepository.findOne(testEtudiantDiplome.getId());
        assertThat(etudiantDiplomeEs).isEqualToComparingFieldByField(testEtudiantDiplome);
    }

    @Test
    @Transactional
    public void getAllEtudiantDiplomes() throws Exception {
        // Initialize the database
        etudiantDiplomeRepository.saveAndFlush(etudiantDiplome);

        // Get all the etudiantDiplomes
        restEtudiantDiplomeMockMvc.perform(get("/api/etudiant-diplomes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudiantDiplome.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION.toString())));
    }

    @Test
    @Transactional
    public void getEtudiantDiplome() throws Exception {
        // Initialize the database
        etudiantDiplomeRepository.saveAndFlush(etudiantDiplome);

        // Get the etudiantDiplome
        restEtudiantDiplomeMockMvc.perform(get("/api/etudiant-diplomes/{id}", etudiantDiplome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etudiantDiplome.getId().intValue()))
            .andExpect(jsonPath("$.annee").value(DEFAULT_ANNEE.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.mention").value(DEFAULT_MENTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEtudiantDiplome() throws Exception {
        // Get the etudiantDiplome
        restEtudiantDiplomeMockMvc.perform(get("/api/etudiant-diplomes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtudiantDiplome() throws Exception {
        // Initialize the database
        etudiantDiplomeService.save(etudiantDiplome);

        int databaseSizeBeforeUpdate = etudiantDiplomeRepository.findAll().size();

        // Update the etudiantDiplome
        EtudiantDiplome updatedEtudiantDiplome = etudiantDiplomeRepository.findOne(etudiantDiplome.getId());
        updatedEtudiantDiplome.setAnnee(UPDATED_ANNEE);
        updatedEtudiantDiplome.setNote(UPDATED_NOTE);
        updatedEtudiantDiplome.setMention(UPDATED_MENTION);

        restEtudiantDiplomeMockMvc.perform(put("/api/etudiant-diplomes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtudiantDiplome)))
            .andExpect(status().isOk());

        // Validate the EtudiantDiplome in the database
        List<EtudiantDiplome> etudiantDiplomes = etudiantDiplomeRepository.findAll();
        assertThat(etudiantDiplomes).hasSize(databaseSizeBeforeUpdate);
        EtudiantDiplome testEtudiantDiplome = etudiantDiplomes.get(etudiantDiplomes.size() - 1);
        assertThat(testEtudiantDiplome.getAnnee()).isEqualTo(UPDATED_ANNEE);
        assertThat(testEtudiantDiplome.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testEtudiantDiplome.getMention()).isEqualTo(UPDATED_MENTION);

        // Validate the EtudiantDiplome in ElasticSearch
        EtudiantDiplome etudiantDiplomeEs = etudiantDiplomeSearchRepository.findOne(testEtudiantDiplome.getId());
        assertThat(etudiantDiplomeEs).isEqualToComparingFieldByField(testEtudiantDiplome);
    }

    @Test
    @Transactional
    public void deleteEtudiantDiplome() throws Exception {
        // Initialize the database
        etudiantDiplomeService.save(etudiantDiplome);

        int databaseSizeBeforeDelete = etudiantDiplomeRepository.findAll().size();

        // Get the etudiantDiplome
        restEtudiantDiplomeMockMvc.perform(delete("/api/etudiant-diplomes/{id}", etudiantDiplome.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean etudiantDiplomeExistsInEs = etudiantDiplomeSearchRepository.exists(etudiantDiplome.getId());
        assertThat(etudiantDiplomeExistsInEs).isFalse();

        // Validate the database is empty
        List<EtudiantDiplome> etudiantDiplomes = etudiantDiplomeRepository.findAll();
        assertThat(etudiantDiplomes).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchEtudiantDiplome() throws Exception {
        // Initialize the database
        etudiantDiplomeService.save(etudiantDiplome);

        // Search the etudiantDiplome
        restEtudiantDiplomeMockMvc.perform(get("/api/_search/etudiant-diplomes?query=id:" + etudiantDiplome.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etudiantDiplome.getId().intValue())))
            .andExpect(jsonPath("$.[*].annee").value(hasItem(DEFAULT_ANNEE.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].mention").value(hasItem(DEFAULT_MENTION.toString())));
    }
}
