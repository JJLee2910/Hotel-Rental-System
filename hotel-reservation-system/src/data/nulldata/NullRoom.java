package data.nulldata;

import data.Room;

/**
 * For UI purpose, No Room selected
 */
public class NullRoom extends Room {

    public NullRoom() {
        super();
    }
    public NullRoom(String id, View view, Double chargesPerNight) {
        super(id, view, chargesPerNight);
    }

    @Override
    public String toString() {
        return "";
    }
}
