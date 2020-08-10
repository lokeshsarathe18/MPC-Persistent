package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.bean.Item;
import com.exception.NoDataFoundException;

// Override and implement all the methods of DBConnectionUtil Interface in this class
public class InventoryServiceImpl implements InventoryService {

	@Override
	public List<Item> readAllItemsFromDb() {
		DatabaseConnectionManager db = new DatabaseConnectionManager();
		List<Item> items = new ArrayList<Item>();
		Item item = null;
		try (Connection conn = db.getConnection()) {
			PreparedStatement ps = conn.prepareStatement("select * from cheese_tbl");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				item = new Item();
				item.setId(rs.getInt(1));
				item.setDescription(rs.getString(2));
				item.setWeight(rs.getFloat(3));
				item.setPrice(rs.getFloat(4));
				item.setManufacturingDate(rs.getDate(5));
				item.setUseBeforeMonths(rs.getInt(6));
				item.setExpiryDate(null);
				items.add(item);
			}

			ps = conn.prepareStatement("select * from milk_tbl");
			rs = ps.executeQuery();
			while (rs.next()) {
				item = new Item();
				item.setId(rs.getInt(1));
				item.setDescription(rs.getString(2));
				item.setWeight(rs.getFloat(3));
				item.setPrice(rs.getFloat(4));
				item.setManufacturingDate(rs.getDate(5));
				item.setUseBeforeMonths(rs.getInt(6));
				item.setExpiryDate(null);
				items.add(item);
			}

			ps = conn.prepareStatement("select * from wheat_tbl");
			rs = ps.executeQuery();
			while (rs.next()) {
				item = new Item();
				item.setId(rs.getInt(1));
				item.setDescription(rs.getString(2));
				item.setWeight(rs.getFloat(3));
				item.setPrice(rs.getFloat(4));
				item.setManufacturingDate(rs.getDate(5));
				item.setUseBeforeMonths(rs.getInt(6));
				item.setExpiryDate(null);
				items.add(item);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			if (items.isEmpty()) {
				items = null;
			}
			return items;
		}
	}

	@Override
	public void calculateExpiryDate(List<Item> items) {
		Calendar calendar = Calendar.getInstance();
		for (Item item : items) {
			calendar.setTime(item.getManufacturingDate());
			calendar.add(Calendar.MONTH, item.getUseBeforeMonths());
			item.setExpiryDate(calendar.getTime());
		}
	}

	@Override
	public void removeExpiredItems(List<Item> items) {
		Iterator<Item> iterator = items.iterator();
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		// date = dateFormat.parse("2016-01-01");
		date = new Date();
		while (iterator.hasNext()) {
			Item item = (Item) iterator.next();
			if (date.compareTo(item.getExpiryDate()) > 0) {
				iterator.remove();
			}
		}
	}

	@Override
	public void sortItems(List<Item> items) {
		Collections.sort(items, new Comparator<Item>() {
			@Override
			public int compare(Item o1, Item o2) {
				return o2.getExpiryDate().compareTo(o1.getExpiryDate());
			}
		});
	}

	@Override
	public void applyDiscount(List<Item> items) {
		Calendar calendar = Calendar.getInstance();
		//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		// date = dateFormat.parse("2016-01-01");
		date = new Date();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 6);
		Date d = calendar.getTime();
		for (Item item : items) {
			if (item.getExpiryDate().compareTo(d) <= 0) {
				float price = item.getPrice() * 80 / 100;
				item.setPrice(price);
			}
		}
	}

	@Override
	public List<Item> searchItem(String ItemType, List<Item> list) throws NoDataFoundException {
		List<Item> items = new ArrayList<Item>();
		for (Item item : list) {
			if (item.getDescription().contains(ItemType)) {
				items.add(item);
			}
		}
		if (items.isEmpty()) {
			items = null;
			throw new NoDataFoundException();
		}
		return items;
	}
}
