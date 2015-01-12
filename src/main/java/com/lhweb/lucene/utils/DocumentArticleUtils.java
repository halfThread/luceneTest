package com.lhweb.lucene.utils;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.util.NumericUtils;

import com.lhweb.lucene.po.Article;

public class DocumentArticleUtils {

	/**
	 * 将article转化为Document对象
	 * @param article
	 * @return
	 */
	public static Document articleToDocument(Article article){
		Document doc = new Document();
		doc.add(new Field("id", NumericUtils.intToPrefixCoded(article.getId()), Store.YES, Index.NOT_ANALYZED));
		doc.add(new Field("title", article.getTitle(), Store.YES, Index.ANALYZED));
		doc.add(new Field("content", article.getContent(), Store.YES, Index.ANALYZED));
		return doc;
	}
	
	/**
	 * 将document转化为article对象
	 * @return
	 */
	public static Article docuemtnToArticle(Document doc){
		Article article = new Article();
		article.setId(NumericUtils.prefixCodedToInt(doc.get("id")));
		article.setTitle(doc.get("title"));
		article.setContent(doc.get("content"));
		return article;
	}
}
