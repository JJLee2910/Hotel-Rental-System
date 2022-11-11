package data;

public class Room {
    private String id;
    private View view;
    private Double chargesPerNight;

    public Room(String id, View view, Double chargesPerNight) {
        this.id = id;
        this.view = view;
        this.chargesPerNight = chargesPerNight;
    }

    public Room() {}

    public String getId() {return id;}

    public void setId(String id) {
        this.id = id;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Double getChargesPerNight() {
        return chargesPerNight;
    }

    public void setChargesPerNight(Double chargesPerNight) {
        this.chargesPerNight = chargesPerNight;
    }

    public enum View {
        JUNGLE,
        SEA
    }

    @Override
    public String toString() {
        return id + ", " + view.toString();
    }
}
