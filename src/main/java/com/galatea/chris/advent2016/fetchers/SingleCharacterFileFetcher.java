package com.galatea.chris.advent2016.fetchers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Reads from a file and extracts single characters. Each element in the first list represents a line. Each element in the second list is a character
 * @author cogrady
 *
 */
public class SingleCharacterFileFetcher implements ListFetcher<List<Character>> {
	private static final Logger log = LoggerFactory
			.getLogger(SingleCharacterFileFetcher.class);

	private Fetcher<Iterator<String>> fileLineFetcher;
	
	public SingleCharacterFileFetcher(String fileLocation)
	{
		this(new FileLineFetcher(fileLocation));
	}
	
	public SingleCharacterFileFetcher(Fetcher<Iterator<String>> fileLineFetcher)
	{
		this.fileLineFetcher = fileLineFetcher;
	}
	
	@Override
	public List<List<Character>> fetchData() throws Exception {
		List<List<Character>> resultList = new LinkedList<List<Character>>();
		Iterator<String> fileLineIterator = fileLineFetcher.fetchData();
		while(fileLineIterator.hasNext())
		{
			String line = fileLineIterator.next();
			List<Character> lineList = new LinkedList<Character>();
			for(int i = 0; i < line.length(); i++)
			{
				lineList.add(line.charAt(i));
			}
			resultList.add(lineList);
		}
		return resultList;
	}
}
