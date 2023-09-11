package Lab2aq1;

// Implements a class HotelRoom 
//Stores Room Number and Room Type in private variables

public class HotelRoom 
	{
	

		private int roomNum;		// Private int can only be changed by methods in this class
		private String roomType;	// Private String can only be changed by methods in this class
		

		public HotelRoom()			// Constructor method which takes no arguments
		{
			roomNum = (0);
			roomType = "";
		}
		
		public void setRoomNum(int num)	// Setter for room number which takes an int as an argument
		{
			roomNum = num;
		}
		
		public void setRoomType(String type )	// Setter for room type which takes string as an argument
		{
			if(type.equalsIgnoreCase("Single")) 
			{
				roomType = "Single";
			}
			else if(type.equalsIgnoreCase("Double"))
			{
				roomType = "Double";
			}
			else
			{
				roomType = "!!The room type you entered '" + type + "' is not recognised. Please enter type 'Single' or 'Double'!!";
			}
		}
		
		public int getRoomNum()			//Getter method to return room number
		{
			return roomNum;
		}
		
		public String getRoomType()		//Getter method to return room type
		{
			return roomType;
		}
	}
