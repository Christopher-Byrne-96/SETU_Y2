package Lab6aq1;

public class Teacher extends Person {
	
	private int numCourses;
	private String[] courses = new String[10];
	
	public Teacher(String name, String address) {
		super(name, address);
	}
	
	public boolean addCourse(String course) {
		for(int i = 0; i < numCourses; i++) {
			if(courses[i].equalsIgnoreCase(course)) {
				return false;
			}
		}
		courses[numCourses] = course;
		numCourses++;
		return true;
	}
	
	public boolean removeCourse(String course) {
		for(int i = 0; i < numCourses; i++) {
			if(courses[i].equalsIgnoreCase(course)) {
				courses[i] = "";
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return "Teacher: " + this.getName() + "(" + this.getAddress() + ")";
	}

}
