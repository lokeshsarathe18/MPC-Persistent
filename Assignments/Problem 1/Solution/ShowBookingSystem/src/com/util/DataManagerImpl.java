package com.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import com.bean.Show;
import com.exception.InvalidSeatNumberException;
import com.exception.SeatsNotAvailableException;
import com.exception.UnknownShowException;

//Override and implement all the methods of DataManger Interface in this class
public class DataManagerImpl implements DataManager {

	@Override
	public List<Show> populateDataFromFile(String fileName) {
		List<Show> shows = new ArrayList<Show>();
		try (FileInputStream fileInputStream = new FileInputStream(fileName);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(
						fileInputStream);
				ObjectInputStream inputStream = new ObjectInputStream(
						bufferedInputStream);) {
			Show show = null;
			while ((show = (Show) inputStream.readObject()) != null) {
				shows.add(show);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return shows;
	}

	@Override
	public void bookShow(List<Show> showList, String showName,
			String show_time, int noOfSeats) throws SeatsNotAvailableException,
			UnknownShowException, InvalidSeatNumberException {
		Boolean flag = false;
		if (noOfSeats < 1) {
			throw new InvalidSeatNumberException();
		}
		for (Show show : showList) {
			if (show.getShowName().equals(showName)) {
				if (!show.getShowTime().equals(show_time)) {
					System.out
							.println("This time is not available for the show");
				}
				int seatsAvailable = show.getSeatsAvailable();
				if (seatsAvailable > noOfSeats) {
					show.setSeatsAvailable(seatsAvailable - noOfSeats);
					flag = true;
					System.out.println("Show Booked successfully ....");
					return;
				} else {
					throw new SeatsNotAvailableException();
				}
			}
		}
		if (flag == false)
			throw new UnknownShowException();
	}
}
