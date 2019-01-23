package com.elasticfurkan.elasticfurkan.dto;


import com.elasticfurkan.elasticfurkan.esmodel.EsWebsite;

public class WebsiteDTO {

    private String link;
    private String content;
    private String category;
    private String title;

    public WebsiteDTO(String category) {
        this.category = category;
    }

    public WebsiteDTO(EsWebsite esWebsite) {
        link = esWebsite.getLink();
        content = esWebsite.getContent();
        category = esWebsite.getArea();
        title = esWebsite.getTitle();
    }

    public WebsiteDTO(String link, String content, String category, String title) {
        this.link = link;
        this.content = content;
        this.category = category;
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "WebsiteDTO [link=" + link + ", content=" + content + ", category=" + category + ", title=" + title
                + "]";
    }

}
