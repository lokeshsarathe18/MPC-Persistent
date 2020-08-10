package com.psl;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.exception.CityNotFoundException;
import com.exception.InvalidStateException;
import com.util.DataManagerImpl;


public class Client {
	
	public static void main(String[] args) throws InvalidStateException, FileNotFoundException, CityNotFoundException {
		//Call your methods from here  to test the code implemented
		DataManagerImpl impl = new DataManagerImpl();
		Map<String, List<String>> stateCityMap  = impl.populateCityDataMap("StateCityDetails.txt");
		for (Entry<String, List<String>> entry : stateCityMap.entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		System.out.println("----------------------------------------------------");
		for (String string : impl.getCities(stateCityMap, "Madhya Pradesh")) {
			System.out.println(string);
		}
		System.out.println("----------------------------------------------------");
		System.out.println(impl.getState(stateCityMap, "Nagpur"));
	}
}