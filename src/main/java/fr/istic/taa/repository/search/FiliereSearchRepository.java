package fr.istic.taa.repository.search;

import fr.istic.taa.domain.Filiere;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Filiere entity.
 */
public interface FiliereSearchRepository extends ElasticsearchRepository<Filiere, Long> {
}
