package at.sebastian.repository
import at.sebastian.domain.AktieWert
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [AktieWert] entity.
 */
@Suppress("unused")
@Repository
interface AktieWertRepository : JpaRepository<AktieWert, Long>
