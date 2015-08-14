package cs276.pe1.spell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Domain {
	private double score;
	private String term;

	public Domain(String term, double score) {
		this.score = score;
		this.term = term;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public static Comparator<Domain> ScoreComparatorAccending = new Comparator<Domain>() {
		public int compare(Domain s1, Domain s2) {
			Double score1 = s1.getScore();
			Double score2 = s2.getScore();
			return score1.compareTo(score2);
		}
	};

	public static Comparator<Domain> ScoreComparatorDecending = new Comparator<Domain>() {
		public int compare(Domain s1, Domain s2) {
			Double score1 = s1.getScore();
			Double score2 = s2.getScore();
			return score2.compareTo(score1);
		}
	};

	@Override
	public String toString() {
		return "[ term=" + term + ", score=" + score + "]";
	}

	public static void main(String[] args) {
		List<Domain> list = new ArrayList<Domain>();
		list.add(new Domain("10", 10));
		list.add(new Domain("3", 3));
		list.add(new Domain("14", 14));
		list.add(new Domain("1", 1));
		list.add(new Domain("10", 10));
		list.add(new Domain("17", 17));
		list.add(new Domain("5", 5));
		System.out.println(list);
		Collections.sort(list, Domain.ScoreComparatorDecending);
		System.out.println(list);
	}
}
