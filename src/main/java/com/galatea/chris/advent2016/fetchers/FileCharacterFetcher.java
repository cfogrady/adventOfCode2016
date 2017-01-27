package com.galatea.chris.advent2016.fetchers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileCharacterFetcher implements Fetcher<Iterator<Character>> {
	private static final Logger log = LoggerFactory
			.getLogger(FileCharacterFetcher.class);

	private String fileName;
	private boolean skipWhiteSpace;
	
	public FileCharacterFetcher(String fileName, boolean skipWhiteSpace)
	{
		this.fileName = fileName;
		this.skipWhiteSpace = skipWhiteSpace;
	}
	
	@Override
	public Iterator<Character> fetchData() throws Exception {
		FileCharacterIterator iterator = new FileCharacterIterator(this.fileName, this.skipWhiteSpace);
		iterator.open();
		// TODO Auto-generated method stub
		return iterator;
	}
	
	public static class FileCharacterIterator implements Iterator<Character>
	{
		BufferedReader reader;
		int nextCharacter;
		String fileName;
		boolean skipWhiteSpace;
		
		public FileCharacterIterator(String fileName, boolean skipWhiteSpace)
		{
			this.reader = null;
			this.nextCharacter = -1;
			this.fileName = fileName;
			this.skipWhiteSpace = skipWhiteSpace;
		}
		
		private void open() throws Exception
		{
			try
			{
				File file = new File(this.fileName);
				if(!file.exists())
				{
					log.info("Failed to get file {} from disk. Attempting to locate file in resources.", this.fileName);
					URL url = getClass().getClassLoader().getResource(this.fileName);
					file = new File(url.getFile());
				}
				log.debug("Using file at {}", file.getAbsoluteFile());
				FileReader fr = new FileReader(file);
				this.reader = new BufferedReader(fr);
				this.nextCharacter = (char)reader.read();
			}
			catch (FileNotFoundException e) {
				log.error("Failed to find file {}", this.fileName);
				throw e;
			}
		}
		
		public void close()
		{
			if(reader != null)
			{
				try {
					reader.close();
				} catch (IOException e) {
					log.warn("Failed to close the BufferedReader on the file. Assuming it has already been closed.", e);
				}
				reader = null;
			}
		}
		
		public Character next()
		{
			int returnLine = this.nextCharacter;
			if(reader != null)
			{
				try {
					this.nextCharacter = reader.read();
					if(skipWhiteSpace)
					{
						while(nextCharacter != 1 && Character.isWhitespace((char)nextCharacter))
						{
							this.nextCharacter = reader.read();
						}
					}
				} catch (IOException e) {
					log.warn("Failed to fetch the next line from the file. Assuming next line is null!", e);
				}
				if(this.nextCharacter == -1) close();
			} else
			{
				this.nextCharacter = -1;
			}
			return (char)returnLine;
		}
		
		public boolean hasNext()
		{
			return this.nextCharacter != -1;
		}

		@Override
		public void remove() {
			log.info("Cannot remove file character from FileCharacterIterator");
			throw new UnsupportedOperationException();
			
		}
		
	}
	
	
}
