package pl.mrs.webappbank.modelv2;

import java.util.Objects;

public class SafeBox extends Resource {
    Position position;

    public SafeBox(String description, boolean available,int row, int column) {
        super(description,available);
        this.position = new Position(row,column);
    }

    public Position getPosition() {
        return position;
    }
}
