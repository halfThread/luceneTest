package com.lhweb.lucene.dao.impl;

import java.util.List;

import org.junit.Test;

import com.lhweb.lucene.main.IndexDao;
import com.lhweb.lucene.po.Article;

public class IndexDaoImplTest {
	IndexDao indexDao = new IndexDaoImpl();

	@Test
	public void testSaveArticle() {
		for (int i = 0; i < 25; i++) {
			Article article = new Article();
			article.setId(i+1);
			article.setTitle((i+1) + ".1 准备lucene的开发环境");
			article.setContent("如果信息检索系统在用户发出了检索请求后再去互联网上找答案，根本没法再有限的时间内返回结果");
			indexDao.saveArticle(article);
		}
	}

	@Test
	public void testDeleteArticle() {
		indexDao.deleteArticle(1);
	}

	@Test
	public void testUpdateArticle() {
		Article article = new Article();
		article.setId(1);
		article.setTitle("lucene这是修改后的标题内容");
		article.setContent("这是修改后的内容");
		indexDao.updateArticle(article);
		testQueryArticle();
	}

	@Test
	public void testQueryArticle() {
		List<Article> articles = indexDao.queryArticle("lucene");
		System.out.println("结果数：" + articles.size());
		for (Article article : articles) {
			System.out.println("=======================================");
			System.out.println("id=" + article.getId());
			System.out.println("title=" + article.getTitle());
			System.out.println("content=" + article.getContent());
		}
	}
	
	@Test
	public void testQueryArticlePage(){
		List<Article> articles = indexDao.queryArticle("lucene",3,10);
		System.out.println("结果数：" + articles.size());
		for (Article article : articles) {
			System.out.println("=======================================");
			System.out.println("id=" + article.getId());
			System.out.println("title=" + article.getTitle());
			System.out.println("content=" + article.getContent());
		}
	}

}
