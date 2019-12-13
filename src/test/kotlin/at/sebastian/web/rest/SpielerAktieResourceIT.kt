package at.sebastian.web.rest

import at.sebastian.CasinoApp
import at.sebastian.domain.SpielerAktie
import at.sebastian.repository.SpielerAktieRepository
import at.sebastian.service.SpielerAktieService
import at.sebastian.service.dto.SpielerAktieDTO
import at.sebastian.service.mapper.SpielerAktieMapper
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


/**
 * Integration tests for the [SpielerAktieResource] REST controller.
 *
 * @see SpielerAktieResource
 */
@SpringBootTest(classes = [CasinoApp::class])
class SpielerAktieResourceIT {

    @Autowired
    private lateinit var spielerAktieRepository: SpielerAktieRepository

    @Autowired
    private lateinit var spielerAktieMapper: SpielerAktieMapper

    @Autowired
    private lateinit var spielerAktieService: SpielerAktieService

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

    private lateinit var restSpielerAktieMockMvc: MockMvc

    private lateinit var spielerAktie: SpielerAktie

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val spielerAktieResource = SpielerAktieResource(spielerAktieService)
        this.restSpielerAktieMockMvc = MockMvcBuilders.standaloneSetup(spielerAktieResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build()
    }

    @BeforeEach
    fun initTest() {
        spielerAktie = createEntity(em)
    }

    @Test
    @Transactional
    @Throws(Exception::class)
    fun createSpielerAktie() {
        val databaseSizeBeforeCreate = spielerAktieRepository.findAll().size

        // Create the SpielerAktie
        val spielerAktieDTO = spielerAktieMapper.toDto(spielerAktie)
        restSpielerAktieMockMvc.perform(
            post("/api/spieler-akties")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerAktieDTO))
        ).andExpect(status().isCreated)

        // Validate the SpielerAktie in the database
        val spielerAktieList = spielerAktieRepository.findAll()
        assertThat(spielerAktieList).hasSize(databaseSizeBeforeCreate + 1)
        val testSpielerAktie = spielerAktieList[spielerAktieList.size - 1]
        assertThat(testSpielerAktie.anzahl).isEqualTo(DEFAULT_ANZAHL)
    }

    @Test
    @Transactional
    fun createSpielerAktieWithExistingId() {
        val databaseSizeBeforeCreate = spielerAktieRepository.findAll().size

        // Create the SpielerAktie with an existing ID
        spielerAktie.id = 1L
        val spielerAktieDTO = spielerAktieMapper.toDto(spielerAktie)

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpielerAktieMockMvc.perform(
            post("/api/spieler-akties")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerAktieDTO))
        ).andExpect(status().isBadRequest)

        // Validate the SpielerAktie in the database
        val spielerAktieList = spielerAktieRepository.findAll()
        assertThat(spielerAktieList).hasSize(databaseSizeBeforeCreate)
    }


    @Test
    @Transactional
    fun getAllSpielerAkties() {
        // Initialize the database
        spielerAktieRepository.saveAndFlush(spielerAktie)

        // Get all the spielerAktieList
        restSpielerAktieMockMvc.perform(get("/api/spieler-akties?sort=id,desc"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spielerAktie.id?.toInt())))
            .andExpect(jsonPath("$.[*].anzahl").value(hasItem(DEFAULT_ANZAHL)))
    }
    
    @Test
    @Transactional
    fun getSpielerAktie() {
        // Initialize the database
        spielerAktieRepository.saveAndFlush(spielerAktie)

        val id = spielerAktie.id
        assertNotNull(id)

        // Get the spielerAktie
        restSpielerAktieMockMvc.perform(get("/api/spieler-akties/{id}", id))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(id.toInt()))
            .andExpect(jsonPath("$.anzahl").value(DEFAULT_ANZAHL))
    }

    @Test
    @Transactional
    fun getNonExistingSpielerAktie() {
        // Get the spielerAktie
        restSpielerAktieMockMvc.perform(get("/api/spieler-akties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound)
    }
    @Test
    @Transactional
    fun updateSpielerAktie() {
        // Initialize the database
        spielerAktieRepository.saveAndFlush(spielerAktie)

        val databaseSizeBeforeUpdate = spielerAktieRepository.findAll().size

        // Update the spielerAktie
        val id = spielerAktie.id
        assertNotNull(id)
        val updatedSpielerAktie = spielerAktieRepository.findById(id).get()
        // Disconnect from session so that the updates on updatedSpielerAktie are not directly saved in db
        em.detach(updatedSpielerAktie)
        updatedSpielerAktie.anzahl = UPDATED_ANZAHL
        val spielerAktieDTO = spielerAktieMapper.toDto(updatedSpielerAktie)

        restSpielerAktieMockMvc.perform(
            put("/api/spieler-akties")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerAktieDTO))
        ).andExpect(status().isOk)

        // Validate the SpielerAktie in the database
        val spielerAktieList = spielerAktieRepository.findAll()
        assertThat(spielerAktieList).hasSize(databaseSizeBeforeUpdate)
        val testSpielerAktie = spielerAktieList[spielerAktieList.size - 1]
        assertThat(testSpielerAktie.anzahl).isEqualTo(UPDATED_ANZAHL)
    }

    @Test
    @Transactional
    fun updateNonExistingSpielerAktie() {
        val databaseSizeBeforeUpdate = spielerAktieRepository.findAll().size

        // Create the SpielerAktie
        val spielerAktieDTO = spielerAktieMapper.toDto(spielerAktie)

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpielerAktieMockMvc.perform(
            put("/api/spieler-akties")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(spielerAktieDTO))
        ).andExpect(status().isBadRequest)

        // Validate the SpielerAktie in the database
        val spielerAktieList = spielerAktieRepository.findAll()
        assertThat(spielerAktieList).hasSize(databaseSizeBeforeUpdate)
    }

    @Test
    @Transactional
    fun deleteSpielerAktie() {
        // Initialize the database
        spielerAktieRepository.saveAndFlush(spielerAktie)

        val databaseSizeBeforeDelete = spielerAktieRepository.findAll().size

        val id = spielerAktie.id
        assertNotNull(id)

        // Delete the spielerAktie
        restSpielerAktieMockMvc.perform(
            delete("/api/spieler-akties/{id}", id)
                .accept(APPLICATION_JSON_UTF8)
        ).andExpect(status().isNoContent)

        // Validate the database contains one less item
        val spielerAktieList = spielerAktieRepository.findAll()
        assertThat(spielerAktieList).hasSize(databaseSizeBeforeDelete - 1)
    }

    @Test
    @Transactional
    fun equalsVerifier() {
        equalsVerifier(SpielerAktie::class)
        val spielerAktie1 = SpielerAktie()
        spielerAktie1.id = 1L
        val spielerAktie2 = SpielerAktie()
        spielerAktie2.id = spielerAktie1.id
        assertThat(spielerAktie1).isEqualTo(spielerAktie2)
        spielerAktie2.id = 2L
        assertThat(spielerAktie1).isNotEqualTo(spielerAktie2)
        spielerAktie1.id = null
        assertThat(spielerAktie1).isNotEqualTo(spielerAktie2)
    }

    @Test
    @Transactional
    fun dtoEqualsVerifier() {
        equalsVerifier(SpielerAktieDTO::class)
        val spielerAktieDTO1 = SpielerAktieDTO()
        spielerAktieDTO1.id = 1L
        val spielerAktieDTO2 = SpielerAktieDTO()
        assertThat(spielerAktieDTO1).isNotEqualTo(spielerAktieDTO2)
        spielerAktieDTO2.id = spielerAktieDTO1.id
        assertThat(spielerAktieDTO1).isEqualTo(spielerAktieDTO2)
        spielerAktieDTO2.id = 2L
        assertThat(spielerAktieDTO1).isNotEqualTo(spielerAktieDTO2)
        spielerAktieDTO1.id = null
        assertThat(spielerAktieDTO1).isNotEqualTo(spielerAktieDTO2)
    }

    @Test
    @Transactional
    fun testEntityFromId() {
        assertThat(spielerAktieMapper.fromId(42L)?.id).isEqualTo(42)
        assertThat(spielerAktieMapper.fromId(null)).isNull()
    }

    companion object {

        private const val DEFAULT_ANZAHL: Int = 1
        private const val UPDATED_ANZAHL: Int = 2

        /**
         * Create an entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createEntity(em: EntityManager): SpielerAktie {
            val spielerAktie = SpielerAktie(
                anzahl = DEFAULT_ANZAHL
            )

            return spielerAktie
        }

        /**
         * Create an updated entity for this test.
         *
         * This is a static method, as tests for other entities might also need it,
         * if they test an entity which requires the current entity.
         */
        @JvmStatic
        fun createUpdatedEntity(em: EntityManager): SpielerAktie {
            val spielerAktie = SpielerAktie(
                anzahl = UPDATED_ANZAHL
            )

            return spielerAktie
        }
    }
}
