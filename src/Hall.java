import java.util.ArrayList;

public class Hall {
    static Hall hall;
    private final String filmName;
    private final String hallName;
    private final int seatPrice;
    private int discountPrice;
    private final int row;
    private final int col;

    public Hall(String filmName, String hallName, int seatPrice, int row, int col) {
        this.filmName = filmName;
        this.hallName = hallName;
        this.seatPrice = seatPrice;
        setDiscountPrice(seatPrice);
        this.row = row;
        this.col = col;
    }

    public String getFilmName() {
        return filmName;
    }
    public String getHallName() {
        return hallName;
    }
    public int getSeatPrice() {
        return seatPrice;
    }
    public int getDiscountPrice() {
        return discountPrice;
    }
    public void setDiscountPrice(int discountPrice) {
        String percantage = Main.properties.getProperty("discount-percentage");
        this.discountPrice = (discountPrice * (100 - Integer.parseInt(percantage))) / 100;
    }

    @Override
    public String toString() {
        return "hall\t" +
                filmName + '\t' +
                hallName + '\t' +
                seatPrice + "\t" +
                row + "\t" +
                col + "\t" +
                '\n';
    }
}

class Seat{
    private final String filmName;
    private final String hallName;
    private final Hall hall;
    private final int row;
    private final int col;
    private User owner;
    private String ownerS;
    private int price;

    public Seat(String filmName, String hallName, int row, int col, String owner, int price, ArrayList<Object> halls, ArrayList<Object> users) {
        this.filmName = filmName;
        this.hallName = hallName;
        this.hall = hallFinder(halls);
        this.owner = userFinder(users, owner);
        this.ownerS = owner;
        this.price = price;
        this.row = row;
        this.col = col;
    }

    public Hall hallFinder(ArrayList<Object> halls){
        for (Object hal: halls){
            Hall hall = (Hall) hal;
            if (hall.getHallName().equals(this.hallName)){
                return hall;
            }
        }
        return null;
    }
    public User userFinder(ArrayList<Object> users, String name){
        if (name.equals("null")){
            return null;
        }
        for (Object user1: users){
            User user = (User) user1;
            if (user.getUserName().equals(name)){
                return user;
            }
        }
        return null;
    }

    public String getFilmName() {
        return filmName;
    }
    public Hall getHall() {
        return hall;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public User getOwner() {
        return owner;
    }
    public String getHallName() {
        return hallName;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }
    public void setOwnerS(String ownerS) {
        this.ownerS = ownerS;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public String getOwnerS() {
        return ownerS;
    }
    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "seat\t" +
                filmName + '\t' +
                hallName + '\t' +
                row + "\t" +
                col + "\t" +
                ownerS+ "\t" +
                price + "\t" +
                '\n';
    }

}// end of class
