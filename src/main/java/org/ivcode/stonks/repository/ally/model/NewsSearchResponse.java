package org.ivcode.stonks.repository.ally.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsSearchResponse {
	@XmlAttribute
	private String id;

	@XmlElement
	private Integer elapsedtime;

	@XmlElementWrapper(name = "articles")
	@XmlElement(name = "article")
	private List<NewsArticle> articles;

	@XmlElement
	private String error;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getElapsedtime() {
		return elapsedtime;
	}

	public void setElapsedtime(Integer elapsedtime) {
		this.elapsedtime = elapsedtime;
	}

	public List<NewsArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<NewsArticle> articles) {
		this.articles = articles;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
