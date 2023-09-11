package lab2aq2;

public class HotelRoom 
{
	private int roomNum;		// Private int can only be changed by methods in this class
	private int roomStatus;	
	private double roomRate;
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
	
	public void setRoomStatus(int num)	// Setter for room status where 0 is vacant and 1 is occupied
	{
		roomStatus = num;
	}
	
	public void setRoomRate(double num)
	{
		roomRate = num;
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
	
	public int getRoomStatus()			//Getter method to return room Status or return -1 if number is not 1 or 0 
	{
		if(roomStatus > 1 || roomStatus < 0)
		{
			return -1;
		}
		return roomStatus;
	}
	
	public double getRoomRate()			//Getter method to return room rate or -1 if room rate is a negative number 
	{
		if(roomRate < 0)
		{
			roomRate = -1;
		}
		return roomRate;
	}
	
	public String getRoomType()		//Getter method to return room type
	{
		return roomType;
	}
}
