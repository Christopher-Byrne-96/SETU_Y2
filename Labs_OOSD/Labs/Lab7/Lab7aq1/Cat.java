package Lab7aq1;

public class Cat extends Animal {
	
	public Cat(String type, int age, char gender) {
		super(type,age,gender);
	}

   //Other Methods
   
   public void eat() {
	   System.out.println("Cat is eating");
   }
   
   public void sleep() {
	   System.out.println("Cat is sleeping");
   }
   
   public void makeSound() {
	   System.out.println("Prrrrrrrr");
   }
}
