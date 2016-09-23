package fr.istic.taa.repository.search;

import fr.istic.taa.domain.DonneesEntreprise;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DonneesEntreprise entity.
 */
public interface DonneesEntrepriseSearchRepository extends ElasticsearchRepository<DonneesEntreprise, Long> {
}
