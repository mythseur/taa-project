package fr.istic.taa.web.rest;

import fr.istic.taa.TaaProjectApp;
import fr.istic.taa.domain.Filiere;
import fr.istic.taa.repository.FiliereRepository;
import fr.istic.taa.repository.search.FiliereSearchRepository;
import fr.istic.taa.service.FiliereService;
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
 * Test class for the FiliereResource REST controller.
 *
 * @see FiliereResource
 */
@RunWith(SpringRunner.class)

@SpringBootTest(classes = TaaProjectApp.class)

public class FiliereResourceIntTest {
    private static final String DEFAULT_LIBELLE = "AAAAA";
    private static final String UPDATED_LIBELLE = "BBBBB";

    @Inject
    private FiliereRepository filiereRepository;

    @Inject
    private FiliereService filiereService;

    @Inject
    private FiliereSearchRepository filiereSearchRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFiliereMockMvc;

    private Filiere filiere;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FiliereResource filiereResource = new FiliereResource();
        ReflectionTestUtils.setField(filiereResource, "filiereService", filiereService);
        this.restFiliereMockMvc = MockMvcBuilders.standaloneSetup(filiereResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Filiere createEntity(EntityManager em) {
        Filiere filiere = new Filiere();
        filiere.setLibelle(DEFAULT_LIBELLE);
        return filiere;
    }

    @Before
    public void initTest() {
        filiereSearchRepository.deleteAll();
        filiere = createEntity(em);
    }

    @Test
    @Transactional
    public void createFiliere() throws Exception {
        int databaseSizeBeforeCreate = filiereRepository.findAll().size();

        // Create the Filiere

        restFiliereMockMvc.perform(post("/api/filieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(filiere)))
            .andExpect(status().isCreated());

        // Validate the Filiere in the database
        List<Filiere> filieres = filiereRepository.findAll();
        assertThat(filieres).hasSize(databaseSizeBeforeCreate + 1);
        Filiere testFiliere = filieres.get(filieres.size() - 1);
        assertThat(testFiliere.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the Filiere in ElasticSearch
        Filiere filiereEs = filiereSearchRepository.findOne(testFiliere.getId());
        assertThat(filiereEs).isEqualToComparingFieldByField(testFiliere);
    }

    @Test
    @Transactional
    public void getAllFilieres() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);

        // Get all the filieres
        restFiliereMockMvc.perform(get("/api/filieres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }

    @Test
    @Transactional
    public void getFiliere() throws Exception {
        // Initialize the database
        filiereRepository.saveAndFlush(filiere);

        // Get the filiere
        restFiliereMockMvc.perform(get("/api/filieres/{id}", filiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(filiere.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFiliere() throws Exception {
        // Get the filiere
        restFiliereMockMvc.perform(get("/api/filieres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFiliere() throws Exception {
        // Initialize the database
        filiereService.save(filiere);

        int databaseSizeBeforeUpdate = filiereRepository.findAll().size();

        // Update the filiere
        Filiere updatedFiliere = filiereRepository.findOne(filiere.getId());
        updatedFiliere.setLibelle(UPDATED_LIBELLE);

        restFiliereMockMvc.perform(put("/api/filieres")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFiliere)))
            .andExpect(status().isOk());

        // Validate the Filiere in the database
        List<Filiere> filieres = filiereRepository.findAll();
        assertThat(filieres).hasSize(databaseSizeBeforeUpdate);
        Filiere testFiliere = filieres.get(filieres.size() - 1);
        assertThat(testFiliere.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the Filiere in ElasticSearch
        Filiere filiereEs = filiereSearchRepository.findOne(testFiliere.getId());
        assertThat(filiereEs).isEqualToComparingFieldByField(testFiliere);
    }

    @Test
    @Transactional
    public void deleteFiliere() throws Exception {
        // Initialize the database
        filiereService.save(filiere);

        int databaseSizeBeforeDelete = filiereRepository.findAll().size();

        // Get the filiere
        restFiliereMockMvc.perform(delete("/api/filieres/{id}", filiere.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate ElasticSearch is empty
        boolean filiereExistsInEs = filiereSearchRepository.exists(filiere.getId());
        assertThat(filiereExistsInEs).isFalse();

        // Validate the database is empty
        List<Filiere> filieres = filiereRepository.findAll();
        assertThat(filieres).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchFiliere() throws Exception {
        // Initialize the database
        filiereService.save(filiere);

        // Search the filiere
        restFiliereMockMvc.perform(get("/api/_search/filieres?query=id:" + filiere.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(filiere.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE.toString())));
    }
}
