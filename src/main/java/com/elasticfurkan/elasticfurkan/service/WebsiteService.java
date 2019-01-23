package com.elasticfurkan.elasticfurkan.service;

import com.elasticfurkan.elasticfurkan.dto.SearchResponseDTO;

public interface WebsiteService {

    public String addBulkData();

    public SearchResponseDTO getEsWebsitesByCategoryAndTitle(String category, String content, Integer page);

    String addBulkData(String url, String content, String category, String title);
}
