package com.galatea.chris.advent2016.solutions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.galatea.chris.advent2016.fetchers.Fetcher;
import com.galatea.chris.advent2016.fetchers.ListFetcher;
import com.galatea.chris.advent2016.fetchers.RoomsFromFileFetcher;
import com.galatea.chris.advent2016.util.LetterCount;


public class UglySolution4 implements Solution {

	@Override
	public void solveProblem(String[] args) throws Exception
	{
		File file = new File("C:\\Users\\Chris\\workspace\\Advent2016\\src\\main\\resources\\Day4\\input.txt");
		FileReader fr = new FileReader(file);
		BufferedReader reader = new BufferedReader(fr);
		//aaaaa-bbb-z-y-x-123[abxyz]
		//encrypyed-room-name-roomId[checkSum]
		String line = null;
		int count = 0;
		int total = 0;
		while((line = reader.readLine()) != null)
		{
			//System.out.println("Working on room: " + line);
			if(isRealRoom(line))
			{
				count++;
				total += getRoomId(line);
				String actualName = encodeStringByShift(getEncryptedRoomName(line), getRoomId(line));
				System.out.println("Room being published : " + actualName + " : " + line);
				publishDownStream(actualName, line);
			}
		}
		System.out.println("Real rooms send downstream: " + count);
		System.out.println("Sum of real room Ids: " + total);
	}
	
	/**
	 * Ignore this method. This method would publish to a hypothetical downstream system.
	 * @param realName
	 * @param line
	 */
	public void publishDownStream(String realName, String line)
	{
		//ignore this method. This method would publish to a hypothetical downstream system.
	}
	
	public boolean isRealRoom(String line)
	{
		Map<Character, LetterCount> letterMap = new HashMap<Character, LetterCount>();
		//count the letters
		for(int i = 0; i < getEncryptedRoomName(line).length(); i++)
		{
			Character letter = getEncryptedRoomName(line).charAt(i);
			if(letter == '-') continue;
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
		List<LetterCount> characterCounts = new ArrayList<LetterCount>(letterMap.size());
		characterCounts.addAll(letterMap.values());
		sort(characterCounts);
		for(int i = 0; i < getCheckSum(line).length(); i++)
		{
			if(getCheckSum(line).charAt(i) != characterCounts.get(i).letter) return false;
		}
		return true;
	}
	
	public void sort(List<LetterCount> characterCounts)
	{
		for(int i = 0; i < characterCounts.size(); i++)
		{
			for(int j = 0; j < characterCounts.size()-i-1; j++)
			{
				LetterCount currentElement = characterCounts.get(j);
				LetterCount nextElement = characterCounts.get(j+1);
				if(currentElement.count < nextElement.count || (currentElement.count == nextElement.count && currentElement.letter > nextElement.letter))
				{
					characterCounts.set(j, nextElement);
					characterCounts.set(j+1, currentElement);
				}
			}
		}
	}
	
	public String getEncryptedRoomName(String line)
	{
		int startOfSector = line.lastIndexOf('-');
		String encryptedName = line.substring(0, startOfSector);
		return encryptedName;
	}
	
	public Integer getRoomId(String line)
	{
		int startOfSector = line.lastIndexOf('-');
		int startOfCheck = line.lastIndexOf('[');
		String sector = line.substring(startOfSector+1, startOfCheck);
		return Integer.parseInt(sector);
	}
	
	public String getCheckSum(String line)
	{
		int startOfCheck = line.lastIndexOf('[');
		String checkSum = line.substring(startOfCheck+1, line.length()-1);
		return checkSum;
	}
	
	public String encodeStringByShift(String decodeStr, int shift)
	{
		StringBuilder encodedString = new StringBuilder();
		for(int i = 0; i < decodeStr.length(); i++)
		{
			char currentChar = decodeStr.charAt(i);
			if(currentChar == '-')
			{
				encodedString.append(currentChar);
			} else {
				char shiftedValue = (char)((((currentChar - 97) + shift) % 26) + 97);
				encodedString.append(shiftedValue);
			}
		}
		return encodedString.toString();
	}
	
	public static class LetterCount
	{
		public int count;
		public char letter;
				
	}
}
