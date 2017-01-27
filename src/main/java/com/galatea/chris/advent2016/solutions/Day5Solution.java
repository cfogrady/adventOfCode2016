package com.galatea.chris.advent2016.solutions;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Day5Solution implements Solution {
	private static final Logger log = LoggerFactory
			.getLogger(Day5Solution.class);
	
	public static int CODE_LENGTH = 8;
	
	@Override
	public void solveProblem(String[] args) throws Exception {
		boolean usePosition = true;
		Character[] passwordArray = new Character[CODE_LENGTH];
		int lettersFound = 0;
		String doorId = "wtnhxymk";
		//String doorId = "abc";
		MessageDigest digestGenerator = MessageDigest.getInstance("MD5");
		StringBuilder password = new StringBuilder();
		for(int index = 0; lettersFound < CODE_LENGTH; index++)
		{
			String toHash = doorId + Integer.toString(index);
			log.debug("Testing {}", toHash);
			digestGenerator.update(toHash.getBytes("UTF-8"));
			byte[] resultDigest = digestGenerator.digest();
			StringBuilder hexStringBuilder = new StringBuilder();
			if(buildHexidecimal(resultDigest, hexStringBuilder) != 0) continue;
			String hexResult = hexStringBuilder.toString();
			log.debug("Result = {}", hexResult);
			if(hexResult.startsWith("00000"))
			{
				if(usePosition)
				{
					char character = hexResult.charAt(5);
					if(character < 48 || character > 55) continue;
					int position = Integer.parseInt(hexResult.substring(5, 6));
					if(passwordArray[position] != null) continue;
					passwordArray[position] = hexResult.charAt(6);
					lettersFound++;
				} else
				{
					password.append(hexResult.subSequence(5, 6));
					lettersFound++;
				}
			}
		}
		if(usePosition)
		{
			for(int i = 0; i < CODE_LENGTH; i++)
			{
				password.append(passwordArray[i]);
			}
		}
		log.info("Code: {}", password);
	}
	
	public int buildHexidecimal(byte[] bytes, StringBuilder hexString)
	{
		log.debug("Length: {}", bytes.length *8);
		int i = 0;
		if(bytes[0] != 0 || bytes[1] != 0) return -1;
		for(byte b : bytes)
		{
			int byteAsNumber = (int) b;
			byteAsNumber = byteAsNumber < 0 ? 256 + byteAsNumber : byteAsNumber; 
			int digit1 = byteAsNumber/16;
			int digit2 = byteAsNumber - digit1*16;
			hexString.append(hexFromNumber(digit1));
			hexString.append(hexFromNumber(digit2));
			i++;
		}
		return 0;
	}
	
	public Character hexFromNumber(int number)
	{
		if(number < 10)
		{
			return Character.valueOf((char)(number+48));
		} else
		{
			return Character.valueOf((char)((number - 10) + 97));
		}
	}
}
