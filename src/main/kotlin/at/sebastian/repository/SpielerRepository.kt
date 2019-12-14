package at.sebastian.repository
import at.sebastian.domain.Spieler
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Spring Data  repository for the [Spieler] entity.
 */
@Suppress("unused")
@Repository
interface SpielerRepository : JpaRepository<Spieler, Long>
