package com.psl.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.psl.bean.Student;
import com.psl.bean.Subject;

public class StudentAssignmentImpl implements StudentAssignment {

	@Override
	public Map<Subject, List<Student>> populateData(String fileSubject,
			String fileStudent) {
		Map<Subject, List<Student>> map = new HashMap<Subject, List<Student>>();
		List<Student> students = null;
		Subject subject = null;
		Student student = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy");
		String s = null;

		try (FileReader fileReader = new FileReader(fileSubject);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			while ((s = bufferedReader.readLine()) != null) {
				String[] str = s.split(",");
				if (str.length == 2) {
					subject = new Subject(str[0].trim(), sdf.parse(str[1]
							.trim()), 0);
					map.put(subject, new ArrayList<Student>());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try (FileReader fileReader = new FileReader(fileStudent);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			while ((s = bufferedReader.readLine()) != null) {
				String[] str = s.split(",");
				if (str.length == 4) {
					student = new Student(Integer.parseInt(str[0].trim()),
							str[1], str[2], sdf.parse(str[3]), 0, 0, false);
					for (Entry<Subject, List<Student>> entry : map.entrySet()) {
						String sub = entry.getKey().getSubjectName();
						if (sub.equals(str[2])) {
							entry.getValue().add(student);
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
	public void calculateMarks(Map<Subject, List<Student>> map) {
		for (Entry<Subject, List<Student>> entry : map.entrySet()) {
			entry.getKey().setNumberOfSubmissions(entry.getValue().size());
			for (Student s : entry.getValue()) {
				int days = (int) (s.getSubmissionDate().getTime() - entry
						.getKey().getAssignmentIssueDate().getTime())
						/ (1000 * 60 * 60 * 24);
				s.setNumberOfDays(days);
				if (days < 6) {
					s.setMarksObtained(45);
				} else if (days < 11) {
					s.setMarksObtained(35);
				} else {
					s.setDefaulter(true);
					s.setMarksObtained(0);
				}
			}
		}
	}

	@Override
	public Map<Subject, List<Student>> generateDefaulterListSubjectWise(
			Map<Subject, List<Student>> map) {
		Map<Subject, List<Student>> result = new HashMap<Subject, List<Student>>();
		for (Entry<Subject, List<Student>> entry : map.entrySet()) {
			List<Student> list = new ArrayList<Student>();
			for (Student s : entry.getValue()) {
				if (s.isDefaulter())
					list.add(s);
			}
			result.put(entry.getKey(), list);
		}
		return result;
	}

	@Override
	public void offerGraseMarks(Map<Subject, List<Student>> map) {
		for (Entry<Subject, List<Student>> entry : map.entrySet()) {
			int minDays = entry.getValue().stream()
					.sorted(new Comparator<Student>() {
						@Override
						public int compare(Student o1, Student o2) {
							return o1.getSubmissionDate().compareTo(
									o2.getSubmissionDate());
						}
					}).collect(Collectors.toList()).get(0).getNumberOfDays();
			for (Student s : entry.getValue()) {
				if (s.getNumberOfDays() == minDays)
					s.setMarksObtained(s.getMarksObtained() + 3);
			}
		}
	}

}
