package com.galatea.chris.advent2016.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pair {
	private static final Logger log = LoggerFactory.getLogger(Pair.class);
		public Integer x;
		public Integer y;
		public Pair()
		{
			
		}
		public Pair(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		public Pair copyInstance()
		{
			Pair copy = new Pair();
			copy.x = x;
			copy.y = y;
			return copy;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Pair [x=");
			builder.append(x);
			builder.append(", y=");
			builder.append(y);
			builder.append("]");
			return builder.toString();
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((x == null) ? 0 : x.hashCode());
			result = prime * result + ((y == null) ? 0 : y.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			if (x == null) {
				if (other.x != null)
					return false;
			} else if (!x.equals(other.x))
				return false;
			if (y == null) {
				if (other.y != null)
					return false;
			} else if (!y.equals(other.y))
				return false;
			return true;
		}
}
