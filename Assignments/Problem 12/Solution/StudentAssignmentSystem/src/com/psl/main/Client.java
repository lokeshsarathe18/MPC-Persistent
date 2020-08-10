package com.psl.main;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.psl.bean.Student;
import com.psl.bean.Subject;
import com.psl.util.StudentAssignmentImpl;

public class Client {

	public static void main(String[] args) {
		StudentAssignmentImpl impl = new StudentAssignmentImpl();
		Map<Subject, List<Student>> map = impl.populateData("subject.txt",
				"student.txt");
		impl.calculateMarks(map);
		for (Entry<Subject, List<Student>> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "\n" + entry.getValue());
		}

		System.out.println("//////////////////////////////////////////////////////////////");
		for (Entry<Subject, List<Student>> entry : impl
				.generateDefaulterListSubjectWise(map).entrySet()) {
			//System.out.println(entry.getKey() + "\n" + entry.getValue());
		}
		
		System.out.println("//////////////////////////////////////////////////////////////");
		impl.offerGraseMarks(map);
		for (Entry<Subject, List<Student>> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "\n" + entry.getValue());
		}
	}
}