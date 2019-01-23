package com.elasticfurkan.elasticfurkan.repository;

import com.elasticfurkan.elasticfurkan.esmodel.EsWebsite;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsWebsiteRepository extends ElasticsearchRepository<EsWebsite, String> {

}
