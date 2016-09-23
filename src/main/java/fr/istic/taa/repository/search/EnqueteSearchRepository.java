package fr.istic.taa.repository.search;

import fr.istic.taa.domain.Enquete;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Enquete entity.
 */
public interface EnqueteSearchRepository extends ElasticsearchRepository<Enquete, Long> {
}
