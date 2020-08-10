package com.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.bean.Category;
import com.bean.Complexity;
import com.bean.Criteria;
import com.bean.Question;

// Override and implement all the methods of DataManager Interface here
public class DataManagerImpl implements DataManager {

	@Override
	public List<Question> populateData() {
		List<Question> questions = new ArrayList<Question>();
		Question question = null;
		try {
			Connection conn = new DatabaseConnectionManager().getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from questionBank");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				question = new Question();
				question.setSrno(rs.getInt(1));
				question.setQuestion(rs.getString(2));
				question.setOption1(rs.getString(3));
				question.setOption2(rs.getString(4));
				question.setOption3(rs.getString(5));
				question.setOption4(rs.getString(6));
				question.setCorrectAns(rs.getString(7));
				question.setCategory(Category.valueOf(rs.getString(8)));
				question.setComplexity(Complexity.valueOf(rs.getString(9)));
				questions.add(question);
			}
		} catch (Exception e) {
			System.out.println("Exception in populateData: " + e);
		} finally {
			if (questions.isEmpty())
				questions = null;
			return questions;
		}
	}

	@Override
	public List<Question> getQuestionByCategory(Category category, List<Question> questionsList) {
		List<Question> questions = new ArrayList<Question>();
		questions = questionsList.stream().filter(o -> o.getCategory().equals(category)).collect(Collectors.toList());
		return questions;
	}

	@Override
	public List<Question> getQuestionByComplexity(Complexity complexity, List<Question> questionsList) {
		List<Question> questions = new ArrayList<Question>();
		questions = questionsList.stream().filter(o -> o.getComplexity().equals(complexity))
				.collect(Collectors.toList());
		return questions;
	}

	@Override
	public Set<Question> generateQuestionPaper(List<Question> list, List<Criteria> template) {
		Set<Question> questionPaper = new HashSet<Question>();
		Collections.shuffle(template);
		for (Criteria c : template) {
			List<Question> question = list.stream()
					.filter(o -> o.getComplexity().equals(c.getComplexity()) && o.getCategory().equals(c.getCategory()))
					.collect(Collectors.toList());
			// System.out.println(question);
			// System.out.println("------------------------------");
			int count = 0;
			while (count < c.getNoOfQuestion()) {
				int i = (int) (Math.random() * question.size());
				if (questionPaper.add(question.get(i))) {
					count++;
					// System.out.println("count = " + i);
				}
			}
		}
		return questionPaper;
	}

	@Override
	public void sortByCategory(List<Question> questionList) {
		Collections.sort(questionList, new Comparator<Question>() {
			@Override
			public int compare(Question q1, Question q2) {
				return q1.getCategory().compareTo(q2.getCategory());
			}
		});
	}

	@Override
	public void sortByComplexity(List<Question> questionList) {
		Collections.sort(questionList, new Comparator<Question>() {
			@Override
			public int compare(Question q1, Question q2) {
				return q1.getComplexity().compareTo(q2.getComplexity());
			}
		});
	}
}