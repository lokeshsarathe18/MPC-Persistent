package com.psl.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.psl.bean.Dish;
import com.psl.bean.Location;
import com.psl.bean.Order;

public class DominozPizzaDeliveryImpl implements DominozPizzaDelivery {

	private void populateDish(String dishFile, List<Dish> dishs) {
		Dish dish = null;
		String str = null;
		String s[] = null;
		try (FileReader fileReader = new FileReader(dishFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			while ((str = bufferedReader.readLine()) != null) {
				s = str.split(",");
				dish = new Dish(Integer.parseInt(s[0].trim()), s[1],
						Double.parseDouble(s[2].trim()),
						Double.parseDouble(s[3].trim()));
				dishs.add(dish);
			}
		} catch (Exception e) {
			System.err.println("Error in populateDish: " + e);
		}
	}

	private void populateLocation(String locationFile, List<Location> locations) {
		Location l = null;
		String str = null;
		String s[] = null;
		try (FileReader fileReader = new FileReader(locationFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			while ((str = bufferedReader.readLine()) != null) {
				s = str.split(",");
				l = new Location(Integer.parseInt(s[0]),
						Integer.parseInt(s[1]), Double.parseDouble(s[2]));
				locations.add(l);
			}
		} catch (Exception e) {
			System.err.println("Error in populateLocation: " + e);
		}
	}

	@Override
	public void populateData(String dishFile, String locationFile,
			List<Dish> dishs, List<Location> locations) {
		populateDish(dishFile, dishs);
		populateLocation(locationFile, locations);
	}

	@Override
	public void calculateLocationForDistance(List<Dish> dishs,
			List<Location> locations) {
		for (Dish d : dishs) {
			for (Location location : locations) {
				double cookTime = d.getTimeToCook();
				double time = cookTime + location.getLocationTime();
				if (time <= 30.0) {
					List<Location> list = d.getList();
					if (list == null) {
						list = new ArrayList<Location>();
						d.setList(list);
					}
					list.add(location);
				}
			}
		}
	}

	private Dish getDishById(int id, List<Dish> dishs) {
		Dish result = null;
		for (Dish dish : dishs) {
			if (dish.getDishId() == id) {
				result = dish;
				break;
			}
		}
		return result;
	}

	private Location getLocationById(int id, List<Location> locations) {
		Location result = null;
		for (Location location : locations) {
			if (location.getLocationCode() == id) {
				result = location;
				break;
			}
		}
		return result;
	}

	@Override
	public List<Order> calculateOrder(String orderFile, List<Dish> dishs,
			List<Location> locations) {
		List<Order> orders = new ArrayList<Order>();
		Order order = null;
		String str = null;
		String s[] = null;
		try (FileReader fileReader = new FileReader(orderFile);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			while ((str = bufferedReader.readLine()) != null) {
				s = str.split(",");
				order = new Order(Integer.parseInt(s[0].trim()),
						Integer.parseInt(s[1].trim()));
				
				Dish dish = getDishById(order.getDishId(), dishs);
				Location location = getLocationById(order.getLocationCode(), locations);
				
				double totalTime = dish.getTimeToCook() + location.getLocationTime();
				if(totalTime <= 30.0){
					int locationDistance = location.getLocationDistance();
					double cost = dish.getCost();
					double totalCost = cost + locationDistance;
					order.setTotalCost(totalCost);
					orders.add(order);
				}
			}
		} catch (Exception e) {
			System.err.println("Error in calculateOrder: " + e);
		}
		return orders;
	}

	@Override
	public void freeDelivery(List<Order> orders, List<Dish> dishs,
			List<Location> locations) {
		int count = 0;
		for (Order order : orders) {
			Dish dish = getDishById(order.getDishId(), dishs);
			Location location = getLocationById(order.getLocationCode(), locations);
			if(location.getLocationDistance()<=10 && dish.getCost()>200){
				order.setDiscount(location.getLocationDistance());
				order.setTotalCost(dish.getCost());
				count++;
			}
		}
		//System.out.println("count= " + count);
	}
}
