package com.lhweb.lucene.utils;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

public class LuceneUtils {
	private static Directory directory = null; // 索引库目录
	private static Analyzer analyzer = null; // 分词器

	private static IndexWriter indexWriter = null;

	static {
		try {
			directory = FSDirectory.open(new File("./indexDir"));
			analyzer = new StandardAnalyzer(Version.LUCENE_30);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Directory getDirectory() {
		return directory;
	}

	public static void setDirectory(Directory directory) {
		LuceneUtils.directory = directory;
	}

	public static Analyzer getAnalyzer() {
		return analyzer;
	}

	public static void setAnalyzer(Analyzer analyzer) {
		LuceneUtils.analyzer = analyzer;
	}

	/**
	 * 初始化indexWriter
	 * 
	 * @return
	 */
	public static IndexWriter getIndexWriter() {
		if (indexWriter == null) {
			synchronized (LuceneUtils.class) {
				if (indexWriter == null) {
					try {
						indexWriter = new IndexWriter(directory, analyzer,MaxFieldLength.LIMITED);
						/**
						 * lucene每做一次增删改操作时并不会马上去更改原数据文件，因为如果原文件过大的话，
						 * 会增加磁盘IO的压力。lucene的方案是，对每次的增删改操作生成一个.cfs/.del/的小文件
						 * 记录该操作。所以每次查询时，先查询原数据文件，再根据小文件修改查询结果。
						 * 但如果小文件数量过多时，同样会增加磁盘IO的压力，所以在操作达到一定数量时，
						 * 应该执行optimize()，将操作应用于原数据文件，合并小文件和原数据文件。
						 * lucene默认的操作次数为10，既小文件数量达到10个时，执行合并文件的操作，
						 * 以达到优化IO的目的。setMergeFactor()方法可以修改该值。
						 */
						//indexWriter.optimize();
						indexWriter.setMergeFactor(5);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
			}
			
			//在JVM退出之前执行线程，适用于JAVA项目，web项目应该在servlerContext监听器中执行  
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run() {
					try {
						indexWriter.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
				}
				
			});
		}
		return indexWriter;
	}
}
