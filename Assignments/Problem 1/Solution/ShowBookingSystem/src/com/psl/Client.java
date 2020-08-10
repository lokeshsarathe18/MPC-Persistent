package com.psl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bean.Show;
import com.exception.InvalidSeatNumberException;
import com.exception.SeatsNotAvailableException;
import com.exception.UnknownShowException;
import com.util.DataManagerImpl;

//import com.bean.Show;

public class Client {

	public static void main(String[] args) throws IOException,
			ClassNotFoundException {
		DataManagerImpl impl = new DataManagerImpl();
		List<Show> shows = impl.populateDataFromFile("ShowDetails.ser");
		for (Show s : shows) {
			System.out.println(s);
		}
		try {
			impl.bookShow(shows, "Sahi re Sahi", "6:30 PM", 10);
		} catch (SeatsNotAvailableException | UnknownShowException
				| InvalidSeatNumberException e) {
			e.printStackTrace();
		}
		for (Show s : shows) {
			System.out.println(s);
		}
	}
}