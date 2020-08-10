package com.psl.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Locale.Category;

import com.psl.bean.Channel;
import com.psl.bean.ChannelCategory;
import com.psl.util.SetTopBoxManagementSystemImpl;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SetTopBoxManagementSystemImpl impl = new SetTopBoxManagementSystemImpl();
		Set<Channel> channels = impl.populateByChannelCategory("Channel.ser");
		List<Channel> list = new ArrayList<Channel>();
		list.addAll(channels);
		impl.calculateBillForEachChannel(list, "Usage.ser");
		for (Channel channel : channels) {
			System.out.println(channel);
		}
		System.out.println(
				"----------------------------------------------------------------------------------hourOfUsage---------------------------------------------------------------------------------");
		list = impl.sortByHighestHourOfUsage(list, "Usage.ser");
		for (Channel channel : list) {
			System.out.println(channel);
		}
		System.out.println(
				"----------------------------------------------------------------------------------getByCategory---------------------------------------------------------------------------------");
		list = impl.getByCategory(list, ChannelCategory.Movie);
		for (Channel channel : list) {
			System.out.println(channel);
		}
	}
}
