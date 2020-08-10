package com.psl.main;

import java.util.ArrayList;
import java.util.List;

import com.psl.bean.Dish;
import com.psl.bean.Location;
import com.psl.bean.Order;
import com.psl.util.DominozPizzaDeliveryImpl;

/*
 * @Author: Gandhali Inamdar
 */
public class Client {

	public static void main(String[] args) {
		DominozPizzaDeliveryImpl deliveryImpl = new DominozPizzaDeliveryImpl();
		List<Dish> dishs = new ArrayList<Dish>();
		List<Location> locations = new ArrayList<Location>();
		deliveryImpl.populateData("dish.txt", "location.txt", dishs, locations);
		deliveryImpl.calculateLocationForDistance(dishs, locations);
		System.out.println(dishs);
		System.out.println("------------------");
//		System.out.println(locations);
//		System.out.println("------------------");
		List<Order> orders = deliveryImpl.calculateOrder("order.txt", dishs, locations);
		System.out.println(orders.size());
		deliveryImpl.freeDelivery(orders, dishs, locations);
		System.out.println(orders);
	}

}
