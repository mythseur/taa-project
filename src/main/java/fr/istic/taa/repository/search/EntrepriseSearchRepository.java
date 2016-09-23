package fr.istic.taa.repository.search;

import fr.istic.taa.domain.Entreprise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Entreprise entity.
 */
public interface EntrepriseSearchRepository extends ElasticsearchRepository<Entreprise, Long> {
}
