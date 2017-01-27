package com.galatea.chris.advent2016.comparators;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.util.LetterCount;

public class LetterCountDescending implements Comparator<LetterCount> {
	private static final Logger log = LoggerFactory
			.getLogger(LetterCountDescending.class);

	@Override
	public int compare(LetterCount o1, LetterCount o2) {
		return Integer.compare(o2.getCount(), o1.getCount());
	}
}
