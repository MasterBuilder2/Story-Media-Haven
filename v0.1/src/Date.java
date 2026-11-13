public class Date {
    private int day;
    private int month;
    private int year;
    @Override
    public String toString(){
        return month + "/" + day + "/" + year;
    }
    public void setDay (int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
}
