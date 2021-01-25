package pl.mrs.webappbank.model.resources;

public class SafeBox extends Resource {
    Position position;

    public SafeBox(String description, boolean available,Position position) {
        super(description,available);
        this.position = position;
    }
    public SafeBox(){
        super(null,true);
        this.position = new Position(0,0);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "SafeBox{" +
                "position=" + position +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", available=" + available +
                '}';
    }
}
