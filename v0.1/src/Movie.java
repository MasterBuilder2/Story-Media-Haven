public class Movie extends Media {
    private String director;
    private int runtimeMinutes;

    Movie(String title, Date releaseDate, String synopsis){
        super(title, releaseDate, synopsis);
    }

    Movie(String title, Date releaseDate, String synopsis, String director, int runtimeMinutes){
        super(title, releaseDate, synopsis);
        this.director = director;
        this.runtimeMinutes = runtimeMinutes;
    }

    public String getDirector() {
        return director;
    }

    public int getRuntimeMinutes() {
        return runtimeMinutes;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setRuntimeMinutes(int runtimeMinutes) {
        this.runtimeMinutes = runtimeMinutes;
    }
}
