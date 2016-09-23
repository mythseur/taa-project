package fr.istic.taa.repository.search;

import fr.istic.taa.domain.Etudiant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Etudiant entity.
 */
public interface EtudiantSearchRepository extends ElasticsearchRepository<Etudiant, Long> {
}
