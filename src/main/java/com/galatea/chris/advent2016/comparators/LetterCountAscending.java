package com.galatea.chris.advent2016.comparators;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.util.LetterCount;

public class LetterCountAscending implements Comparator<LetterCount> {
	private static final Logger log = LoggerFactory
			.getLogger(LetterCountAscending.class);

	@Override
	public int compare(LetterCount o1, LetterCount o2) {
		return Integer.compare(o1.getCount(), o2.getCount());
	}
}
