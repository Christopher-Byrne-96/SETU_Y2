package Lab6aq1;

public class Student extends Person {
	
//-----------------------------------------------------------------------------------------------------------------------
//|||||| VARIABLES    |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
//-----------------------------------------------------------------------------------------------------------------------
	
private int numCourses = 0;
private String[] courses = new String[10];
private int[] grades = new int[10];


//-----------------------------------------------------------------------------------------------------------------------
//|||||| CONSTRUCTERS |||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
//-----------------------------------------------------------------------------------------------------------------------

public Student(String name, String address) {
	super(name, address);
}



public void addCourseGrade(String course, int grade) {
	courses[numCourses] = course;
	grades[numCourses] = grade;
	numCourses++;
}

public void printGrades() {
	System.out.print(this.toString());
	for(int i = 0; i<numCourses; i++) {
		System.out.print(" " + courses[i] + ":"+ grades[i]);
	}
	System.out.println();
}

public double getAverageGrade() {
	double ave = 0;
	for(int grade: grades) {
		ave = ave + grade;
	}
	
	return ave/numCourses;
}

public String toString() {
	return "Student: " + this.getName() + "(" + this.getAddress() + ")";
	}

}
