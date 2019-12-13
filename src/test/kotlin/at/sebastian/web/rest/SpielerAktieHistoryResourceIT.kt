package at.sebastian.web.rest

import at.sebastian.CasinoApp
import at.sebastian.domain.SpielerAktieHistory
import at.sebastian.repository.SpielerAktieHistoryRepository
import at.sebastian.web.rest.errors.ExceptionTranslator

import kotlin.test.assertNotNull

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.transaction.annotation.Transactional
import org.springframework.validation.Validator
import javax.persistence.EntityManager
import java.time.Instant
import java.time.temporal.ChronoUnit

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasItem
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


/**
 * Integration tests for the [SpielerAktieHistoryResource] REST controller.
 *
 * @see SpielerAktieHistoryResource
 */
@SpringBootTest(classes = [CasinoApp::class])
class SpielerAktieHistoryResourceIT {

    @Autowired
    private lateinit var spielerAktieHistoryRepository: SpielerAktieHistoryRepository

    @Autowired
    private lateinit var jacksonMessageConverter: MappingJackson2HttpMessageConverter

    @Autowired
    private lateinit var pageableArgumentResolver: PageableHandlerMethodArgumentResolver

    @Autowired
    private lateinit var exceptionTranslator: ExceptionTranslator

    @Autowired
    private lateinit var em: EntityManager

    @Autowired
    private lateinit var validator: Validator

    private lateinit var restSpielerAktieHistoryMockMvc: MockMvc

    private lateinit var spielerAktieHistory: SpielerAktieHistory

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val spielerAktieHistoryResource = SpielerAktieHistoryResource(spielerAktieHistoryRepository)
        this.restSpielerAktieHistoryMockMvc = MockMvcBuilders.standaloneSetup(spielerAktieHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build()
    }

    @BeforeEach
    fun initTest() {
        spielerAktieHistory = createEntity(em)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createSpielerAktieHistory() {
        val databaseSizeBeforeCreate = spielerAktieHistoryRepository.findAll().size

        // Create the SpielerAktieHistory
        restSpielerAktieHistoryMockMvc.perform(
            post("/api/spieler-aktie-histories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerAktieHistory))
        ).andExpect(status().isCreated)

        // Validate the SpielerAktieHistory in the database
        val spielerAktieHistoryList = spielerAktieHistoryRepository.findAll()
        assertThat(spielerAktieHistoryList).hasSize(databaseSizeBeforeCreate + 1)
        val testSpielerAktieHistory = spielerAktieHistoryList[spielerAktieHistoryList.size - 1]
        assertThat(testSpielerAktieHistory.anzahl).isEqualTo(DEFAULT_ANZAHL)
        assertThat(testSpielerAktieHistory.creationTime).isEqualTo(DEFAULT_CREATION_TIME)
    }

    @Test
    @Transactional
    fun createSpielerAktieHistoryWithExistingId() {
        val databaseSizeBeforeCreate = spielerAktieHistoryRepository.findAll().size

        // Create the SpielerAktieHistory with an existing ID
        spielerAktieHistory.id = 1L

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpielerAktieHistoryMockMvc.perform(
            post("/api/spieler-aktie-histories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerAktieHistory))
        ).andExpect(status().isBadRequest)

        // Validate the SpielerAktieHistory in the database
        val spielerAktieHistoryList = spielerAktieHistoryRepository.findAll()
        assertThat(spielerAktieHistoryList).hasSize(databaseSizeBeforeCreate)
    }


    @Test
    @Transactional
    fun getAllSpielerAktieHistories() {
        // Initialize the database
        spielerAktieHistoryRepository.saveAndFlush(spielerAktieHistory)

        // Get all the spielerAktieHistoryList
        restSpielerAktieHistoryMockMvc.perform(get("/api/spieler-aktie-histories?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spielerAktieHistory.id?.toInt())))
            .andExpect(jsonPath("$.[*].anzahl").value(hasItem(DEFAULT_ANZAHL)))
            .andExpect(jsonPath("$.[*].creationTime").value(hasItem(DEFAULT_CREATION_TIME.toString())))
    }
    
    @Test
    @Transactional
    fun getSpielerAktieHistory() {
        // Initialize the database
        spielerAktieHistoryRepository.saveAndFlush(spielerAktieHistory)

        val id = spielerAktieHistory.id
        assertNotNull(id)

        // Get the spielerAktieHistory
        restSpielerAktieHistoryMockMvc.perform(get("/api/spieler-aktie-histories/{id}", id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(id.toInt()))
            .andExpect(jsonPath("$.anzahl").value(DEFAULT_ANZAHL))
            .andExpect(jsonPath("$.creationTime").value(DEFAULT_CREATION_TIME.toString()))
    }

    @Test
    @Transactional
    fun getNonExistingSpielerAktieHistory() {
        // Get the spielerAktieHistory
        restSpielerAktieHistoryMockMvc.perform(get("/api/spieler-aktie-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }
    @Test
    @Transactional
    fun updateSpielerAktieHistory() {
        // Initialize the database
        spielerAktieHistoryRepository.saveAndFlush(spielerAktieHistory)

        val databaseSizeBeforeUpdate = spielerAktieHistoryRepository.findAll().size

        // Update the spielerAktieHistory
        val id = spielerAktieHistory.id
        assertNotNull(id)
        val updatedSpielerAktieHistory = spielerAktieHistoryRepository.findById(id).get()
        // Disconnect from session so that the updates on updatedSpielerAktieHistory are not directly saved in db
        em.detach(updatedSpielerAktieHistory)
        updatedSpielerAktieHistory.anzahl = UPDATED_ANZAHL
        updatedSpielerAktieHistory.creationTime = UPDATED_CREATION_TIME

        restSpielerAktieHistoryMockMvc.perform(
            put("/api/spieler-aktie-histories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(updatedSpielerAktieHistory))
        ).andExpect(status().isOk)

        // Validate the SpielerAktieHistory in the database
        val spielerAktieHistoryList = spielerAktieHistoryRepository.findAll()
        assertThat(spielerAktieHistoryList).hasSize(databaseSizeBeforeUpdate)
        val testSpielerAktieHistory = spielerAktieHistoryList[spielerAktieHistoryList.size - 1]
        assertThat(testSpielerAktieHistory.anzahl).isEqualTo(UPDATED_ANZAHL)
        assertThat(testSpielerAktieHistory.creationTime).isEqualTo(UPDATED_CREATION_TIME)
    }

    @Test
    @Transactional
    fun updateNonExistingSpielerAktieHistory() {
        val databaseSizeBeforeUpdate = spielerAktieHistoryRepository.findAll().size

        // Create the SpielerAktieHistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpielerAktieHistoryMockMvc.perform(
            put("/api/spieler-aktie-histories")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerAktieHistory))
        ).andExpect(status().isBadRequest)

        // Validate the SpielerAktieHistory in the database
        val spielerAktieHistoryList = spielerAktieHistoryRepository.findAll()
        assertThat(spielerAktieHistoryList).hasSize(databaseSizeBeforeUpdate)
    }

    @Test
    @Transactional
    fun deleteSpielerAktieHistory() {
        // Initialize the database
        spielerAktieHistoryRepository.saveAndFlush(spielerAktieHistory)

        val databaseSizeBeforeDelete = spielerAktieHistoryRepository.findAll().size

        val id = spielerAktieHistory.id
        assertNotNull(id)

        // Delete the spielerAktieHistory
        restSpielerAktieHistoryMockMvc.perform(
            delete("/api/spieler-aktie-histories/{id}", id)
                .accept(APPLICATION_JSON_UTF8)
        ).andExpect(status().isNoContent)

        // Validate the database contains one less item
        val spielerAktieHistoryList = spielerAktieHistoryRepository.findAll()
        assertThat(spielerAktieHistoryList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    fun equalsVerifier() {
        equalsVerifier(SpielerAktieHistory::class)
        val spielerAktieHistory1 = SpielerAktieHistory()
        spielerAktieHistory1.id = 1L
        val spielerAktieHistory2 = SpielerAktieHistory()
        spielerAktieHistory2.id = spielerAktieHistory1.id
        assertThat(spielerAktieHistory1).isEqualTo(spielerAktieHistory2)
        spielerAktieHistory2.id = 2L
        assertThat(spielerAktieHistory1).isNotEqualTo(spielerAktieHistory2)
        spielerAktieHistory1.id = null
        assertThat(spielerAktieHistory1).isNotEqualTo(spielerAktieHistory2)
    }

    companion object {

        private const val DEFAULT_ANZAHL: Int = 1
        private const val UPDATED_ANZAHL: Int = 2

        private val DEFAULT_CREATION_TIME: Instant = Instant.ofEpochMilli(0L)
        private val UPDATED_CREATION_TIME: Instant = Instant.now().truncatedTo(ChronoUnit.MILLIS)

        /**
         * Create an entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createEntity(em: EntityManager): SpielerAktieHistory {
            val spielerAktieHistory = SpielerAktieHistory(
                anzahl = DEFAULT_ANZAHL,
                creationTime = DEFAULT_CREATION_TIME
            )

            return spielerAktieHistory
        }

        /**
         * Create an updated entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createUpdatedEntity(em: EntityManager): SpielerAktieHistory {
            val spielerAktieHistory = SpielerAktieHistory(
                anzahl = UPDATED_ANZAHL,
                creationTime = UPDATED_CREATION_TIME
            )

            return spielerAktieHistory
        }
    }
}
