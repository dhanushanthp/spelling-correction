package cs276.pe1.lucene;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.util.Version;

public class SearchIndex {
	public static void searchIndex(String searchString,String searchOption) throws IOException, ParseException {
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);
		Query q = new QueryParser(searchOption, analyzer).parse(searchString);

		int hitsPerPage = 20;
		@SuppressWarnings("deprecation")
		IndexReader reader = IndexReader.open(IMDBIndexer.getIndexPath());
		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		System.out.println("Found " + hits.length + " hits.");
		
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get(IMDBIndexer.TITLE) + "\n" + d.get(IMDBIndexer.PLOTS));
		}
	}

	public static void main(String[] args) throws IOException, ParseException {
		searchIndex("'Marco'",IMDBIndexer.AUTHORs);
		searchIndex("1-800-Missing",IMDBIndexer.TITLE);
	}
}
