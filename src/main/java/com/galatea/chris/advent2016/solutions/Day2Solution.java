package com.galatea.chris.advent2016.solutions;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.fetchers.Fetcher;
import com.galatea.chris.advent2016.fetchers.ListFetcher;
import com.galatea.chris.advent2016.util.Pair;

public class Day2Solution implements Solution {
	private static final Logger log = LoggerFactory
			.getLogger(Day2Solution.class);

	private ListFetcher<List<Character>> characterListFetcher;
	private Fetcher<Character[][]> gridFetcher;
	
	public Day2Solution(ListFetcher<List<Character>> characterListFetcher)
	{
		this(characterListFetcher, null);
	}
	
	public Day2Solution(ListFetcher<List<Character>> characterListFetcher, Fetcher<Character[][]> gridFetcher)
	{
		this.characterListFetcher = characterListFetcher;
		this.gridFetcher = gridFetcher;
	}
		
	@Override
	public void solveProblem(String[] args) throws Exception {
		StringBuilder code = new StringBuilder();
		List<List<Character>> characterList = this.characterListFetcher.fetchData();
		Character[][] codeGrid = gridFetcher == null ? createCodeGrid() : gridFetcher.fetchData();
		log.info("CodeGrid: {}", codeGrid);
		Pair currentLocation = findFiveOnGrid(codeGrid);
		log.info("Starting at {} at {}, {}", codeGrid[currentLocation.x][currentLocation.y], currentLocation.x, currentLocation.y);
		for(List<Character> digitList : characterList)
		{
			for(Character movement : digitList)
			{
				try
				{
					if(movement == 'U'&& newLocationOnGrid(currentLocation.x, currentLocation.y-1, codeGrid)) currentLocation.y--;
					if(movement == 'D' && newLocationOnGrid(currentLocation.x, currentLocation.y+1, codeGrid)) currentLocation.y++;
					if(movement == 'R' && newLocationOnGrid(currentLocation.x+1, currentLocation.y, codeGrid)) currentLocation.x++;
					if(movement == 'L' && newLocationOnGrid(currentLocation.x-1, currentLocation.y, codeGrid)) currentLocation.x--;
				} catch (ArrayIndexOutOfBoundsException aioobe)
				{
					log.info("Failed to move from {},{} in direction {}", currentLocation.x, currentLocation.y, movement);
					throw aioobe;
				}
			}
			log.info("Location after line: {}", codeGrid[currentLocation.x][currentLocation.y]);
			code.append(codeGrid[currentLocation.x][currentLocation.y]);
		}
		log.info("Code is {}", code.toString());
	}
	
	public Pair findFiveOnGrid(Character[][] codeGrid) throws Exception
	{
		for(int x = 0; x < codeGrid.length; x++)
		{
			for(int y = 0; y < codeGrid.length; y++)
			{
				if(codeGrid[x][y] == null) continue;
				if(codeGrid[x][y] == '5')
				{
					Pair location = new Pair(x, y);
					return location;
				}
			}
		}
		log.error("Count not find a '5' in grid: {}", codeGrid);
		throw new Exception("Could not find a '5' in grid!");
	}
	
	public boolean newLocationOnGrid(int x, int y, Character[][] grid)
	{
		if(y < 0) return false;
		if(x < 0) return false;
		if(x >= grid.length) return false;
		if(y >= grid[x].length) return false;
		if(grid[x][y] == null) return false;
		return true;
	}
	
	public Character[][] createCodeGrid()
	{
		Character[][] codeGrid = new Character[3][3];
		for(int i = 1; i <= 9; i++)
		{
			codeGrid[(i-1)%3][(i-1)/3] = Character.valueOf((char)(i + 48));
		}
		return codeGrid;
	}
	
	public Character[][] createCodeGrid2()
	{
		Character[][] codeGrid = new Character[5][5];
		for(int i = 1; i <= 9; i++)
		{
			codeGrid[(i-1)%3][(i-1)/3] = Character.valueOf((char)(i + 48));
		}
		return codeGrid;
	}
}
