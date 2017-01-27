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
 * Takes a CSV and generates a raw list from the input data
 * @author cogrady
 *
 */
public class CSVListFetcher implements ListFetcher<String> {
	private static final Logger log = LoggerFactory.getLogger(CSVListFetcher.class);
	
	private Fetcher<Iterator<String>> fileLineFetcher;
	
	public CSVListFetcher(String fileLocation)
	{
		this(new FileLineFetcher(fileLocation));
	}
	
	public CSVListFetcher(Fetcher<Iterator<String>> fileLineFetcher)
	{
		this.fileLineFetcher = fileLineFetcher;
	}
	
	@Override
	public List<String> fetchData() throws Exception {
		List<String> resultList = new LinkedList<String>();

		Iterator<String> fileLineIterator = this.fileLineFetcher.fetchData();
		while(fileLineIterator.hasNext())
		{
			String line = fileLineIterator.next();
			String[] values = line.split(",");
			for(String value : values)
			{
				resultList.add(value.trim());
			}
		}
		return resultList;
	}

}
