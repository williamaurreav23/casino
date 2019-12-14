package at.sebastian.repository
import at.sebastian.domain.SpielerAktie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [SpielerAktie] entity.
 */
@Suppress("unused")
@Repository
interface SpielerAktieRepository : JpaRepository<SpielerAktie, Long>
