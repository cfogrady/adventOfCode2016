package com.galatea.chris.advent2016.fetchers;

public interface Fetcher<R> {
	public R fetchData() throws Exception;
}
