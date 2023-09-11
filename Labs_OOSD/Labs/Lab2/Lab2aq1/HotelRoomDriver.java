package Lab2aq1;

public class HotelRoomDriver {

	public static void main(String[] args) 
	{
		HotelRoom roomA = new HotelRoom();
		HotelRoom roomB = new HotelRoom();
		
		roomA.setRoomNum(200);
		roomA.setRoomType("Single");
		
		roomB.setRoomNum(201);
		roomB.setRoomType("double");
		
		System.out.println("RoomA room number is " + roomA.getRoomNum() + ", room type is " + roomA.getRoomType() );
		System.out.println("RoomB room number is " + roomB.getRoomNum() + ", room type is " + roomB.getRoomType() );

	}

}
