package com.galatea.chris.advent2016.solutions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.fetchers.Fetcher;

public class Day7Solution implements Solution {
	private static final Logger log = LoggerFactory
			.getLogger(Day7Solution.class);

	private Fetcher<Iterator<String>> fileLineFetcher;
	
	public Day7Solution(Fetcher<Iterator<String>> fileLineFetcher)
	{
		this.fileLineFetcher = fileLineFetcher;
	}
	
	@Override
	public void solveProblem(String[] args) throws Exception {
		Iterator<String> lineIterator = this.fileLineFetcher.fetchData();
		long tlsCount = 0;
		long sslCount = 0;
		while(lineIterator.hasNext())
		{
			String line = lineIterator.next();
			log.info("Testing line: {}", line);
			List<String> superNetSequences = getSuperNetSequences(line);
			List<String> hyperNetSequences = getHyperNetSequences(line);
			log.debug("SuperNetSequences: {}", hyperNetSequences);
			log.debug("HyperNetSequences: {}", hyperNetSequences);
			if(supportsTLS(superNetSequences, hyperNetSequences)) tlsCount++;
			if(supportsSSL(superNetSequences, hyperNetSequences)) sslCount++;
			
		}
		log.info("Total sequences with TLS: {}", tlsCount);
		log.info("Total sequences with SSL: {}", sslCount);
	}
	
	public boolean supportsSSL(List<String> superNetSequences, List<String> hyperNetSequences)
	{
		Iterator<String> superNetSequenceIterator = superNetSequences.iterator();
		while(superNetSequenceIterator.hasNext())
		{
			String superNetSequence = superNetSequenceIterator.next();
			List<String> abaSequences = this.getABASequences(superNetSequence);
			for(String abaSequence : abaSequences)
			{
				String bab = getBABFromABA(abaSequence);
				for(String hyperNetSequence : hyperNetSequences)
				{
					if(hyperNetSequence.contains(bab)) return true;
				}
			}
		}
		return false;
	}
	
	public boolean supportsTLS(List<String> superNetSequences, List<String> hyperNetSequences)
	{
		boolean hasABBA = false;
		Iterator<String> superNetSequenceIterator = superNetSequences.iterator();
		while(superNetSequenceIterator.hasNext() && !hasABBA)
		{
			String superNetSequence = superNetSequenceIterator.next();
			if(checkABBA(superNetSequence))
			{
				hasABBA = true;
				log.info("Super Net Sequence {} has ABBA", superNetSequence);
			}
		}
		if(hasABBA)
		{
			boolean ABBAinHyperNet = false;
			Iterator<String> hyperNetSequenceIterator = hyperNetSequences.iterator();
			while(hyperNetSequenceIterator.hasNext() && !ABBAinHyperNet)
			{
				String hyperNetSequence = hyperNetSequenceIterator.next();
				if(checkABBA(hyperNetSequence))
				{
					ABBAinHyperNet = true;
					log.info("HyperNet {} has ABBA. No TLS support.", hyperNetSequence);
				}
			}
			if(!ABBAinHyperNet)
			{
				return true;
			}
		}
		return false;
	}
	
	
	public boolean checkABBA(String sequence)
	{
		for(int i = 0; i < sequence.length()-3; i++)
		{
			if(sequence.charAt(i) == sequence.charAt(i+3) && sequence.charAt(i+1) == sequence.charAt(i+2) && sequence.charAt(i) != sequence.charAt(i+1))
			{
				log.debug("ABBA: {}", sequence.substring(i, i+4));
				return true;
			}
		}
		return false;
	}
	
	public List<String> getABASequences(String sequence)
	{
		List<String> abaSequences = new LinkedList<String>();
		for(int i = 0; i < sequence.length()-2; i++)
		{
			if(sequence.charAt(i) == sequence.charAt(i+2) && sequence.charAt(i) != sequence.charAt(i+1))
			{
				log.debug("ABA: {}", sequence.substring(i, i+3));
				abaSequences.add(sequence.substring(i, i+3));
			}
		}
		return abaSequences;
	}
	
	public String getBABFromABA(String abaSequence)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(abaSequence.charAt(1));
		builder.append(abaSequence.charAt(0));
		builder.append(abaSequence.charAt(1));
		return builder.toString();
	}
	
	public List<String> getSuperNetSequences(String line)
	{
		List<String> sequences = new LinkedList<String>();
		int currentIndex = 0;
		while(currentIndex < line.length())
		{
			int startOfHyperNet = line.indexOf('[', currentIndex);
			if(startOfHyperNet == -1)
			{
				//no more hyper nets
				sequences.add(line.substring(currentIndex));
				currentIndex = line.length();
			} else
			{
				sequences.add(line.substring(currentIndex, startOfHyperNet));
				int endOfHyperNet = line.indexOf(']', currentIndex);
				currentIndex = endOfHyperNet+1;
			}
		}
		return sequences;
	}
	
	public List<String> getHyperNetSequences(String line)
	{
		List<String> sequences = new LinkedList<String>();
		int currentIndex = 0;
		while(currentIndex < line.length())
		{
			int startOfHyperNet = line.indexOf('[', currentIndex);
			if(startOfHyperNet == -1)
			{
				//no more hyper nets
				currentIndex = line.length();
			} else
			{
				int endOfHyperNet = line.indexOf(']', currentIndex);
				sequences.add(line.substring(startOfHyperNet+1,endOfHyperNet));
				currentIndex = endOfHyperNet+1;
			}
		}
		return sequences;
	}
}
