package com.galatea.chris.advent2016.solutions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.Direction;
import com.galatea.chris.advent2016.fetchers.CSVListFetcher;
import com.galatea.chris.advent2016.fetchers.ListFetcher;
import com.galatea.chris.advent2016.util.Pair;

public class Day1Problem1 implements Solution {

	private static final Logger log = LoggerFactory.getLogger(Day1Problem1.class);
	
	private ListFetcher<String> listFetcher;
	private boolean checkFirstDoubleVisit;
	
	public Day1Problem1(ListFetcher<String> listFetcher, boolean checkFirstDoubleVisit)
	{
		this.listFetcher = listFetcher;
		this.checkFirstDoubleVisit = checkFirstDoubleVisit;
	}
	
	@Override
	public void solveProblem(String[] args) throws Exception {
		List<String> inputValues = listFetcher.fetchData();
		Pair pair = walk(inputValues);
		log.info("Finished problem with {} steps North and {} steps East", pair.y, pair.x);
	}
	
	public Pair walk(List<String> inputValues) throws Exception
	{
		Set<Pair> visitedLocations = new HashSet<Pair>();
		
		log.info("Input Set: {}", inputValues);
		Direction direction = Direction.NORTH;
		int northSouth = 0;
		int eastWest = 0;
		for(String turn : inputValues)
		{
			direction = getNewDirection(direction, turn);
			int steps = getStepsAfterTurn(turn);
			Pair pair = new Pair();
			pair.x = eastWest;
			pair.y = northSouth;
			switch(direction)
			{
				case NORTH:
					if(checkFirstDoubleVisit)
					{
						for(int i = 1; i <= steps; i++)
						{
							pair = pair.copyInstance();
							pair.y++;
							if(visitedLocations.contains(pair)) return pair;
							visitedLocations.add(pair);
						}
					}
					northSouth += steps;
					break;
				case SOUTH:
					if(checkFirstDoubleVisit)
					{
						for(int i = 1; i <= steps; i++)
						{
							pair = pair.copyInstance();
							pair.y--;
							if(visitedLocations.contains(pair)) return pair;
							visitedLocations.add(pair);
						}
					}
					northSouth -= steps;
					break;
				case EAST:
					if(checkFirstDoubleVisit)
					{
						for(int i = 1; i <= steps; i++)
						{
							pair = pair.copyInstance();
							pair.x++;
							if(visitedLocations.contains(pair)) return pair;
							visitedLocations.add(pair);
						}
					}
					eastWest += steps;
					break;
				case WEST:
					if(checkFirstDoubleVisit)
					{
						for(int i = 1; i <= steps; i++)
						{
							pair = pair.copyInstance();
							pair.x--;
							if(visitedLocations.contains(pair)) return pair;
							visitedLocations.add(pair);
						}
					}
					eastWest -= steps;
					break;
			}
		}
		Pair endLocation = new Pair();
		endLocation.x = eastWest;
		endLocation.y = northSouth;
		return endLocation;
	}
	
	public Direction getNewDirection(Direction currentDirection, String turn) throws Exception
	{
		switch(currentDirection)
		{
			case NORTH:
				if(turn.startsWith("R")) return Direction.EAST;
				if(turn.startsWith("L")) return Direction.WEST;
				break;
			case SOUTH:
				if(turn.startsWith("L")) return Direction.EAST;
				if(turn.startsWith("R")) return Direction.WEST;
				break;
			case EAST:
				if(turn.startsWith("L")) return Direction.NORTH;
				if(turn.startsWith("R")) return Direction.SOUTH;
				break;
			case WEST:
				if(turn.startsWith("R")) return Direction.NORTH;
				if(turn.startsWith("L")) return Direction.SOUTH;
				break;
		}
		log.error("Failed to get a new direction on turn {}", turn);
		throw new Exception("Failed to get a new direction on turn");
	}
	
	public int getStepsAfterTurn(String turn)
	{
		String stepsStr = turn.substring(1);
		int steps = Integer.parseInt(stepsStr);
		return steps;
	}
}
