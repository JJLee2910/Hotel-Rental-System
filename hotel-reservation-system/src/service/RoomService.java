package service;

import data.Room;

import java.util.List;

/**
 * backend logic related to room
 */
public interface RoomService {
    /**
     *
     * @return all room list
     */
    List<Room> getAllRooms();

    /**
     * get room by id
     * @param roomId
     * @return room
     */
    Room getRoomById(String roomId);
}
