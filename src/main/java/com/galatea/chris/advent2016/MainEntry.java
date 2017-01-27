package com.galatea.chris.advent2016;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.galatea.chris.advent2016.fetchers.CSVListFetcher;
import com.galatea.chris.advent2016.solutions.Solution;

public class MainEntry {

	private static final Logger log = LoggerFactory.getLogger(MainEntry.class);
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("Day9/applicationContext.xml");
		Solution solution = (Solution)context.getBean("solution");
		try {
			long start = System.currentTimeMillis();
			solution.solveProblem(args);
			long end = System.currentTimeMillis();
			long minutes = (end-start)/(1000*60);
			long seconds = ((end-start)-(minutes*1000*60))/1000;
			long milliseconds = (end-start) - (minutes*60*1000 + seconds*1000); 
			log.info("Took: {} minutes {} seconds and {} milliseconds", minutes, seconds, milliseconds);
		} catch (Exception e) {
			log.error("Failed to solve problem!", e);
		}
	}

}
