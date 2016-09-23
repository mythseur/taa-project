package fr.istic.taa.repository;

import fr.istic.taa.domain.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Stage entity.
 */
@SuppressWarnings("unused")
public interface StageRepository extends JpaRepository<Stage, Long> {

}
