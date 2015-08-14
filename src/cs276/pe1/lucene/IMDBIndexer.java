package cs276.pe1.lucene;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.util.Version;

import cs276.pe1.lucene.IMDBParser.MoviePlotRecord;

public class IMDBIndexer {
	public static final String TITLE = "title";
	public static final String AUTHORs = "authors";
	public static final String PLOTS = "plots";

	public static void main(String[] argv) throws Exception {

		File indexPath = getIndexPath();
		indexPath.mkdir();

		@SuppressWarnings("deprecation")
		IndexWriter writer = new IndexWriter(indexPath, getLuceneAnalyzer(), true, IndexWriter.MaxFieldLength.LIMITED);
		Document d;
		int cnt = 0;
		for (MoviePlotRecord rec : IMDBParser.readRecords()) {

			// index title
			Field contentField = new Field(IMDBIndexer.TITLE, rec.title, Field.Store.YES, Field.Index.TOKENIZED);
			// index authors
			Field fileNameField = new Field(IMDBIndexer.AUTHORs, rec.authors, Field.Store.YES, Field.Index.TOKENIZED);
			// index plots
			Field filePathField = new Field(IMDBIndexer.PLOTS, rec.plots, Field.Store.YES, Field.Index.TOKENIZED);

			d = new Document();

			d.add(contentField);
			d.add(fileNameField);
			d.add(filePathField);

			writer.addDocument(d);

			cnt++;
		}
		writer.close();
		System.out.println("Added a total of " + cnt + " records to the index.");
		System.err.println("Done");
	}

	/**
	 * Get the correct analyzer to use when instantiating a QueryParser.
	 * 
	 * @return the analyzer used to build the index
	 */
	public static Analyzer getLuceneAnalyzer() {
		return new StandardAnalyzer(Version.LUCENE_CURRENT);
	}

	/**
	 * @return the path of the IMDB index
	 */
	public static File getIndexPath() {
		return new File(new File(System.getProperty("user.home")), "cs276-index");
	}
}
