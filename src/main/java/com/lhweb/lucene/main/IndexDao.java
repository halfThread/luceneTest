package com.lhweb.lucene.main;

import java.util.List;

import com.lhweb.lucene.po.Article;

public interface IndexDao {
	
	public void saveArticle(Article article);
	
	public void deleteArticle(Integer articleId);
	
	public void updateArticle(Article article);
	
	public List<Article> queryArticle(String queryStr);
	
	public List<Article> queryArticle(String queryStr,int pageNo,int pageSize);
}	
