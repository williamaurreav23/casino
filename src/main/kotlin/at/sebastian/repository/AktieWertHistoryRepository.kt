package at.sebastian.repository
import at.sebastian.domain.AktieWertHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [AktieWertHistory] entity.
 */
@Suppress("unused")
@Repository
interface AktieWertHistoryRepository : JpaRepository<AktieWertHistory, Long>
