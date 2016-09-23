package fr.istic.taa.repository.search;

import fr.istic.taa.domain.DonneesEtudiant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DonneesEtudiant entity.
 */
public interface DonneesEtudiantSearchRepository extends ElasticsearchRepository<DonneesEtudiant, Long> {
}
