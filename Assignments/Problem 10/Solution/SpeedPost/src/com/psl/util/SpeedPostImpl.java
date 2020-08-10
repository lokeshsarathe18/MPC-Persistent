package com.psl.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.psl.bean.HeadOffice;
import com.psl.bean.Post;

public class SpeedPostImpl implements SpeedPost {

	@Override
	public Map<HeadOffice, List<Post>> populateData(String HeadOfficeFileName,
			String postFileName) {
		Map<HeadOffice, List<Post>> map = new HashMap<HeadOffice, List<Post>>();
		HeadOffice office = null;
		List<Post> posts = null;
		String s = null;
		try (FileReader fileReader = new FileReader(HeadOfficeFileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			while ((s = bufferedReader.readLine()) != null) {
				String[] str = s.split(",");
				if (str.length == 3) {
					str[2] = str[2].substring(1, str[2].length() - 1);
					String[] codeString = str[2].split("-");
					List<String> codes = new ArrayList<String>();
					for (String string : codeString) {
						codes.add(string);
					}
					office = new HeadOffice(str[0], str[1], codes);
					posts = new ArrayList<Post>();
					map.put(office, posts);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Post post = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try (FileReader fileReader = new FileReader(postFileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			while ((s = bufferedReader.readLine()) != null) {
				String[] str = s.split(",");
				if (str.length == 4) {
					post = new Post(str[0], sdf.parse(str[1]), str[2], str[3]);
					for (Entry<HeadOffice, List<Post>> entry : map.entrySet()) {
						for (String p : entry.getKey().getListOfPinCodes()) {
							if (post.getPinCode().equals(p)) {
								entry.getValue().add(post);
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	public List<Post> mostOldestPost(Map<HeadOffice, List<Post>> map) {
		List<Post> posts = new ArrayList<Post>();
		for (Entry<HeadOffice, List<Post>> entry : map.entrySet()) {
			posts.addAll(entry.getValue());
		}
		posts = posts.stream().sorted(new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return o1.getPostDate().compareTo(o2.getPostDate());
			}
		}).limit(5).collect(Collectors.toList());
		return posts;
	}

	@Override
	public HeadOffice trackPost(Map<HeadOffice, List<Post>> map, String postId) {
		HeadOffice headOffice = null;
		for (Entry<HeadOffice, List<Post>> entry : map.entrySet()) {
			for (Post p : entry.getValue()) {
				if(p.getPostId().equals(postId)){
					headOffice = entry.getKey();
				}
			}
		}
		return headOffice;
	}

	@Override
	public List<Post> sortByAge(Map<HeadOffice, List<Post>> map) {
		List<Post> posts = new ArrayList<Post>();
		for (Entry<HeadOffice, List<Post>> entry : map.entrySet()) {
			posts.addAll(entry.getValue());
		}
		posts = posts.stream().sorted(new Comparator<Post>() {
			@Override
			public int compare(Post o1, Post o2) {
				return o1.getPostDate().compareTo(o2.getPostDate());
			}
		}).collect(Collectors.toList());
		return posts;
	}
}
