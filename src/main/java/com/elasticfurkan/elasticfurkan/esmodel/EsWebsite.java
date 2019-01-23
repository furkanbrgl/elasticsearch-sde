package com.elasticfurkan.elasticfurkan.esmodel;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "testindex", type = "testtype", shards = 1, replicas = 0, refreshInterval = "-1")
public class EsWebsite {

    @Id
    private String link;

    @Field(type = FieldType.text, fielddata = true)
    private String content;

    @Field(type=FieldType.keyword)
    private String area;

    @Field(type = FieldType.text, fielddata = true)
    private String title;


    public EsWebsite() {

    }

    public EsWebsite(String link, String content, String area, String title) {
        this.link = link;
        this.content = content;
        this.area = area;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "EsWebsite [link=" + link + ", content=" + content + ", area=" + area + ", title=" + title
                + ", suggestion=" + "" + "]";
    }

}
