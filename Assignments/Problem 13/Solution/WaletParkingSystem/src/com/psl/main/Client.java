package com.psl.main;

import java.util.Set;

import com.psl.bean.ParkingBlock;
import com.psl.exception.ParkingFullException;
import com.psl.util.ParkingSystemImpl;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParkingSystemImpl impl = new ParkingSystemImpl();
		Set<ParkingBlock> blocks = impl.populateData("walletParking.txt");
		for (ParkingBlock parkingBlock : blocks) {
			//System.out.println(parkingBlock);
		}
		System.out.println("///////////////////////////////////////////////////////////////////////////");
		//System.out.println("removed: " + impl.removeVehicle(blocks, 2023, 1620));
		System.out.println("--------------------------------------------------------------");
		for (ParkingBlock parkingBlock : impl.sortByParkingTime(blocks)) {
			//System.out.println(parkingBlock);
		}
		try {
			impl.parkVehicle(blocks, 8624, 1300);
			impl.parkVehicle(blocks, 7845, 1400);
			impl.parkVehicle(blocks, 3547, 1230);
			impl.parkVehicle(blocks, 1234, 1645);
		} catch (ParkingFullException e) {
			e.printStackTrace();
		}
		for (ParkingBlock parkingBlock : blocks) {
			System.out.println(parkingBlock);
		}
	}
}
