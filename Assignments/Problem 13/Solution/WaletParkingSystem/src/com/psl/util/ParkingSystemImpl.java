package com.psl.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Time;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.psl.bean.ParkingBlock;
import com.psl.exception.ParkingFullException;

public class ParkingSystemImpl implements ParkingSystem {

	@Override
	public Set<ParkingBlock> populateData(String fileName) {
		Set<ParkingBlock> blocks = new TreeSet<ParkingBlock>(new Comparator<ParkingBlock>() {
			@Override
			public int compare(ParkingBlock o1, ParkingBlock o2) {
				return o1.getBlockNumber() - o2.getBlockNumber();
			}
		});
		ParkingBlock block = null;
		try (FileReader fileReader = new FileReader(fileName); BufferedReader br = new BufferedReader(fileReader)) {
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] str = s.split(",");
				if (str.length == 3) {
					int blockNumber = Integer.parseInt(str[0].trim());
					int vehicleNumber = Integer.parseInt(str[1].trim());
					int parkTime = Integer.parseInt(str[2].trim());
					block = new ParkingBlock(blockNumber, vehicleNumber, parkTime, 0, 0.0);
				} else {
					block = new ParkingBlock(Integer.parseInt(str[0].trim()), 2000, 0, 0, 0.0);
				}
				blocks.add(block);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blocks;
	}

	@Override
	public ParkingBlock removeVehicle(Set<ParkingBlock> treeSet, int vehicleNumber, int outitme) {
		ParkingBlock block = null;
		for (ParkingBlock parkingBlock : treeSet) {
			if (parkingBlock.getVehicleNumber() == vehicleNumber) {
				parkingBlock.setOutTime(outitme);
				String in = "" + parkingBlock.getParkTime();
				String out = "" + outitme;

				long time = (Time.valueOf(out.substring(0, 2) + ":" + out.substring(2) + ":" + "00").getTime()
						- Time.valueOf(in.substring(0, 2) + ":" + in.substring(2) + ":" + "00").getTime())
						/ (1000 * 60);

				Double charge = time * 10 / 60.0;
				parkingBlock.setCharge(charge);
				block = new ParkingBlock(parkingBlock.getBlockNumber(), parkingBlock.getVehicleNumber(),
						parkingBlock.getParkTime(), parkingBlock.getOutTime(), parkingBlock.getCharge());

				parkingBlock.setVehicleNumber(2000);
				parkingBlock.setOutTime(0);
				parkingBlock.setParkTime(0);
				parkingBlock.setCharge(0);

			}
		}
		return block;
	}

	@Override
	public Set<ParkingBlock> sortByParkingTime(Set<ParkingBlock> treeSet) {
		Set<ParkingBlock> blocks = new TreeSet<ParkingBlock>(new Comparator<ParkingBlock>() {
			@Override
			public int compare(ParkingBlock o1, ParkingBlock o2) {
				int result = o1.getParkTime() - o2.getParkTime();
				if (result == 0)
					result = o1.getBlockNumber() - o2.getBlockNumber();
				return result;
			}
		});
		blocks.addAll(treeSet);
		return blocks;
	}

	@Override
	public void parkVehicle(Set<ParkingBlock> set, int vehicleNumber, int parkTime) throws ParkingFullException {
		boolean flag = true;
		for (ParkingBlock parkingBlock : set) {
			if (parkingBlock.getVehicleNumber() == 2000 && parkingBlock.getParkTime() == 0) {
				flag = false;
				parkingBlock.setVehicleNumber(vehicleNumber);
				parkingBlock.setParkTime(parkTime);
				System.out.println("Vehicle parked:" + vehicleNumber);
				return;
			}
		}
		if (flag) {
			throw new ParkingFullException();
		}
	}
}
