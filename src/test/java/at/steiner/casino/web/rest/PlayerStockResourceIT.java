package at.steiner.casino.web.rest;

import at.steiner.casino.CasinoApp;
import at.steiner.casino.domain.PlayerStock;
import at.steiner.casino.repository.PlayerStockRepository;
import at.steiner.casino.service.PlayerStockService;
import at.steiner.casino.service.dto.PlayerStockDTO;
import at.steiner.casino.service.mapper.PlayerStockMapper;
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
 * Integration tests for the {@link PlayerStockResource} REST controller.
 */
@SpringBootTest(classes = CasinoApp.class)
public class PlayerStockResourceIT {

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    @Autowired
    private PlayerStockRepository playerStockRepository;

    @Autowired
    private PlayerStockMapper playerStockMapper;

    @Autowired
    private PlayerStockService playerStockService;

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

    private MockMvc restPlayerStockMockMvc;

    private PlayerStock playerStock;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerStockResource playerStockResource = new PlayerStockResource(playerStockService);
        this.restPlayerStockMockMvc = MockMvcBuilders.standaloneSetup(playerStockResource)
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
    public static PlayerStock createEntity(EntityManager em) {
        PlayerStock playerStock = new PlayerStock()
            .amount(DEFAULT_AMOUNT);
        return playerStock;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerStock createUpdatedEntity(EntityManager em) {
        PlayerStock playerStock = new PlayerStock()
            .amount(UPDATED_AMOUNT);
        return playerStock;
    }

    @BeforeEach
    public void initTest() {
        playerStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayerStock() throws Exception {
        int databaseSizeBeforeCreate = playerStockRepository.findAll().size();

        // Create the PlayerStock
        PlayerStockDTO playerStockDTO = playerStockMapper.toDto(playerStock);
        restPlayerStockMockMvc.perform(post("/api/player-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStockDTO)))
            .andExpect(status().isCreated());

        // Validate the PlayerStock in the database
        List<PlayerStock> playerStockList = playerStockRepository.findAll();
        assertThat(playerStockList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerStock testPlayerStock = playerStockList.get(playerStockList.size() - 1);
        assertThat(testPlayerStock.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createPlayerStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerStockRepository.findAll().size();

        // Create the PlayerStock with an existing ID
        playerStock.setId(1L);
        PlayerStockDTO playerStockDTO = playerStockMapper.toDto(playerStock);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerStockMockMvc.perform(post("/api/player-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerStock in the database
        List<PlayerStock> playerStockList = playerStockRepository.findAll();
        assertThat(playerStockList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlayerStocks() throws Exception {
        // Initialize the database
        playerStockRepository.saveAndFlush(playerStock);

        // Get all the playerStockList
        restPlayerStockMockMvc.perform(get("/api/player-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)));
    }
    
    @Test
    @Transactional
    public void getPlayerStock() throws Exception {
        // Initialize the database
        playerStockRepository.saveAndFlush(playerStock);

        // Get the playerStock
        restPlayerStockMockMvc.perform(get("/api/player-stocks/{id}", playerStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playerStock.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPlayerStock() throws Exception {
        // Get the playerStock
        restPlayerStockMockMvc.perform(get("/api/player-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayerStock() throws Exception {
        // Initialize the database
        playerStockRepository.saveAndFlush(playerStock);

        int databaseSizeBeforeUpdate = playerStockRepository.findAll().size();

        // Update the playerStock
        PlayerStock updatedPlayerStock = playerStockRepository.findById(playerStock.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerStock are not directly saved in db
        em.detach(updatedPlayerStock);
        updatedPlayerStock
            .amount(UPDATED_AMOUNT);
        PlayerStockDTO playerStockDTO = playerStockMapper.toDto(updatedPlayerStock);

        restPlayerStockMockMvc.perform(put("/api/player-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStockDTO)))
            .andExpect(status().isOk());

        // Validate the PlayerStock in the database
        List<PlayerStock> playerStockList = playerStockRepository.findAll();
        assertThat(playerStockList).hasSize(databaseSizeBeforeUpdate);
        PlayerStock testPlayerStock = playerStockList.get(playerStockList.size() - 1);
        assertThat(testPlayerStock.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayerStock() throws Exception {
        int databaseSizeBeforeUpdate = playerStockRepository.findAll().size();

        // Create the PlayerStock
        PlayerStockDTO playerStockDTO = playerStockMapper.toDto(playerStock);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerStockMockMvc.perform(put("/api/player-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStockDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerStock in the database
        List<PlayerStock> playerStockList = playerStockRepository.findAll();
        assertThat(playerStockList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlayerStock() throws Exception {
        // Initialize the database
        playerStockRepository.saveAndFlush(playerStock);

        int databaseSizeBeforeDelete = playerStockRepository.findAll().size();

        // Delete the playerStock
        restPlayerStockMockMvc.perform(delete("/api/player-stocks/{id}", playerStock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlayerStock> playerStockList = playerStockRepository.findAll();
        assertThat(playerStockList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
