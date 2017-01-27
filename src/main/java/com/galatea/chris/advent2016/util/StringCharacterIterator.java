package com.galatea.chris.advent2016.util;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringCharacterIterator implements Iterator<Character>
{
	private static final Logger log = LoggerFactory
			.getLogger(StringCharacterIterator.class);
	
	private String str;
	private int index;
	
	public StringCharacterIterator(String str)
	{
		this.str = str;
		this.index = 0;
	}

	@Override
	public boolean hasNext() {
		return index < str.length();
	}

	@Override
	public Character next() {
		char next = str.charAt(index);
		index++;
		return next;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("The remove method is not functional for the StringCharacterIterator");
	}
}

