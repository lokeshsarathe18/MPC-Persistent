package com.psl.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.psl.bean.Channel;
import com.psl.bean.ChannelCategory;
import com.psl.bean.Usage;

public class SetTopBoxManagementSystemImpl implements SetTopBoxManagementSysytem {

	@Override
	public Set<Channel> populateByChannelCategory(String fileNameChannel) {
		Set<Channel> channels = new TreeSet<Channel>(new Comparator<Channel>() {
			@Override
			public int compare(Channel o1, Channel o2) {
				return o1.getCategory().compareTo(o2.getCategory());
			}
		});
		Channel channel = null;
		try (FileInputStream fs = new FileInputStream(fileNameChannel);
				BufferedInputStream bs = new BufferedInputStream(fs);
				ObjectInputStream inputStream = new ObjectInputStream(bs)) {
			while ((channel = (Channel) inputStream.readObject()) != null) {
				channels.add(channel);
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return channels;
	}

	@Override
	public void calculateBillForEachChannel(List<Channel> list, String fileNameUsage) {
		Usage usage = null;
		try (FileInputStream fs = new FileInputStream(fileNameUsage);
				BufferedInputStream bs = new BufferedInputStream(fs);
				ObjectInputStream inputStream = new ObjectInputStream(bs)) {
			while ((usage = (Usage) inputStream.readObject()) != null) {
				for (Channel channel : list) {
					if (channel.getChannelId().equals(usage.getChannelId())) {
						double billAmount = channel.getBillAmount();
						double hourOfUsage = usage.getEndTime() - usage.getStartTime();
						billAmount += channel.getCost() * hourOfUsage;
						channel.setBillAmount(billAmount);
						channel.setHourOfUsage(hourOfUsage + channel.getHourOfUsage());
					}
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	@Override
	public List<Channel> sortByHighestHourOfUsage(List<Channel> list, String fileNameUsage) {
		List<Channel> channels = new ArrayList<Channel>(list);
		channels.sort(new Comparator<Channel>() {
			@Override
			public int compare(Channel o1, Channel o2) {
				return (int)(o1.getHourOfUsage()-o2.getHourOfUsage());
			}
		});
		return channels;
	}

	@Override
	public List<Channel> getByCategory(List<Channel> list, ChannelCategory category) {
		List<Channel> channels = new ArrayList<Channel>();
		for (Channel channel : list) {
			if(channel.getCategory().equals(category)) {
				channels.add(channel);
			}
		}
		return channels;
	}
}