package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.Map.Entry;

import com.exception.CityNotFoundException;
import com.exception.InvalidStateException;

// Override and implement the methods of Interface DataManager here 
public class DataManagerImpl implements DataManager {

	@Override
	public Map<String, List<String>> populateCityDataMap(String fileName) throws FileNotFoundException {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		String s = null;
		try (FileReader fileReader = new FileReader(fileName); BufferedReader br = new BufferedReader(fileReader);) {
			while ((s = br.readLine()) != null) {
				String[] str = s.split("-");
				if (str.length == 2) {
					if (map.get(str[0]) != null) {
						List<String> l = map.get(str[0]);
						l.add(str[1]);
					} else {
						List<String> l = new ArrayList<String>();
						l.add(str[1]);
						map.put(str[0], l);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<String> getCities(Map<String, List<String>> stateCityMap, String state) throws InvalidStateException {
		List<String> cities = stateCityMap.get(state);
		if(cities == null) {
			throw new InvalidStateException();
		}
		return cities;
	}

	@Override
	public String getState(Map<String, List<String>> stateCityMap, String city) throws CityNotFoundException {
		String state = null;
		for (Entry<String, List<String>> entry : stateCityMap.entrySet()) {
			List<String> cities = entry.getValue();
			for (String string : cities) {
				if(string.equals(city)) {
					state = entry.getKey();
					break;
				}
			}
		}
		if(state  == null)
			throw new CityNotFoundException();
		return state;
	}
}
