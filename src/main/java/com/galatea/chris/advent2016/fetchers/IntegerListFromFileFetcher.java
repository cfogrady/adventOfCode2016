package com.galatea.chris.advent2016.fetchers;

import java.text.ParseException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntegerListFromFileFetcher implements ListFetcher<Integer> {
	private static final Logger log = LoggerFactory
			.getLogger(IntegerListFromFileFetcher.class);
	
	Fetcher<Iterator<String>> fileLineFetcher;
	
	public IntegerListFromFileFetcher(String fileName)
	{
		this(new FileLineFetcher(fileName));
	}
	
	public IntegerListFromFileFetcher(Fetcher<Iterator<String>> fileLineFetcher)
	{
		this.fileLineFetcher = fileLineFetcher;
	}

	@Override
	public List<Integer> fetchData() throws Exception {
		List<Integer> numbers = new LinkedList<Integer>();
		Iterator<String> fileLineIterator = this.fileLineFetcher.fetchData();
		while(fileLineIterator.hasNext())
		{
			String line = fileLineIterator.next();
			try
			{
				numbers.add(Integer.parseInt(line.trim()));
			} catch (NumberFormatException nfe)
			{
				log.error("Failed to parse line: {}", line);
				throw nfe;
			}
		}
		return numbers;
	}
}
