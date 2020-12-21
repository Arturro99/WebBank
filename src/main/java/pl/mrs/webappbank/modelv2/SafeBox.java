package pl.mrs.webappbank.modelv2;

public class SafeBox extends Resource {
    static class Position{
        int row;
        int column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
    Position position;

    public SafeBox(int row, int column) {
        this.position = new Position(row,column);
    }

}
