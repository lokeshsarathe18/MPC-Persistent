package com.psl;

import java.util.List;

import com.psl.beans.Student;
import com.psl.exceptions.InsufficientDataException;
import com.psl.util.StudentDataManager;

public class Client {

	public static void main(String[] args) {
		StudentDataManager dataManager = new StudentDataManager();
		List<Student> students = dataManager.populateData("StudentDetails.txt");
		try {
			dataManager.validateData(students);
		} catch (InsufficientDataException e) {
			e.printStackTrace();
		}
		// dataManager.sortData(students);
		for (Student student : students) {
			System.out.println(student);
		}

	}
}
