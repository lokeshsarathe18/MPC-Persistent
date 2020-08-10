package com.psl.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.INPUT_STREAM;

import com.psl.bean.Destination;
import com.psl.bean.Ship;

public class ShipyardSystemImpl implements ShipyardSystem {

	@Override
	public List<Ship> populateData(String fileName) {
		List<Ship> ships = new ArrayList<Ship>();
		Ship ship = null;
		try (FileInputStream fileInputStream = new FileInputStream(fileName);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						fileInputStream);
				ObjectInputStream inputStream = new ObjectInputStream(
						bufferedInputStream);) {
			while ((ship = (Ship) inputStream.readObject()) != null) {
				ships.add(ship);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return ships;
	}

	@Override
	public void calculateEndDate(String locationFile, List<Ship> list) {
		Destination destination = null;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try (FileInputStream fileInputStream = new FileInputStream(locationFile);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						fileInputStream);
				ObjectInputStream inputStream = new ObjectInputStream(
						bufferedInputStream);) {
			while ((destination = (Destination) inputStream.readObject()) != null) {
				// System.out.println(destination);
				for (Ship ship : list) {
					if (ship.getDestinationName().equals(
							destination.getDestinationName())) {
						calendar.setTime(ship.getStartDate());
						calendar.add(Calendar.DAY_OF_MONTH,
								destination.getNumberOfDays());
						String s = sdf.format(calendar.getTime());
						Date d = sdf.parse(s);
						// System.out.println(s + "   "+ d);
						ship.setEndDate(d);
						ship.setNumberOfDays(destination.getNumberOfDays());
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	@Override
	public void sortByLongestJourney(List<Ship> list) {
		Collections.sort(list, new Comparator<Ship>() {
			@Override
			public int compare(Ship o1, Ship o2) {
				return o2.getNumberOfDays() - o1.getNumberOfDays();
			}
		});
	}

	@Override
	public void calculateCost(List<Ship> list) {
		int days = 0;
		double cost = 0;
		for (Ship ship : list) {
			days = ship.getNumberOfDays();
			if (days > 100) {
				cost = 100 * 100 + (days - 100) * 70;
			} else {
				cost = days * 100;
			}
			ship.setTotalCost(cost);
		}
	}
}
