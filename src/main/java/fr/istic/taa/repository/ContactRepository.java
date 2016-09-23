package fr.istic.taa.repository;

import fr.istic.taa.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Contact entity.
 */
@SuppressWarnings("unused")
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
