package cs276.pe1.spell;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs276.util.IOUtils;
import cs276.util.StringUtils;

public class KGramWithEditDistanceSpellingCorrector implements SpellingCorrector {
	/**
	 * The implementation contain map as dictionary and posting list.
	 */
	private static Map<String, Set<String>> kGramPostings = new HashMap<String, Set<String>>();

	public KGramWithEditDistanceSpellingCorrector() {

		File path = new File("data/big.txt.gz");
		for (String line : IOUtils.readLines(IOUtils.openFile(path))) {
			for (String word : StringUtils.tokenize(line)) {
				ArrayList<String> bigrams = StringUtils.bigrams(word);
				for (String bigram : bigrams) {
					if (kGramPostings.containsKey(bigram)) {
						kGramPostings.get(bigram).add(word);
					} else {
						Set<String> postings = new HashSet<String>();
						postings.add(word);
						kGramPostings.put(bigram, postings);
					}
				}
			}
		}

	}

	public List<String> corrections(String word) {
		ArrayList<String> bigrams = StringUtils.bigrams(word);
		Set<String> suggestions = new HashSet<String>();
		ArrayList<Domain> suggectionList = new ArrayList<Domain>();
		ArrayList<String> orderedList = new ArrayList<String>();

		for (String bigram : bigrams) {
			if (kGramPostings.containsKey(bigram)) {
				suggestions.addAll(kGramPostings.get(bigram));
			}
		}

		for (String suggestion : suggestions) {
			double jCoff = StringUtils.levenshtein(word, suggestion);
			suggectionList.add(new Domain(suggestion, jCoff));
		}

		Collections.sort(suggectionList, Domain.ScoreComparatorAccending);
		for (Domain domain : suggectionList) {
			orderedList.add(domain.getTerm());
		}

		return orderedList;
	}
}
