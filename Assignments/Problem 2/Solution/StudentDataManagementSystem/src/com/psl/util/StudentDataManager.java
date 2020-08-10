package com.psl.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.psl.beans.Address;
import com.psl.beans.Student;
import com.psl.exceptions.InsufficientDataException;

//Override all the methods of the DataManager Interface
public class StudentDataManager implements DataManager {

	@Override
	public List<Student> populateData(String fileName) {
		List<Student> students = new ArrayList<Student>();
		String str = null;
		Student student = null;
		try (FileReader fileReader = new FileReader(fileName);
				BufferedReader bufferedReader = new BufferedReader(fileReader)) {
			while ((str = bufferedReader.readLine()) != null) {
				String[] s = str.split(",");
				if (s.length == 6) {
					int rollno, age;
					String studentName, streetName, city, zipCode;
					try {
						rollno = Integer.parseInt(s[0]);
					} catch (Exception e) {
						rollno = 0;
					}
					if (s[1].isEmpty()) {
						studentName = null;
					} else {
						studentName = s[1];
					}
					try {
						age = Integer.parseInt(s[2]);
					} catch (Exception e) {
						age = 0;
					}
					if (s[3].isEmpty()) {
						streetName = null;
					} else {
						streetName = s[3];
					}
					if (s[3].isEmpty()) {
						city = null;
					} else {
						city = s[4];
					}
					if (s[3].isEmpty()) {
						zipCode = null;
					} else {
						zipCode = s[5];
					}

					student = new Student(rollno, studentName, age,
							new Address(streetName, city, zipCode));
					students.add(student);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return students;
	}

	@Override
	public void validateData(List<Student> studentList)
			throws InsufficientDataException {
		Iterator<Student> iterator = studentList.iterator();
		while (iterator.hasNext()) {
			Student student = (Student) iterator.next();
			Address address = student.getAddress();
			if (student.getRollno() == 0 || student.getAge() == 0
					|| student.getStudentName() == null
					|| address.getCity() == null
					|| address.getStreetName() == null
					|| address.getZipCode() == null)
				iterator.remove();
		}
	}

	@Override
	public void sortData(List<Student> studentList) {
		Collections.sort(studentList, new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				int r = o1.getStudentName().compareTo(o2.getStudentName());
				if (r == 0) {
					r = o1.getAge() - o2.getAge();
				}
				if (r == 0) {
					r = o1.getRollno() - o2.getRollno();
				}
				return r;
			}
		});
	}
}
