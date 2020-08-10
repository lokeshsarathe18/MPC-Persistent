package com.psl;

import java.util.*;

import com.bean.Category;
import com.bean.Complexity;
import com.bean.Criteria;
import com.bean.Question;
import com.util.DataManagerImpl;

public class Client {
	public static void main(String[] args) {
		DataManagerImpl lokesh = new DataManagerImpl();
		List<Question> questions = lokesh.populateData();
		// 5 questions of Geography
		// System.out.println(lokesh.getQuestionByCategory(Category.Geography,
		// questions).size());
		// 8 questions of Geography
		// System.out.println(lokesh.getQuestionByComplexity(Complexity.Medium,
		// questions).size());

		List<Criteria> template = new ArrayList<Criteria>();
		template.add(new Criteria(Category.GK, Complexity.Simple, 2));
		template.add(new Criteria(Category.GK, Complexity.Medium, 1));
		template.add(new Criteria(Category.GK, Complexity.Complex, 1));
		template.add(new Criteria(Category.Science, Complexity.Complex, 1));
		template.add(new Criteria(Category.History, Complexity.Medium, 2));
		template.add(new Criteria(Category.History, Complexity.Simple, 2));
		template.add(new Criteria(Category.Geography, Complexity.Medium, 1));

		Set<Question> questionPaper = lokesh.generateQuestionPaper(questions, template);
		for (Question question : questionPaper) {
			System.out.println(question);
		}
		/*
		 * lokesh.sortByCategory(questions); System.out.println(questions);
		 * System.out.println("--------------------------------");
		 * lokesh.sortByComplexity(questions); System.out.println(questions);
		 */
	}
}
