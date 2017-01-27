package com.galatea.chris.advent2016.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LetterCount {
	private static final Logger log = LoggerFactory
			.getLogger(LetterCount.class);
	
	private char letter;
	private int count;
	
	public LetterCount(char letter)
	{
		this.letter = letter;
		this.count = 1;
	}
	
	public char getLetter() {
		return letter;
	}
	
	public void setLetter(char letter) {
		this.letter = letter;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + count;
		result = prime * result + letter;
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
		if (letter != other.letter)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LetterCount [letter=");
		builder.append(letter);
		builder.append(", count=");
		builder.append(count);
		builder.append("]");
		return builder.toString();
	}
	
	
}
