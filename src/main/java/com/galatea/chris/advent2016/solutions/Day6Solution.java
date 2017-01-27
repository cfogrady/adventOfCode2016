package com.galatea.chris.advent2016.solutions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.fetchers.Fetcher;
import com.galatea.chris.advent2016.util.LetterCount;

public class Day6Solution implements Solution {
	private static final Logger log = LoggerFactory
			.getLogger(Day6Solution.class);

	private Fetcher<Iterator<String>> fileLineFetcher;
	private Comparator<LetterCount> letterCountComparator;
	
	
	public Day6Solution(Fetcher<Iterator<String>> fileLineFetcher, Comparator<LetterCount> letterCountComparator)
	{
		this.fileLineFetcher = fileLineFetcher;
		this.letterCountComparator = letterCountComparator;
	}
	
	@Override
	public void solveProblem(String[] args) throws Exception {
		ArrayList<Map<Character, LetterCount>> letterPositionCounts = new ArrayList<Map<Character,LetterCount>>();
		Iterator<String> fileLineIterator = this.fileLineFetcher.fetchData();
		while(fileLineIterator.hasNext())
		{
			String line = fileLineIterator.next();
			for(int i = 0; i < line.length(); i++)
			{
				char letter = line.charAt(i);
				if(i >= letterPositionCounts.size())
				{
					Map<Character,LetterCount> letterCounts = new HashMap<Character,LetterCount>();
					letterPositionCounts.add(letterCounts);
				}
				Map<Character,LetterCount> letterCounts = letterPositionCounts.get(i);
				LetterCount letterCount = letterCounts.get(letter);
				if(letterCount == null)
				{
					letterCount = new LetterCount(letter);
					letterCounts.put(letter, letterCount);
				} else
				{
					letterCount.setCount(letterCount.getCount()+1);
				}
			}
		}
		StringBuilder result = new StringBuilder();
		for(Map<Character, LetterCount> letterCounts : letterPositionCounts)
		{
			ArrayList<LetterCount> allLettersUsed = new ArrayList<LetterCount>(letterCounts.size());
			allLettersUsed.addAll(letterCounts.values());
			Collections.sort(allLettersUsed, letterCountComparator);
			result.append(allLettersUsed.get(0).getLetter());
		}
		log.info("Message Received: {}", result);
	}
}
