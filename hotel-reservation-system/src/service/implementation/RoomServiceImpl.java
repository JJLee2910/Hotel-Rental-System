package service.implementation;

import dao.RoomsUtil;
import data.Room;
import service.RoomService;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RoomServiceImpl implements RoomService {
    @Override
    public List<Room> getAllRooms() {
        try {
            RoomsUtil roomsUtil = new RoomsUtil();
            return roomsUtil.getAllRooms();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Room getRoomById(String roomId) {
        try {
            RoomsUtil roomsUtil = new RoomsUtil();
            return roomsUtil.getAllRooms().stream().filter(room -> room.getId().equals(roomId)).findFirst().orElseGet(null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
