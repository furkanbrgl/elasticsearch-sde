package com.elasticfurkan.elasticfurkan.dto;

import java.util.List;
import java.util.Set;

public class SearchResponseDTO {
    private List<WebsiteDTO> websiteDTOS;
    private List<KeyValue> keyValues;
    private Set<String> suggestionList;
    private long totalPageCount;
    private int pageSize;
    private int activePage;

    public SearchResponseDTO() {

    }

    public List<WebsiteDTO> getWebsiteDTOS() {
        return websiteDTOS;
    }

    public void setWebsiteDTOS(List<WebsiteDTO> websiteDTOS) {
        this.websiteDTOS = websiteDTOS;
    }

    public List<KeyValue> getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(List<KeyValue> keyValues) {
        this.keyValues = keyValues;
    }

    public Set<String> getSuggestionList() {
        return suggestionList;
    }

    public void setSuggestionList(Set<String> suggestionList) {
        this.suggestionList = suggestionList;
    }

    public long getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(long totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getActivePage() {
        return activePage;
    }

    public void setActivePage(int activePage) {
        this.activePage = activePage;
    }

    @Override
    public String toString() {
        return "SearchResponseDTO [websiteDTOS=" + websiteDTOS + ", keyValues=" + keyValues + ", suggestionList="
                + suggestionList + ", totalPageCount=" + totalPageCount + ", pageSize=" + pageSize + ", activePage="
                + activePage + "]";
    }

}
