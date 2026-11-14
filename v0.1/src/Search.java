import java.util.ArrayList;
public class Search {
    private static final Search SINGLETON = new Search();
    private ArrayList<Media> catelogDatabase;
    private Search(){
        this.catelogDatabase = new ArrayList<Media>();

    }

    public static Search getSINGLETON() {
        return SINGLETON;
    }

    public void addMedia(Media m){
        catelogDatabase.add(m);
    }

    public ArrayList<Media> searchByTitle(String query) {
        ArrayList<Media> results = new ArrayList<Media>();
        for (Media m : catelogDatabase) {
            if (m.getTitle().toLowerCase().contains(query.toLowerCase())) {
                results.add(m);
            }
        }
        return results;
    }

    public void displayResults(ArrayList<Media> list){
        for(Media m : list){
            System.out.println(m.toString());
        }
    }

}
