package at.steiner.casino.web.rest;

import at.steiner.casino.CasinoApp;
import at.steiner.casino.domain.PlayerStockTransaction;
import at.steiner.casino.repository.PlayerStockTransactionRepository;
import at.steiner.casino.service.PlayerStockTransactionService;
import at.steiner.casino.service.dto.PlayerStockTransactionDTO;
import at.steiner.casino.service.mapper.PlayerStockTransactionMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static at.steiner.casino.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PlayerStockTransactionResource} REST controller.
 */
@SpringBootTest(classes = CasinoApp.class)
public class PlayerStockTransactionResourceIT {

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_AMOUNT = 1;
    private static final Integer UPDATED_AMOUNT = 2;

    @Autowired
    private PlayerStockTransactionRepository playerStockTransactionRepository;

    @Autowired
    private PlayerStockTransactionMapper playerStockTransactionMapper;

    @Autowired
    private PlayerStockTransactionService playerStockTransactionService;

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

    private MockMvc restPlayerStockTransactionMockMvc;

    private PlayerStockTransaction playerStockTransaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PlayerStockTransactionResource playerStockTransactionResource = new PlayerStockTransactionResource(playerStockTransactionService);
        this.restPlayerStockTransactionMockMvc = MockMvcBuilders.standaloneSetup(playerStockTransactionResource)
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
    public static PlayerStockTransaction createEntity(EntityManager em) {
        PlayerStockTransaction playerStockTransaction = new PlayerStockTransaction()
            .time(DEFAULT_TIME)
            .amount(DEFAULT_AMOUNT);
        return playerStockTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlayerStockTransaction createUpdatedEntity(EntityManager em) {
        PlayerStockTransaction playerStockTransaction = new PlayerStockTransaction()
            .time(UPDATED_TIME)
            .amount(UPDATED_AMOUNT);
        return playerStockTransaction;
    }

    @BeforeEach
    public void initTest() {
        playerStockTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlayerStockTransaction() throws Exception {
        int databaseSizeBeforeCreate = playerStockTransactionRepository.findAll().size();

        // Create the PlayerStockTransaction
        PlayerStockTransactionDTO playerStockTransactionDTO = playerStockTransactionMapper.toDto(playerStockTransaction);
        restPlayerStockTransactionMockMvc.perform(post("/api/player-stock-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStockTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the PlayerStockTransaction in the database
        List<PlayerStockTransaction> playerStockTransactionList = playerStockTransactionRepository.findAll();
        assertThat(playerStockTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        PlayerStockTransaction testPlayerStockTransaction = playerStockTransactionList.get(playerStockTransactionList.size() - 1);
        assertThat(testPlayerStockTransaction.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testPlayerStockTransaction.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createPlayerStockTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = playerStockTransactionRepository.findAll().size();

        // Create the PlayerStockTransaction with an existing ID
        playerStockTransaction.setId(1L);
        PlayerStockTransactionDTO playerStockTransactionDTO = playerStockTransactionMapper.toDto(playerStockTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlayerStockTransactionMockMvc.perform(post("/api/player-stock-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStockTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerStockTransaction in the database
        List<PlayerStockTransaction> playerStockTransactionList = playerStockTransactionRepository.findAll();
        assertThat(playerStockTransactionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPlayerStockTransactions() throws Exception {
        // Initialize the database
        playerStockTransactionRepository.saveAndFlush(playerStockTransaction);

        // Get all the playerStockTransactionList
        restPlayerStockTransactionMockMvc.perform(get("/api/player-stock-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(playerStockTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)));
    }
    
    @Test
    @Transactional
    public void getPlayerStockTransaction() throws Exception {
        // Initialize the database
        playerStockTransactionRepository.saveAndFlush(playerStockTransaction);

        // Get the playerStockTransaction
        restPlayerStockTransactionMockMvc.perform(get("/api/player-stock-transactions/{id}", playerStockTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(playerStockTransaction.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT));
    }

    @Test
    @Transactional
    public void getNonExistingPlayerStockTransaction() throws Exception {
        // Get the playerStockTransaction
        restPlayerStockTransactionMockMvc.perform(get("/api/player-stock-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlayerStockTransaction() throws Exception {
        // Initialize the database
        playerStockTransactionRepository.saveAndFlush(playerStockTransaction);

        int databaseSizeBeforeUpdate = playerStockTransactionRepository.findAll().size();

        // Update the playerStockTransaction
        PlayerStockTransaction updatedPlayerStockTransaction = playerStockTransactionRepository.findById(playerStockTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedPlayerStockTransaction are not directly saved in db
        em.detach(updatedPlayerStockTransaction);
        updatedPlayerStockTransaction
            .time(UPDATED_TIME)
            .amount(UPDATED_AMOUNT);
        PlayerStockTransactionDTO playerStockTransactionDTO = playerStockTransactionMapper.toDto(updatedPlayerStockTransaction);

        restPlayerStockTransactionMockMvc.perform(put("/api/player-stock-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStockTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the PlayerStockTransaction in the database
        List<PlayerStockTransaction> playerStockTransactionList = playerStockTransactionRepository.findAll();
        assertThat(playerStockTransactionList).hasSize(databaseSizeBeforeUpdate);
        PlayerStockTransaction testPlayerStockTransaction = playerStockTransactionList.get(playerStockTransactionList.size() - 1);
        assertThat(testPlayerStockTransaction.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testPlayerStockTransaction.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPlayerStockTransaction() throws Exception {
        int databaseSizeBeforeUpdate = playerStockTransactionRepository.findAll().size();

        // Create the PlayerStockTransaction
        PlayerStockTransactionDTO playerStockTransactionDTO = playerStockTransactionMapper.toDto(playerStockTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlayerStockTransactionMockMvc.perform(put("/api/player-stock-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(playerStockTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PlayerStockTransaction in the database
        List<PlayerStockTransaction> playerStockTransactionList = playerStockTransactionRepository.findAll();
        assertThat(playerStockTransactionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePlayerStockTransaction() throws Exception {
        // Initialize the database
        playerStockTransactionRepository.saveAndFlush(playerStockTransaction);

        int databaseSizeBeforeDelete = playerStockTransactionRepository.findAll().size();

        // Delete the playerStockTransaction
        restPlayerStockTransactionMockMvc.perform(delete("/api/player-stock-transactions/{id}", playerStockTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PlayerStockTransaction> playerStockTransactionList = playerStockTransactionRepository.findAll();
        assertThat(playerStockTransactionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
