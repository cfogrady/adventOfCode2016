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

public class FileLineFetcher implements Fetcher<Iterator<String>> {
	private static final Logger log = LoggerFactory
			.getLogger(FileLineFetcher.class);

	private String fileName;
	
	public FileLineFetcher(String fileName)
	{
		this.fileName = fileName;
	}
	
	@Override
	public FileLineIterator fetchData() throws Exception {
		FileLineIterator iterator = new FileLineIterator(fileName);
		iterator.open();
		// TODO Auto-generated method stub
		return iterator;
	}
	
	public static class FileLineIterator implements Iterator<String>
	{
		BufferedReader reader;
		String nextLine;
		String fileName;
		
		public FileLineIterator(String fileName)
		{
			this.reader = null;
			this.nextLine = null;
			this.fileName = fileName;
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
				this.nextLine = reader.readLine();
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
		
		public String next()
		{
			String returnLine = this.nextLine;
			if(reader != null)
			{
				try {
					this.nextLine = reader.readLine();
				} catch (IOException e) {
					log.warn("Failed to fetch the next line from the file. Assuming next line is null!", e);
				}
				if(this.nextLine == null) close();
			} else
			{
				this.nextLine = null;
			}
			return returnLine;
		}
		
		public boolean hasNext()
		{
			return this.nextLine != null;
		}

		@Override
		public void remove() {
			log.info("Cannot remove file line from FileLineIterator");
			throw new UnsupportedOperationException();
			
		}
		
	}
}
