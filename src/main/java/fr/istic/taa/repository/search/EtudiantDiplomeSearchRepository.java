package fr.istic.taa.repository.search;

import fr.istic.taa.domain.EtudiantDiplome;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EtudiantDiplome entity.
 */
public interface EtudiantDiplomeSearchRepository extends ElasticsearchRepository<EtudiantDiplome, Long> {
}
