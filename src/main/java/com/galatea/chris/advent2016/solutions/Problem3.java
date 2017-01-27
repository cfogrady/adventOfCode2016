package com.galatea.chris.advent2016.solutions;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.galatea.chris.advent2016.fetchers.ListFetcher;

public class Problem3 implements Solution {
	
	ListFetcher<List<Integer>> dataFetcher;
	
	public Problem3(ListFetcher<List<Integer>> dataFetcher)
	{
		this.dataFetcher = dataFetcher;
	}
	
	@Override
	public void solveProblem(String[] args) throws Exception
	{
		List<List<Integer>> dateGroups = dataFetcher.fetchData();
		int count = 0;
		for(List<Integer> intGrouping : dateGroups)
		{
			//Stream<Integer> intStream = intGrouping.stream();
			Integer max = Collections.max(intGrouping);
			Integer sum = sum(intGrouping);
			//Integer sum = intGrouping.stream().reduce(0, (a, b) -> a+b);
			if(sum - max <= max)
			{
				System.out.println("Set is not a Triangle: " + intGrouping.toString());
			} else
			{
				//System.out.println("Set is a Triangle: " + intGrouping.toString());
				count++;
			}
		}
		System.out.println(count + " possible triangles");
	}
	
	public Integer sum(Collection<Integer> values)
	{
		Integer total = 0;
		for(Integer value : values)
		{
			total += value;
		}
		return total;
	}
}
