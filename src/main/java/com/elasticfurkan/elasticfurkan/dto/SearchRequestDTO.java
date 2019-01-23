package com.elasticfurkan.elasticfurkan.dto;

public class SearchRequestDTO {
	private String content;
	private String category;

	public SearchRequestDTO() {

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

	@Override
	public String toString() {
		return "SearchRequestDTO [content=" + content + ", category=" + category + "]";
	}

}
