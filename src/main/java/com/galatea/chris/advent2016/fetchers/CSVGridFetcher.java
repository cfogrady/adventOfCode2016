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

public class CSVGridFetcher implements Fetcher<Character[][]> {
	private static final Logger log = LoggerFactory
			.getLogger(CSVGridFetcher.class);
	
	private Fetcher<Iterator<String>> fileLineFetcher;
	
	public CSVGridFetcher(String fileLocation)
	{
		this(new FileLineFetcher(fileLocation));
	}
	
	public CSVGridFetcher(Fetcher<Iterator<String>> fileLineFetcher)
	{
		this.fileLineFetcher = fileLineFetcher;
	}
	
	@Override
	public Character[][] fetchData() throws Exception {
		Character[][] resultGrid = null;
		int y = 0;
		String line = null;
		
		Iterator<String> fileLineIterator = fileLineFetcher.fetchData();
		while(fileLineIterator.hasNext())
		{
			line = fileLineIterator.next();
			String[] values = line.split(",");
			if(resultGrid == null) resultGrid = new Character[values.length][values.length];
			for(int x = 0; x < values.length; x++)
			{
				if(values[x] == null || values[x].isEmpty() || values[x].equals(" "))
				{
					resultGrid[x][y] = null;
				} else
				{
					resultGrid[x][y] = values[x].charAt(0);
				}
			}
			y++;
		}
		return resultGrid;
	}
}
