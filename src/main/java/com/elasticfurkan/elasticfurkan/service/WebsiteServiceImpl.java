package com.elasticfurkan.elasticfurkan.service;

import com.elasticfurkan.elasticfurkan.config.ElasticsearchConfig;
import com.elasticfurkan.elasticfurkan.dto.KeyValue;
import com.elasticfurkan.elasticfurkan.dto.SearchResponseDTO;
import com.elasticfurkan.elasticfurkan.dto.WebsiteDTO;
import com.elasticfurkan.elasticfurkan.esmodel.EsWebsite;
import com.elasticfurkan.elasticfurkan.repository.EsWebsiteRepository;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.elasticfurkan.elasticfurkan.util.StringUtil.getRegex;

@Service
public class WebsiteServiceImpl implements WebsiteService {

    private String filteredTag = "area";

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Autowired
    private EsWebsiteRepository esWebsiteRepository;
    @Autowired
    private ElasticsearchConfig elasticsearchConfig;

    @Override
    public String addBulkData() {

        EsWebsite esWebsite = new EsWebsite();
        esWebsite.setArea("This is sample area");
        esWebsite.setContent("this content field explains why we used instance of website");
        esWebsite.setLink("website link is here");
        esWebsite.setTitle("website title is here");

        esWebsiteRepository.save(esWebsite);
        return "ok";
    }

    @Override
    public String addBulkData(String url, String content, String category, String title) {
        esWebsiteRepository.save(new EsWebsite(url, content, category, title));
        return "ok";
    }


    @Override
    public SearchResponseDTO getEsWebsitesByCategoryAndTitle(String category, String content, Integer page) {

        SearchResponseDTO searchResponseDTO = new SearchResponseDTO();

        this.fillWebsitesDTOListByPageNumer(elasticsearchConfig.getEsIndex(), page, content, category, searchResponseDTO);

        searchResponseDTO.setKeyValues(this.getKeyValuesGroupByCategory(elasticsearchConfig.getEsIndex(), filteredTag));
        searchResponseDTO.setTotalPageCount(
                searchResponseDTO.getTotalPageCount() > 40 ? 39 : searchResponseDTO.getTotalPageCount());
        searchResponseDTO.setPageSize(elasticsearchConfig.getPageSize());

        return searchResponseDTO;
    }

    private SearchResponseDTO fillWebsitesDTOListByPageNumer(String esIndex, Integer page, String content, String category, SearchResponseDTO searchResponseDTO) {

        Set<String> stems = new LinkedHashSet<>();
        stems.add(content);

        BoolQueryBuilder contentBuilder = new BoolQueryBuilder();
        stems.forEach(s -> contentBuilder.should(QueryBuilders.queryStringQuery("title:" + s))
                .should(QueryBuilders.queryStringQuery("content:" + s)));
        QueryStringQueryBuilder third = QueryBuilders.queryStringQuery("area:" + category);
        BoolQueryBuilder filter;
        if (category == null || category.equals(""))
            filter = contentBuilder;
        else
            filter = new BoolQueryBuilder().must(contentBuilder).must(third);

        List<WebsiteDTO> websiteDTOList = new ArrayList<>();

        SearchResponse searchResponse = elasticsearchOperations.getClient().prepareSearch(esIndex)
                .setQuery(filter).setFrom(elasticsearchConfig.getPageSize() * page).setSize(elasticsearchConfig.getPageSize()).execute().actionGet();

        SearchHit[] results = searchResponse.getHits().getHits();
        for (SearchHit hit : results) {
            websiteDTOList.add(new WebsiteDTO(getRegex(hit.getSource().get("link").toString()),
                    getRegex(hit.getSource().get("content").toString()),
                    getRegex(hit.getSource().get("area").toString()),
                    getRegex(hit.getSource().get("title").toString())));

        }
        searchResponseDTO.setWebsiteDTOS(websiteDTOList);

        searchResponseDTO.setTotalPageCount(searchResponse.getHits().getTotalHits() / elasticsearchConfig.getPageSize());

        return searchResponseDTO;
    }

    /*
    get categoryName and count of using
     */
    private List<KeyValue> getKeyValuesGroupByCategory(String esIndex, String fiteredTag) {

        List<KeyValue> keyValues = new ArrayList<>();

        TermsAggregationBuilder aggregation = AggregationBuilders.terms("category_tags")
                .field(fiteredTag).size(100)
                .order(Terms.Order.count(false));
        SearchResponse suggestResponse = elasticsearchOperations.getClient().prepareSearch(esIndex)
                .setSize(0)
                .addAggregation(aggregation)
                .execute().actionGet();

        Terms terms = suggestResponse.getAggregations().get("category_tags");
        List<? extends Terms.Bucket> buckets = terms.getBuckets();

        for (Terms.Bucket bucket : buckets) {
            keyValues.add(new KeyValue(bucket.getKeyAsString(), (int) bucket.getDocCount()));
        }
        return keyValues;
    }


}
