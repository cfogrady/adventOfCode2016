package com.galatea.chris.advent2016.fetchers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class RoomsFromFileFetcher implements ListFetcher<RoomsFromFileFetcher.Room> {
	
	private Fetcher<Iterator<String>> fileLineReader;
	
	public RoomsFromFileFetcher(String fileName)
	{
		this.fileLineReader = new FileLineFetcher(fileName);
	}
	
	public List<RoomsFromFileFetcher.Room> fetchData() throws Exception
	{
		List<RoomsFromFileFetcher.Room> rooms = new LinkedList<RoomsFromFileFetcher.Room>();
		
		Iterator<String> iterator = this.fileLineReader.fetchData();
		while(iterator.hasNext())
		{
			String line = iterator.next();
			int startOfSector = line.lastIndexOf('-');
			int startOfCheck = line.lastIndexOf('[');
			String encryptedName = line.substring(0, startOfSector);
			String sector = line.substring(startOfSector+1, startOfCheck);
			String checkSum = line.substring(startOfCheck+1, line.length()-1);
//			System.out.println("EncryptedName: " + encryptedName);
//			System.out.println("Sector: " + sector);
//			System.out.println("CheckSum: " + checkSum);
			Room room = new Room();
			room.encyrptedName = encryptedName;
			room.checkSum = checkSum;
			room.sectorId = Integer.parseInt(sector);
			rooms.add(room);
		}
		return rooms;
	}
	
	public static class Room
	{
		public String encyrptedName;
		public Integer sectorId;
		public String checkSum;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((checkSum == null) ? 0 : checkSum.hashCode());
			result = prime * result + ((encyrptedName == null) ? 0 : encyrptedName.hashCode());
			result = prime * result + ((sectorId == null) ? 0 : sectorId.hashCode());
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
			Room other = (Room) obj;
			if (checkSum == null) {
				if (other.checkSum != null)
					return false;
			} else if (!checkSum.equals(other.checkSum))
				return false;
			if (encyrptedName == null) {
				if (other.encyrptedName != null)
					return false;
			} else if (!encyrptedName.equals(other.encyrptedName))
				return false;
			if (sectorId == null) {
				if (other.sectorId != null)
					return false;
			} else if (!sectorId.equals(other.sectorId))
				return false;
			return true;
		}
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("Room [encyrptedName=");
			builder.append(encyrptedName);
			builder.append(", sectorId=");
			builder.append(sectorId);
			builder.append(", checkSum=");
			builder.append(checkSum);
			builder.append("]");
			return builder.toString();
		}
		
		
	}

}
