package com.galatea.chris.advent2016.models;

import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Floor {
	private static final Logger log = LoggerFactory.getLogger(Floor.class);
	
	private int floorNumber;
	private TreeSet<String> microchips;
	private TreeSet<String> generators;
	
	public Floor(int floorNumber)
	{
		this.floorNumber = floorNumber;
		this.microchips = new TreeSet<>();
		this.generators = new TreeSet<>();
	}
	
	public int getFloorNumber() {
		return floorNumber;
	}
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}

	public TreeSet<String> getMicrochips() {
		return microchips;
	}

	public void setMicrochips(TreeSet<String> microchips) {
		this.microchips = microchips;
	}

	public TreeSet<String> getGenerators() {
		return generators;
	}

	public void setGenerators(TreeSet<String> generators) {
		this.generators = generators;
	}
	
	
}
