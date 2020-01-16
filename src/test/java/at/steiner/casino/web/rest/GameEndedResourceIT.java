package at.steiner.casino.web.rest;

import at.steiner.casino.CasinoApp;
import at.steiner.casino.domain.GameEnded;
import at.steiner.casino.repository.GameEndedRepository;
import at.steiner.casino.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static at.steiner.casino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GameEndedResource} REST controller.
 */
@SpringBootTest(classes = CasinoApp.class)
public class GameEndedResourceIT {

    @Autowired
    private GameEndedRepository gameEndedRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restGameEndedMockMvc;

    private GameEnded gameEnded;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GameEndedResource gameEndedResource = new GameEndedResource(gameEndedRepository);
        this.restGameEndedMockMvc = MockMvcBuilders.standaloneSetup(gameEndedResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameEnded createEntity(EntityManager em) {
        GameEnded gameEnded = new GameEnded();
        return gameEnded;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GameEnded createUpdatedEntity(EntityManager em) {
        GameEnded gameEnded = new GameEnded();
        return gameEnded;
    }

    @BeforeEach
    public void initTest() {
        gameEnded = createEntity(em);
    }

    @Test
    @Transactional
    public void createGameEnded() throws Exception {
        int databaseSizeBeforeCreate = gameEndedRepository.findAll().size();

        // Create the GameEnded
        restGameEndedMockMvc.perform(post("/api/game-endeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameEnded)))
            .andExpect(status().isCreated());

        // Validate the GameEnded in the database
        List<GameEnded> gameEndedList = gameEndedRepository.findAll();
        assertThat(gameEndedList).hasSize(databaseSizeBeforeCreate + 1);
        GameEnded testGameEnded = gameEndedList.get(gameEndedList.size() - 1);
    }

    @Test
    @Transactional
    public void createGameEndedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gameEndedRepository.findAll().size();

        // Create the GameEnded with an existing ID
        gameEnded.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGameEndedMockMvc.perform(post("/api/game-endeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameEnded)))
            .andExpect(status().isBadRequest());

        // Validate the GameEnded in the database
        List<GameEnded> gameEndedList = gameEndedRepository.findAll();
        assertThat(gameEndedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGameEndeds() throws Exception {
        // Initialize the database
        gameEndedRepository.saveAndFlush(gameEnded);

        // Get all the gameEndedList
        restGameEndedMockMvc.perform(get("/api/game-endeds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gameEnded.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getGameEnded() throws Exception {
        // Initialize the database
        gameEndedRepository.saveAndFlush(gameEnded);

        // Get the gameEnded
        restGameEndedMockMvc.perform(get("/api/game-endeds/{id}", gameEnded.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gameEnded.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGameEnded() throws Exception {
        // Get the gameEnded
        restGameEndedMockMvc.perform(get("/api/game-endeds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGameEnded() throws Exception {
        // Initialize the database
        gameEndedRepository.saveAndFlush(gameEnded);

        int databaseSizeBeforeUpdate = gameEndedRepository.findAll().size();

        // Update the gameEnded
        GameEnded updatedGameEnded = gameEndedRepository.findById(gameEnded.getId()).get();
        // Disconnect from session so that the updates on updatedGameEnded are not directly saved in db
        em.detach(updatedGameEnded);

        restGameEndedMockMvc.perform(put("/api/game-endeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGameEnded)))
            .andExpect(status().isOk());

        // Validate the GameEnded in the database
        List<GameEnded> gameEndedList = gameEndedRepository.findAll();
        assertThat(gameEndedList).hasSize(databaseSizeBeforeUpdate);
        GameEnded testGameEnded = gameEndedList.get(gameEndedList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGameEnded() throws Exception {
        int databaseSizeBeforeUpdate = gameEndedRepository.findAll().size();

        // Create the GameEnded

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGameEndedMockMvc.perform(put("/api/game-endeds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gameEnded)))
            .andExpect(status().isBadRequest());

        // Validate the GameEnded in the database
        List<GameEnded> gameEndedList = gameEndedRepository.findAll();
        assertThat(gameEndedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGameEnded() throws Exception {
        // Initialize the database
        gameEndedRepository.saveAndFlush(gameEnded);

        int databaseSizeBeforeDelete = gameEndedRepository.findAll().size();

        // Delete the gameEnded
        restGameEndedMockMvc.perform(delete("/api/game-endeds/{id}", gameEnded.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GameEnded> gameEndedList = gameEndedRepository.findAll();
        assertThat(gameEndedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
