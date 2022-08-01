package cinema;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class cinemaInformation {

    private int rows;
    private int cols;

    private List<Seat> seats;
    private List<Seat> reservedSeats;

    cinemaInformation(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.seats = new ArrayList<Seat>();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.seats.add(new Seat(i + 1, j + 1));
            }
        }
        this.reservedSeats = new ArrayList<>();
    }

    public List<Seat> getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(List<Seat> reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Seat> getSeats() {
        return seats;
    }


}


class Seat {
    private int row;
    private int column;

    private int price;
    private Token token;

    Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = row <= 4 ? 10 : 8;
        this.token = new Token();
    }

    Seat(){
        this.token = new Token();
    }

    public int getRow() {
        return row;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setCol(int col) {
        this.column = col;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        int price = getRow() <= 4 ? 10 : 8;
        return "{\"row\":" + this.row + ",\"column\":" + this.column + ",\"price\":" + price + "}";
    }

    public String toStringWithToken() {
        return "{" + this.token.toString() + ",\"ticket\": " +
                "{\"row\":" + this.row + ",\"column\":" + this.column + ",\"price\":" + price + "}}";
    }
}

class Token {
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    Token() {
        this.token = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "\"token\": \"" + getToken() + "\"";
    }

    @Override
    public boolean equals(Object b) {
        if (this == b) {
            return true;
        }
        if (b == null || b.getClass() != this.getClass()) {
            return false;
        }
        Token t = (Token) b;
        return this.getToken().equals(t.getToken());
    }
}