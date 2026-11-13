public class Date {
    private int day;
    private int month;
    private int year;

    public Date(){
        this.day = 13;
        this.month = 11;
        this.year = 2026;
    }
    public Date(int day, int month, int year){
        setDate(day,month,year);
    }

    public void setDay(int day) {this.day = day;}
    public void setMonth(int month) {this.month = month;}
    public void setYear(int year) {this.year = year;}
    public void setDate (int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
    @Override
    public String toString(){
        return month + "/" + day + "/" + year;
    }
}
