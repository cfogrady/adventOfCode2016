package com.galatea.chris.advent2016.solutions;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.fetchers.Fetcher;
import com.galatea.chris.advent2016.util.StringCharacterIterator;

public class Day9Solution implements Solution {
	private static final Logger log = LoggerFactory
			.getLogger(Day9Solution.class);

	private Fetcher<Iterator<Character>> fileCharacterFetcher;
	private int version;
	
	public Day9Solution(Fetcher<Iterator<Character>> fileCharacterFetcher)
	{
		this.fileCharacterFetcher = fileCharacterFetcher;
		version = 2;
	}
	
	@Override
	public void solveProblem(String[] args) throws Exception {
		Iterator<Character> characterIterator = this.fileCharacterFetcher.fetchData();
		long decompressedLength = getDecompressLength(characterIterator);
		//log.info("Decompression: {}", decompressed);
		log.info("Size of decompressed text: {}", decompressedLength);
	}
	
	public long getDecompressLength(Iterator<Character> characterIterator)
	{
		long count = 0;
		while(characterIterator.hasNext())
		{
			Character currentChar = characterIterator.next();
			if(currentChar == '(')
			{
				Marker marker = getNextMarker(characterIterator);
				long markerResultCount = getMarkerResultStringCount(marker, characterIterator);
				count += markerResultCount;
			} else
			{
				count++;
			}
		}
		return count;
	}
	
//	public String decompress(Iterator<Character> characterIterator)
//	{
//		StringBuilder decompressed = new StringBuilder();
//		while(characterIterator.hasNext())
//		{
//			Character currentChar = characterIterator.next();
//			if(currentChar == '(')
//			{
//				Marker marker = getNextMarker(characterIterator);
//				String markerResult = getMarkerResultString(marker, characterIterator);
//				decompressed.append(markerResult);
//			} else
//			{
//				decompressed.append(currentChar);
//			}
//		}
//		return decompressed.toString();
//	}
	
	public Marker getNextMarker(Iterator<Character> characterIterator)
	{
		Marker marker = new Marker();
		char currentCharacter = '(';
		StringBuilder tmpStrBuilder = new StringBuilder();
		while(characterIterator.hasNext() && currentCharacter != 'x')
		{
			currentCharacter = characterIterator.next();
			if(Character.isDigit(currentCharacter))
			{
				tmpStrBuilder.append(currentCharacter);
			} else if (currentCharacter != 'x')
			{
				log.warn("Malformed marker! Ignoring character.");
			}
		}
		marker.numberOfCharacters = Integer.parseInt(tmpStrBuilder.toString());
		tmpStrBuilder = new StringBuilder();
		while(characterIterator.hasNext() && currentCharacter != ')')
		{
			currentCharacter = characterIterator.next();
			if(Character.isDigit(currentCharacter))
			{
				tmpStrBuilder.append(currentCharacter);
			} else if (currentCharacter != ')')
			{
				log.warn("Malformed marker! Ignoring character.");
			}
		}
		marker.numberOfRepeats = Integer.parseInt(tmpStrBuilder.toString());
		return marker;
	}
	
	public long getMarkerResultStringCount(Marker marker, Iterator<Character> characterIterator)
	{
		try
		{
			long count = 0;
			StringBuilder resultBuilder = new StringBuilder();
			//get repeated String
			for(int i = 0; i < marker.numberOfCharacters && characterIterator.hasNext(); i++)
			{
				char character = characterIterator.next();
				resultBuilder.append(character);
			}
			String strToRepeat = resultBuilder.toString();
			resultBuilder = new StringBuilder();
			count = getDecompressLength(getStringCharacterIterator(strToRepeat));
			//repeat String
//			for(int i = 0; i < marker.numberOfRepeats; i++)
//			{
//				resultBuilder.append(strToRepeat);
//			}
//			return resultBuilder.toString();
			return count*marker.numberOfRepeats;
		} catch (Exception e)
		{
			log.error("Failed on Marker: {}", marker);
			throw e;
		}
		
		
	}
	
	public Iterator<Character> getStringCharacterIterator(String str)
	{
		return new StringCharacterIterator(str);
	}
	
	public static class Marker
	{
		public int numberOfCharacters;
		public int numberOfRepeats;
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Marker [numberOfCharacters=");
			builder.append(numberOfCharacters);
			builder.append(", numberOfRepeats=");
			builder.append(numberOfRepeats);
			builder.append("]");
			return builder.toString();
		}
		
		
	}
}
