package com.galatea.chris.advent2016.solutions.year2015;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.fetchers.ListFetcher;
import com.galatea.chris.advent2016.solutions.Solution;

public class Solution24 implements Solution {
	private static final Logger log = LoggerFactory.getLogger(Solution24.class);

	ListFetcher<Integer> listIntegerFetcher;
	
	public Solution24(ListFetcher<Integer> listIntegerFetcher)
	{
		this.listIntegerFetcher = listIntegerFetcher;
	}
	
	@Override
	public void solveProblem(String[] args) throws Exception {
		List<Integer> fetchedData = this.listIntegerFetcher.fetchData();
		ArrayList<Integer> giftWeights = new ArrayList<Integer>();
		giftWeights.addAll(fetchedData);
		Collections.sort(giftWeights);
		int sum = sum(giftWeights);
		int distributedWeight = sum/3;
		log.info("Total weight is {} and should be {} at each location.", sum, distributedWeight);
		GiftCompartment compartmentConfiguration = null;
		Stack<Integer> stack = new Stack<Integer>();
		stack.add(giftWeights.size()-1);
		//long runTime = 0;
		BigInteger runTime = BigInteger.ONE;
		while(!stack.isEmpty())
		{
			runTime = runTime.add(BigInteger.ONE);
			int currentPackageWeight = sumOfSubSet(giftWeights, stack);
			int nextElement;
			if(currentPackageWeight < distributedWeight)
			{
				nextElement = stack.peek()-1; 
			} else if(currentPackageWeight == distributedWeight)
			{
				if(compartmentConfiguration == null)
				{
					GiftCompartment config = new GiftCompartment();
					config.setGiftWeights(createSubSet(giftWeights, stack));
					compartmentConfiguration = config;
					
					long qe = productOfSubSet(giftWeights, stack);
					String currentSet = getStringForSubSet(giftWeights, stack);
					//log.info("Valid Set of size {}: {}", stack.size(), currentSet);
					//log.info("QE: {}", qe);
				} else if(stack.size() <= compartmentConfiguration.giftWeights.size())
				{
					long qe = productOfSubSet(giftWeights, stack);
					String currentSet = getStringForSubSet(giftWeights, stack);
					//log.info("Valid Set of size {}: {}", stack.size(), currentSet);
					//log.info("QE: {}", qe);
					if(qe < compartmentConfiguration.getProduct())
					{
						GiftCompartment config = new GiftCompartment();
						config.setGiftWeights(createSubSet(giftWeights, stack));
						compartmentConfiguration = config;
					}
				}
				//log.info("Valid Set of size {}: {}", stack.size(), currentSet);
				//log.info("QE: {}", productOfSubSet(giftWeights, stack));
				stack.pop();
				nextElement = stack.pop()-1;
			} else // current weight is greater
			{
				nextElement = stack.pop()-1;
			}
			while(nextElement < 0 && !stack.isEmpty())
			{
				nextElement = stack.pop()-1;
			}
			if(nextElement >= 0) stack.push(nextElement);
		}
		log.info("Best combination with QE {}: {}", compartmentConfiguration.getProduct(), compartmentConfiguration.getGiftWeights() );
		log.info("RunTime: {}", runTime);
	}
	
	public void alternateSolver() throws Exception
	{
		List<Integer> fetchedData = this.listIntegerFetcher.fetchData();
		ArrayList<Integer> giftWeights = new ArrayList<Integer>();
		giftWeights.addAll(fetchedData);
		Collections.sort(giftWeights);
		int sum = sum(giftWeights);
		int distributedWeight = sum/3;
		log.info("Total weight is {} and should be {} at each location.", sum, distributedWeight);
		GiftCompartment configuration = null;
		for(int combinationSize = 1; combinationSize <= giftWeights.size() && configuration == null; combinationSize++)
		{
			Integer[] currentCombination = new Integer[combinationSize];
			currentCombination[0] = giftWeights.size()-1;
			while(currentCombination[0] >= 0)
			{
				//currentCombination[]
			}
		}
	}
	
	public List<Integer> createSubSet(ArrayList<Integer> set, Collection<Integer> indexes)
	{
		List<Integer> subSet = new LinkedList<Integer>();
		for(Integer i : indexes)
		{
			subSet.add(set.get(i));
		}
		return subSet;
	}
	
	public String getStringForSubSet(ArrayList<Integer> set, Stack<Integer> indexes)
	{
		StringBuilder builder = new StringBuilder();
		for(Integer i : indexes)
		{
			builder.append(set.get(i)).append(" ");
		}
		return builder.toString();
	}
	
	public static int sum(Collection<Integer> numbers)
	{
		int sum = 0;
		for(Integer n : numbers)
		{
			sum+= n;
		}
		return sum;
	}
	
	public static int sumOfSubSet(ArrayList<Integer> numberSet, Collection<Integer> indexes)
	{
		int sum = 0;
		for(Integer i : indexes)
		{
			sum+= numberSet.get(i);
		}
		return sum;
	}
	
	public static long productOfSubSet(ArrayList<Integer> numberSet, Collection<Integer> indexes)
	{
		long product = 1;
		for(Integer i : indexes)
		{
			product*= numberSet.get(i);
		}
		return product;
	}
	
	public static class GiftCompartment
	{
		private List<Integer> giftWeights;
		public GiftCompartment()
		{
			this.giftWeights = new LinkedList<Integer>();
		}
		public List<Integer> getGiftWeights() {
			return giftWeights;
		}
		public void setGiftWeights(List<Integer> giftWeights) {
			this.giftWeights = giftWeights;
		}
		
		public long getProduct()
		{
			long product = 1;
			for(Integer i : giftWeights)
			{
				product*= i;
			}
			return product;
		}
		
	}
}
