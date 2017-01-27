package com.galatea.chris.advent2016.solutions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.galatea.chris.advent2016.fetchers.ListFetcher;
import com.galatea.chris.advent2016.fetchers.RoomsFromFileFetcher;

public class Problem4 implements Solution {
	
	ListFetcher<RoomsFromFileFetcher.Room> dataFetcher;
	
	public Problem4()
	{
		dataFetcher = new RoomsFromFileFetcher("C:\\Users\\Chris\\workspace\\problems\\src\\main\\resources\\Day4\\input.txt");
	}
	public Problem4(ListFetcher<RoomsFromFileFetcher.Room> dataFetcher)
	{
		this.dataFetcher = dataFetcher;
	}
	
	@Override
	public void solveProblem(String[] args) throws Exception
	{
		List<RoomsFromFileFetcher.Room> roomList = this.dataFetcher.fetchData();
		long total = 0;
		for(RoomsFromFileFetcher.Room room : roomList)
		{
			if(isRealRoom(room))
			{
				total += room.sectorId;
				String actualName = encodeStringByShift(room.encyrptedName, room.sectorId);
				if(actualName.contains("north") && actualName.contains("pole")) System.out.println(actualName + ": " + room.toString());
			}
		}
		System.out.println("Total: " + total);
	}
	
	public boolean isRealRoom(RoomsFromFileFetcher.Room room)
	{
		Map<Character, LetterCount> letterMap = new HashMap<Character, LetterCount>();
		//count the letters
		for(int i = 0; i < room.encyrptedName.length(); i++)
		{
			Character letter = room.encyrptedName.charAt(i);
			if(!Character.isLowerCase(letter)) continue;
			if(letterMap.containsKey(letter))
			{
				LetterCount letterCount = letterMap.get(letter);
				letterCount.count++;
			} else
			{
				LetterCount letterCount = new LetterCount();
				letterCount.letter = letter;
				letterCount.count = 1;
				letterMap.put(letter, letterCount);
			}
		}
		//sort letterCounts
		List<LetterCount> characterCounts = new ArrayList<LetterCount>(letterMap.size());
		characterCounts.addAll(letterMap.values());
		Collections.sort(characterCounts);
		//check against room checksum
		for(int i = 0; i < room.checkSum.length(); i++)
		{
			if(room.checkSum.charAt(i) != characterCounts.get(i).letter.charValue()) return false;
		}
		return true;
	}
	
	public String encodeStringByShift(String decodeStr, int shift)
	{
		StringBuilder encodedString = new StringBuilder();
		for(int i = 0; i < decodeStr.length(); i++)
		{
			char currentChar = decodeStr.charAt(i);
			if(!Character.isLowerCase(currentChar))
			{
				encodedString.append(currentChar);
			} else {
				char shiftedValue = (char)((((currentChar - 97) + shift) % 26) + 97);
				encodedString.append(shiftedValue);
			}
		}
		return encodedString.toString();
	}
	
	public static class LetterCount implements Comparable<LetterCount>
	{
		public int count;
		public Character letter;
		

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + count;
			result = prime * result + ((letter == null) ? 0 : letter.hashCode());
			return result;
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LetterCount other = (LetterCount) obj;
			if (count != other.count)
				return false;
			if (letter == null) {
				if (other.letter != null)
					return false;
			} else if (!letter.equals(other.letter))
				return false;
			return true;
		}
		
		

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("LetterCount [count=");
			builder.append(count);
			builder.append(", letter=");
			builder.append(letter);
			builder.append("]");
			return builder.toString();
		}


		@Override
		public int compareTo(LetterCount o) {
			if(count == o.count)
			{
				return letter.compareTo(o.letter); //ascending
				//return o.letter.compareTo(letter); //descending
			} else
			{
				//return Integer.compare(count, o.count); //ascending
				return (Integer.compare(o.count, count)); //descending
			}
		}
		
		
	}
}
