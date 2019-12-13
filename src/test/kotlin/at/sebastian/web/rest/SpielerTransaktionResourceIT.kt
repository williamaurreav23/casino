package at.sebastian.web.rest

import at.sebastian.CasinoApp
import at.sebastian.domain.SpielerTransaktion
import at.sebastian.repository.SpielerTransaktionRepository
import at.sebastian.service.SpielerTransaktionService
import at.sebastian.service.dto.SpielerTransaktionDTO
import at.sebastian.service.mapper.SpielerTransaktionMapper
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

import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.Matchers.hasItem
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import at.sebastian.domain.enumeration.Transaktion

/**
 * Integration tests for the [SpielerTransaktionResource] REST controller.
 *
 * @see SpielerTransaktionResource
 */
@SpringBootTest(classes = [CasinoApp::class])
class SpielerTransaktionResourceIT {

    @Autowired
    private lateinit var spielerTransaktionRepository: SpielerTransaktionRepository

    @Autowired
    private lateinit var spielerTransaktionMapper: SpielerTransaktionMapper

    @Autowired
    private lateinit var spielerTransaktionService: SpielerTransaktionService

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

    private lateinit var restSpielerTransaktionMockMvc: MockMvc

    private lateinit var spielerTransaktion: SpielerTransaktion

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val spielerTransaktionResource = SpielerTransaktionResource(spielerTransaktionService)
        this.restSpielerTransaktionMockMvc = MockMvcBuilders.standaloneSetup(spielerTransaktionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build()
    }

    @BeforeEach
    fun initTest() {
        spielerTransaktion = createEntity(em)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createSpielerTransaktion() {
        val databaseSizeBeforeCreate = spielerTransaktionRepository.findAll().size

        // Create the SpielerTransaktion
        val spielerTransaktionDTO = spielerTransaktionMapper.toDto(spielerTransaktion)
        restSpielerTransaktionMockMvc.perform(
            post("/api/spieler-transaktions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerTransaktionDTO))
        ).andExpect(status().isCreated)

        // Validate the SpielerTransaktion in the database
        val spielerTransaktionList = spielerTransaktionRepository.findAll()
        assertThat(spielerTransaktionList).hasSize(databaseSizeBeforeCreate + 1)
        val testSpielerTransaktion = spielerTransaktionList[spielerTransaktionList.size - 1]
        assertThat(testSpielerTransaktion.wert).isEqualTo(DEFAULT_WERT)
        assertThat(testSpielerTransaktion.typ).isEqualTo(DEFAULT_TYP)
    }

    @Test
    @Transactional
    fun createSpielerTransaktionWithExistingId() {
        val databaseSizeBeforeCreate = spielerTransaktionRepository.findAll().size

        // Create the SpielerTransaktion with an existing ID
        spielerTransaktion.id = 1L
        val spielerTransaktionDTO = spielerTransaktionMapper.toDto(spielerTransaktion)

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpielerTransaktionMockMvc.perform(
            post("/api/spieler-transaktions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerTransaktionDTO))
        ).andExpect(status().isBadRequest)

        // Validate the SpielerTransaktion in the database
        val spielerTransaktionList = spielerTransaktionRepository.findAll()
        assertThat(spielerTransaktionList).hasSize(databaseSizeBeforeCreate)
    }


    @Test
    @Transactional
    fun getAllSpielerTransaktions() {
        // Initialize the database
        spielerTransaktionRepository.saveAndFlush(spielerTransaktion)

        // Get all the spielerTransaktionList
        restSpielerTransaktionMockMvc.perform(get("/api/spieler-transaktions?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spielerTransaktion.id?.toInt())))
            .andExpect(jsonPath("$.[*].wert").value(hasItem(DEFAULT_WERT)))
            .andExpect(jsonPath("$.[*].typ").value(hasItem(DEFAULT_TYP.toString())))
    }
    
    @Test
    @Transactional
    fun getSpielerTransaktion() {
        // Initialize the database
        spielerTransaktionRepository.saveAndFlush(spielerTransaktion)

        val id = spielerTransaktion.id
        assertNotNull(id)

        // Get the spielerTransaktion
        restSpielerTransaktionMockMvc.perform(get("/api/spieler-transaktions/{id}", id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(id.toInt()))
            .andExpect(jsonPath("$.wert").value(DEFAULT_WERT))
            .andExpect(jsonPath("$.typ").value(DEFAULT_TYP.toString()))
    }

    @Test
    @Transactional
    fun getNonExistingSpielerTransaktion() {
        // Get the spielerTransaktion
        restSpielerTransaktionMockMvc.perform(get("/api/spieler-transaktions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }
    @Test
    @Transactional
    fun updateSpielerTransaktion() {
        // Initialize the database
        spielerTransaktionRepository.saveAndFlush(spielerTransaktion)

        val databaseSizeBeforeUpdate = spielerTransaktionRepository.findAll().size

        // Update the spielerTransaktion
        val id = spielerTransaktion.id
        assertNotNull(id)
        val updatedSpielerTransaktion = spielerTransaktionRepository.findById(id).get()
        // Disconnect from session so that the updates on updatedSpielerTransaktion are not directly saved in db
        em.detach(updatedSpielerTransaktion)
        updatedSpielerTransaktion.wert = UPDATED_WERT
        updatedSpielerTransaktion.typ = UPDATED_TYP
        val spielerTransaktionDTO = spielerTransaktionMapper.toDto(updatedSpielerTransaktion)

        restSpielerTransaktionMockMvc.perform(
            put("/api/spieler-transaktions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerTransaktionDTO))
        ).andExpect(status().isOk)

        // Validate the SpielerTransaktion in the database
        val spielerTransaktionList = spielerTransaktionRepository.findAll()
        assertThat(spielerTransaktionList).hasSize(databaseSizeBeforeUpdate)
        val testSpielerTransaktion = spielerTransaktionList[spielerTransaktionList.size - 1]
        assertThat(testSpielerTransaktion.wert).isEqualTo(UPDATED_WERT)
        assertThat(testSpielerTransaktion.typ).isEqualTo(UPDATED_TYP)
    }

    @Test
    @Transactional
    fun updateNonExistingSpielerTransaktion() {
        val databaseSizeBeforeUpdate = spielerTransaktionRepository.findAll().size

        // Create the SpielerTransaktion
        val spielerTransaktionDTO = spielerTransaktionMapper.toDto(spielerTransaktion)

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpielerTransaktionMockMvc.perform(
            put("/api/spieler-transaktions")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerTransaktionDTO))
        ).andExpect(status().isBadRequest)

        // Validate the SpielerTransaktion in the database
        val spielerTransaktionList = spielerTransaktionRepository.findAll()
        assertThat(spielerTransaktionList).hasSize(databaseSizeBeforeUpdate)
    }

    @Test
    @Transactional
    fun deleteSpielerTransaktion() {
        // Initialize the database
        spielerTransaktionRepository.saveAndFlush(spielerTransaktion)

        val databaseSizeBeforeDelete = spielerTransaktionRepository.findAll().size

        val id = spielerTransaktion.id
        assertNotNull(id)

        // Delete the spielerTransaktion
        restSpielerTransaktionMockMvc.perform(
            delete("/api/spieler-transaktions/{id}", id)
                .accept(APPLICATION_JSON_UTF8)
        ).andExpect(status().isNoContent)

        // Validate the database contains one less item
        val spielerTransaktionList = spielerTransaktionRepository.findAll()
        assertThat(spielerTransaktionList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    fun equalsVerifier() {
        equalsVerifier(SpielerTransaktion::class)
        val spielerTransaktion1 = SpielerTransaktion()
        spielerTransaktion1.id = 1L
        val spielerTransaktion2 = SpielerTransaktion()
        spielerTransaktion2.id = spielerTransaktion1.id
        assertThat(spielerTransaktion1).isEqualTo(spielerTransaktion2)
        spielerTransaktion2.id = 2L
        assertThat(spielerTransaktion1).isNotEqualTo(spielerTransaktion2)
        spielerTransaktion1.id = null
        assertThat(spielerTransaktion1).isNotEqualTo(spielerTransaktion2)
    }

    @Test
    @Transactional
    fun dtoEqualsVerifier() {
        equalsVerifier(SpielerTransaktionDTO::class)
        val spielerTransaktionDTO1 = SpielerTransaktionDTO()
        spielerTransaktionDTO1.id = 1L
        val spielerTransaktionDTO2 = SpielerTransaktionDTO()
        assertThat(spielerTransaktionDTO1).isNotEqualTo(spielerTransaktionDTO2)
        spielerTransaktionDTO2.id = spielerTransaktionDTO1.id
        assertThat(spielerTransaktionDTO1).isEqualTo(spielerTransaktionDTO2)
        spielerTransaktionDTO2.id = 2L
        assertThat(spielerTransaktionDTO1).isNotEqualTo(spielerTransaktionDTO2)
        spielerTransaktionDTO1.id = null
        assertThat(spielerTransaktionDTO1).isNotEqualTo(spielerTransaktionDTO2)
    }

    @Test
    @Transactional
    fun testEntityFromId() {
        assertThat(spielerTransaktionMapper.fromId(42L)?.id).isEqualTo(42)
        assertThat(spielerTransaktionMapper.fromId(null)).isNull()
    }

    companion object {

        private const val DEFAULT_WERT: Int = 1
        private const val UPDATED_WERT: Int = 2

        private val DEFAULT_TYP: Transaktion = Transaktion.SPIELSTART
        private val UPDATED_TYP: Transaktion = Transaktion.ROULETTE

        /**
         * Create an entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createEntity(em: EntityManager): SpielerTransaktion {
            val spielerTransaktion = SpielerTransaktion(
                wert = DEFAULT_WERT,
                typ = DEFAULT_TYP
            )

            return spielerTransaktion
        }

        /**
         * Create an updated entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createUpdatedEntity(em: EntityManager): SpielerTransaktion {
            val spielerTransaktion = SpielerTransaktion(
                wert = UPDATED_WERT,
                typ = UPDATED_TYP
            )

            return spielerTransaktion
        }
    }
}
