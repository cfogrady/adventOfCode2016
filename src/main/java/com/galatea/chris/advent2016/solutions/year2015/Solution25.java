package com.galatea.chris.advent2016.solutions.year2015;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.galatea.chris.advent2016.solutions.Solution;

public class Solution25 implements Solution {
	private static final Logger log = LoggerFactory.getLogger(Solution25.class);

	@Override
	public void solveProblem(String[] args) throws Exception {
		ManualCodes code = new ManualCodes();
		while(code.getX() != 3019 || code.getY() != 3010)
		{
			code.moveToNextCode();
		}
		log.info("Code at {}, {} is {}", code.getX(), code.getY(), code.getValue());
		
	}
	
	public static class ManualCodes
	{
		private int x;
		private int y;
		private long value;
		
		public ManualCodes()
		{
			this.y = 1;
			this.x = 1;
			this.value = 20151125l;
		}
		
		public ManualCodes(int x, int y, long value)
		{
			this.x = x;
			this.y = y;
			this.value = value;
		}
		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		public long getValue() {
			return value;
		}
		public void setValue(long value) {
			this.value = value;
		}
		
		public void moveToNextCode()
		{
			if(y == 1)
			{
				y = x+1;
				x = 1;
			} else
			{
				x++;
				y--;
			}
			value = value * 252533l % 33554393l;
		}
	}
}
