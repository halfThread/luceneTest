package com.lhweb.lucene.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.NumericUtils;
import org.apache.lucene.util.Version;

import com.lhweb.lucene.main.IndexDao;
import com.lhweb.lucene.po.Article;
import com.lhweb.lucene.utils.DocumentArticleUtils;
import com.lhweb.lucene.utils.LuceneUtils;

public class IndexDaoImpl implements IndexDao {

	/**
	 * 新增文档到索引库中
	 */
	@Override
	public void saveArticle(Article article) {
		try {
			Document doc = DocumentArticleUtils.articleToDocument(article);
			LuceneUtils.getIndexWriter().addDocument(doc);
			LuceneUtils.getIndexWriter().commit();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 从索引库中删除文档
	 */
	@Override
	public void deleteArticle(Integer articleId) {
		try {
			Term term = new Term("id", NumericUtils.intToPrefixCoded(articleId));
			LuceneUtils.getIndexWriter().deleteDocuments(term);
			//因为效率问题，lucene并不会及时马上将更新提价到硬盘，而是在内存中有缓存，等到一定量的操作后才一次性写入到硬盘中
			LuceneUtils.getIndexWriter().commit();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 更新索引库
	 */
	@Override
	public void updateArticle(Article article) {
		try {
			Term term = new Term("id", NumericUtils.intToPrefixCoded(article.getId()));
			Document doc = DocumentArticleUtils.articleToDocument(article);
//			indexWriter.deleteDocuments(term);
//			indexWriter.addDocument(doc);
			LuceneUtils.getIndexWriter().updateDocument(term, doc);
			LuceneUtils.getIndexWriter().commit();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 全文检索
	 */
	@Override
	public List<Article> queryArticle(String queryStr) {
		IndexSearcher indexSearcher = null;
		List<Article> articles = new ArrayList<Article>();
		
		try {
			QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_30,
					new String[] { "title", "content" }, LuceneUtils.getAnalyzer());
			
			indexSearcher = new IndexSearcher(LuceneUtils.getDirectory());
			
			TopDocs docs = indexSearcher.search(queryParser.parse(queryStr), 10000);
			
			int count = docs.totalHits;
			ScoreDoc[] scoreDocs = docs.scoreDocs;
			
			for(int i=0;i<scoreDocs.length;i++){
				ScoreDoc scoreDoc= scoreDocs[i];
				float score = scoreDoc.score;
				int docId = scoreDoc.doc;
				
				Document doc = indexSearcher.doc(docId);
				Article article = DocumentArticleUtils.docuemtnToArticle(doc);
				articles.add(article);
			}
			return articles;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			try {
				indexSearcher.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<Article> queryArticle(String queryStr, int pageNo, int pageSize) {
		IndexSearcher indexSearcher = null;
		List<Article> articles = new ArrayList<Article>();
		
		try {
			QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_30,
					new String[] { "title", "content" }, LuceneUtils.getAnalyzer());
			
			indexSearcher = new IndexSearcher(LuceneUtils.getDirectory());
			
			TopDocs docs = indexSearcher.search(queryParser.parse(queryStr), pageNo*pageSize);
			
			int count = docs.totalHits;
			ScoreDoc[] scoreDocs = docs.scoreDocs;
			int startIndex = (pageNo-1)*pageSize;
			int endIndex = count < pageNo*pageSize ? count : pageNo*pageSize;
			
			
			for(int i=startIndex ; i < endIndex ; i++){
				ScoreDoc scoreDoc= scoreDocs[i];
				//float score = scoreDoc.score;
				int docId = scoreDoc.doc;
				
				Document doc = indexSearcher.doc(docId);
				Article article = DocumentArticleUtils.docuemtnToArticle(doc);
				articles.add(article);
				
			}
			return articles;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			try {
				indexSearcher.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
