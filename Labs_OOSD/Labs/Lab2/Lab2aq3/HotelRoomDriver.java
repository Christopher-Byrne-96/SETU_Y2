package Lab2aq3;

import Lab2aq3.HotelRoom;

public class HotelRoomDriver {

	public static void main(String[] args) 
	
	{
		
	HotelRoom roomA = new HotelRoom();
	HotelRoom roomB = new HotelRoom();
	HotelRoom roomC = new HotelRoom( 202, 0 , 90 , "Single");
	
	roomA.setRoomNum(200);
	roomA.setRoomType("Single");
	roomA.setRoomStatus(1);
	roomA.setRoomRate(100);
	
	
	roomB.setRoomNum(201);
	roomB.setRoomType("double");
	roomB.setRoomStatus(0);
	roomB.setRoomRate(80);
	
	System.out.println("..................................................................................................................................");
	System.out.println("RoomA room number is " + roomA.getRoomNum() + ", room type is " + roomA.getRoomType() );
	System.out.println("RoomA room rate is " + roomA.getRoomRate() + ", room status is " + roomA.getRoomStatus() );
	System.out.println("..................................................................................................................................");
	System.out.println("RoomB room number is " + roomB.getRoomNum() + ", room type is " + roomB.getRoomType() );
	System.out.println("RoomB room rate is " + roomB.getRoomRate() + ", room status is " + roomB.getRoomStatus() );
	System.out.println("..................................................................................................................................");
	System.out.println("RoomC room number is " + roomC.getRoomNum() + ", room type is " + roomC.getRoomType() );
	System.out.println("RoomC room rate is " + roomC.getRoomRate() + ", room status is " + roomC.getRoomStatus() );
	System.out.println("..................................................................................................................................");
	



	}

}
