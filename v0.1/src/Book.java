public class Book extends Media{
    private String author;
    private String ISBN;
    private int pageCount;

    Book(String title, Date releaseDate, String synopsis){
        super(title, releaseDate, synopsis);
    }
    Book(String title, Date releaseDate, String synopsis, String author, String ISBN, int pageCount){
        super(title, releaseDate, synopsis);
        this.author = author;
        this.ISBN = ISBN;
        this.pageCount = pageCount;
    }

    public String getAuthor() {
        return author;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setISBN(String ISBN){
        this.ISBN = ISBN;
    }

    public void setPageCount(int pageCount){
        this.pageCount = pageCount;
    }
}
