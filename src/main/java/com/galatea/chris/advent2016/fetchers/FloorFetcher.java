package com.galatea.chris.advent2016.fetchers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.models.Floor;

public class FloorFetcher implements ListFetcher<Floor> {
	private static final Logger log = LoggerFactory.getLogger(FloorFetcher.class);

	private Fetcher<Iterator<String>> fileLineFetcher;
	
	public FloorFetcher(Fetcher<Iterator<String>> fileLineFetcher)
	{
		this.fileLineFetcher = fileLineFetcher;
	}
	
	@Override
	public List<Floor> fetchData() throws Exception {
		List<Floor> floors = new ArrayList<Floor>(); 
		Iterator<String> iter = this.fileLineFetcher.fetchData();
		while(iter.hasNext())
		{
			String line = iter.next();
			int floorId = getFloorLevel(line);
			if(floorId == 0) continue;
			Floor floor = new Floor(floorId);
			//floor.getGenerators().add(e)
		}
		return floors;
	}
	
	private List<String> getMicrochips(String line)
	{
		List<String> microchips = new LinkedList<String>();
		int endIndex = line.indexOf("-");
		int startIndex = line.substring(0, endIndex).lastIndexOf(" ")+1;
		String microchip = new String(line.substring(startIndex, endIndex));
		return microchips;
	}
	
	private int getFloorLevel(String line)
	{
		int startIndex = line.indexOf(" ")+1;
		int endIndex = line.indexOf(" ", startIndex);
		int floorNumber = 0;
		String floorNumberStr = line.substring(startIndex, endIndex);
		if("first".equals(floorNumberStr))
		{
			floorNumber = 1;
		} else if("second".equals(floorNumberStr))
		{
			floorNumber = 2;
		} else if("third".equals(floorNumberStr))
		{
			floorNumber = 3;
		} else if("fourth".equals(floorNumberStr))
		{
			floorNumber = 4;
		} else if("fifth".equals(floorNumberStr))
		{
			floorNumber = 5;
		} else
		{
			log.warn("Failed to get floor number from line: {}", line);
		}
		return floorNumber;
	}
}
