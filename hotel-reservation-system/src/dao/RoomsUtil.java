package dao;

import data.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class RoomsUtil {

    /**
     * Create instance variable
     */
    private List<Room> roomList = new ArrayList<>();

    /**
     * Room Util Constructor, will read the file and store the data in the room list when object is created
     * Format
     * RoomID, View, Price
     */
    public RoomsUtil() throws FileNotFoundException {
        readFile();
    }

    /**
     * Read the file and store the data in the roomList
     * @return roomList
     */
    public List<Room> getAllRooms() {
        return roomList;
    }

    /**
     * Read room list from file
     */
    public void readFile() {
        File myObj = new File(Objects.requireNonNull(getClass().getResource("/database/rooms.txt")).getFile());
        Scanner myReader = null;
        try {
            myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] roomData = data.split(",");
                roomList.add(new Room(roomData[0], Room.View.valueOf(roomData[1]), Double.parseDouble(roomData[2])));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
