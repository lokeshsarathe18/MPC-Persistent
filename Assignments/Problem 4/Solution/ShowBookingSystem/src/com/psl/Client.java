package com.psl;

import java.util.ArrayList;
import java.util.List;

import com.bean.Item;
import com.exception.NoDataFoundException;
import com.util.InventoryServiceImpl;

public class Client {

	/**
	 * @param args
	 */

	public static void main(String[] args) {
		InventoryServiceImpl impl = new InventoryServiceImpl();
		List<Item> items = impl.readAllItemsFromDb();

		impl.calculateExpiryDate(items);
		for (Item item : items) {
			System.out.println(item);
		}
		System.out.println("///////////////////////////////////////////////////////////");
		//impl.removeExpiredItems(items);
		impl.sortItems(items);
		impl.applyDiscount(items);
		for (Item item : items) {
			System.out.println(item);
		}
		System.out.println("--------------------------------------------------------------------------------------------------------------");
		try {
			System.out.println(impl.searchItem("milk", impl.readAllItemsFromDb()));
		} catch (NoDataFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
