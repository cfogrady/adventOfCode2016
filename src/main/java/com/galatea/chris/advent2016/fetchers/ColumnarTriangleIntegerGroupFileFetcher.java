package com.galatea.chris.advent2016.fetchers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ColumnarTriangleIntegerGroupFileFetcher implements ListFetcher<List<Integer>> {
	
	private Fetcher<Iterator<String>> fileLineFetcher;
	
	public ColumnarTriangleIntegerGroupFileFetcher(String fileName)
	{
		this.fileLineFetcher = new FileLineFetcher(fileName);
	}
	
	public List<List<Integer>> fetchData() throws Exception
	{
		List<List<Integer>> resultSet = new LinkedList<List<Integer>>();
		List<List<Integer>> cacheSet = new ArrayList<List<Integer>>(3);
		cacheSet.add(null);
		cacheSet.add(null);
		cacheSet.add(null);
		
		Iterator<String> iterator = fileLineFetcher.fetchData();
		int count = 0;
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
				cacheSet.set(count, currentSet);
			} catch (Exception e)
			{
				System.out.println("Failed: " + line);
				e.printStackTrace();
				throw e;
			}
			if(count ==2)
			{
				for(int i = 0; i < 3; i ++)
				{
					List<Integer> triangleSet = new ArrayList<Integer>(3);
					triangleSet.add(cacheSet.get(0).get(i));
					triangleSet.add(cacheSet.get(1).get(i));
					triangleSet.add(cacheSet.get(2).get(i));
					resultSet.add(triangleSet);
				}
				count = 0;
			} else
			{
				count++;
			}
		}
		return resultSet;
	}

}
