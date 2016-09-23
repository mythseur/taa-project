package fr.istic.taa.repository.search;

import fr.istic.taa.domain.Alternance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Alternance entity.
 */
public interface AlternanceSearchRepository extends ElasticsearchRepository<Alternance, Long> {
}
