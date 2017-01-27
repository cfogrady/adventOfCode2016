package com.galatea.chris.advent2016.solutions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.fetchers.Fetcher;

public class Day8Solution implements Solution {
	private static final Logger log = LoggerFactory
			.getLogger(Day8Solution.class);

	private Fetcher<Iterator<String>> instructionDataFetcher;
	
	public Day8Solution(Fetcher<Iterator<String>> instructionDataFetcher)
	{
		this.instructionDataFetcher = instructionDataFetcher;
	}
	
	@Override
	public void solveProblem(String[] args) throws Exception {
		Iterator<String> instructionIterator = this.instructionDataFetcher.fetchData();
		boolean[][] virtualScreen = initializeScreen(50, 6);
		//boolean[][] virtualScreen = initializeScreen(7, 3);
		while(instructionIterator.hasNext())
		{
			String line = instructionIterator.next();
			if(line.startsWith("rect"))
			{
				handleRect(line, virtualScreen);
			} else if (line.startsWith("rotate") && line.contains("column"))
			{
				handleColShift(line, virtualScreen);
			} else if (line.startsWith("rotate") && line.contains("row"))
			{
				handleRowShift(line, virtualScreen);
			} else
			{
				log.warn("Ignoring bad instruction: {}", line);
			}
			log.info("-------------------Screen Refresh-----------------");
			log.info("{}{}",System.lineSeparator(),getScreenDisplay(virtualScreen));
		}
		log.info("Pixels On Screen: {}", countPixelsOn(virtualScreen));
	}
	
	String getScreenDisplay(boolean[][] virtualScreen)
	{
		StringBuilder screenDisplay = new StringBuilder();
		for(int y = 0; y < virtualScreen[0].length; y++)
		{
			for(int x = 0; x < virtualScreen.length; x++)
			{
				screenDisplay.append(virtualScreen[x][y] ? "#" : " ");
			}
			screenDisplay.append(System.lineSeparator());
		}
		return screenDisplay.toString();
	}
	
	int countPixelsOn(boolean[][] virtualScreen)
	{
		int count = 0;
		for(int i = 0; i < virtualScreen.length; i++)
		{
			for(int j = 0; j < virtualScreen[i].length; j++)
			{
				if(virtualScreen[i][j]) count++;
			}
		}
		return count;
	}
	
	public boolean[][] initializeScreen(int x, int y)
	{
		return new boolean[x][y];
	}
	
	public void handleColShift(String line, boolean[][] virtualScreen)
	{
		try
		{
			int xIndex = line.indexOf("x");
			String xStr = line.substring(xIndex+2, line.indexOf(' ', xIndex+2));
			int x = Integer.parseInt(xStr);
			String shiftStr = line.substring(line.indexOf(' ', line.indexOf("by"))+1);
			int shift = Integer.parseInt(shiftStr);
			int height = virtualScreen[x].length;
			Queue<Boolean> buffer = new LinkedList<Boolean>();
			for(int i = 0; i < virtualScreen[x].length; i++)
			{
				if(buffer.size() < (shift % height))
				{
					buffer.add(virtualScreen[x][(i+shift) % height ]);
					virtualScreen[x][(i+shift) % height] = virtualScreen[x][i];
				} else
				{
					buffer.add(virtualScreen[x][(i+shift) % height ]);
					virtualScreen[x][(i+shift) % height] = buffer.poll();
				}
			}
		} catch (Exception e)
		{
			log.error("Failed to handle instruction: {}", line);
			throw e;
		}
	}
	
	public void handleRowShift(String line, boolean[][] virtualScreen)
	{
		try
		{
			int yIndex = line.indexOf('y');
			String yStr = line.substring(yIndex+2, line.indexOf(' ', yIndex+2));
			int y = Integer.parseInt(yStr);
			String shiftStr = line.substring(line.indexOf(' ', line.indexOf("by"))+1);
			int shift = Integer.parseInt(shiftStr);
			int width = virtualScreen.length;
			Queue<Boolean> buffer = new LinkedList<Boolean>();
			for(int i = 0; i < virtualScreen.length; i++)
			{
				int shiftedX = (i+shift) % width;
				if(buffer.size() < (shift % width))
				{
					buffer.add(virtualScreen[shiftedX][y]);
					virtualScreen[shiftedX][y] = virtualScreen[i][y];
				} else
				{
					buffer.add(virtualScreen[shiftedX][y]);
					virtualScreen[shiftedX][y] = buffer.poll();
				}
			}
		} catch (Exception e)
		{
			log.error("Failed to handle instruction: {}", line);
			throw e;
		}
	}
	
	public void handleRect(String line, boolean[][] virtualScreen)
	{
		try
		{
			String dimensions = line.substring(5);
			String xStr = dimensions.substring(0, dimensions.indexOf('x'));
			String yStr = dimensions.substring(dimensions.indexOf('x') + 1);
			int width = Integer.parseInt(xStr);
			int height = Integer.parseInt(yStr);
			for(int i = 0; i < width; i++)
			{
				for(int j = 0; j < height; j++)
				{
					virtualScreen[i][j] = true;
				}
			}
		} catch (Exception e)
		{
			log.error("Failed to handle instruction: {}", line);
			throw e;
		}
	}
	
}
