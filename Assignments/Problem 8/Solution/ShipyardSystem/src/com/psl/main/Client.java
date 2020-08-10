package com.psl.main;

import java.util.List;

import com.psl.bean.Ship;
import com.psl.util.ShipyardSystemImpl;

public class Client {

	public static void main(String[] args) {
		ShipyardSystemImpl impl = new ShipyardSystemImpl();
		List<Ship> ships = impl.populateData("ship.ser");
		impl.calculateEndDate("destination.ser", ships);
		impl.sortByLongestJourney(ships);
		impl.calculateCost(ships);
		
		System.out.println(ships);
		
	}

}
