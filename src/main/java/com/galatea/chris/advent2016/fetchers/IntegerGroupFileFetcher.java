package com.galatea.chris.advent2016.fetchers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class IntegerGroupFileFetcher implements ListFetcher<List<Integer>>{
	
	private Fetcher<Iterator<String>> fileLineReader;
	
	public IntegerGroupFileFetcher(String fileName)
	{
		this.fileLineReader = new FileLineFetcher(fileName);
	}
	
	public List<List<Integer>> fetchData() throws Exception
	{
		List<List<Integer>> resultSet = new LinkedList<List<Integer>>();
		
		Iterator<String> iterator = fileLineReader.fetchData();
		while(iterator.hasNext())
		{
			String line = iterator.next();
			try
			{
				String[] parts = line.split("\\s+");
				List<Integer> currentSet = new ArrayList<Integer>(3);
			
				for(String part : parts)
				{
					if(part == null || part.isEmpty()) continue;
					currentSet.add(Integer.parseInt(part));
				}
				resultSet.add(currentSet);
			} catch (Exception e)
			{
				System.out.println("Failed: " + line);
				e.printStackTrace();
				throw e;
			}
						
		}
		return resultSet;
	}

}
