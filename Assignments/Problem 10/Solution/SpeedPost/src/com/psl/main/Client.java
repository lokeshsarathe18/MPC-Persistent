package com.psl.main;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.psl.bean.HeadOffice;
import com.psl.bean.Post;
import com.psl.util.SpeedPostImpl;


public class Client {
	
	public static void main(String[] args) {
		SpeedPostImpl impl = new SpeedPostImpl();
		Map<HeadOffice, List<Post>> map =impl.populateData("HeadOffice.txt", "Post.txt");
		for (Entry<HeadOffice, List<Post>> entry : map.entrySet()) {
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
//			System.out.println("***************************************");
		}
		List<Post> posts = impl.mostOldestPost(map);
		for (Post post : posts) {
			//System.out.println(post);
		}
		System.out.println(impl.trackPost(map, "P115"));
		posts = impl.sortByAge(map);
		for (Post post : posts) {
			System.out.println(post);
		}
	}
}
