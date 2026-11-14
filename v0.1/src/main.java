import java.util.*;
public class main {
    public static void main (String[] args){
        FR1();
    }
    
    public static void FR1(){
        //Creating Media
        Book b1 = new Book("Siddhartha",new Date(1,10,1922),
                "Hermann Hesse's philosophical novel Siddhartha tells the story of a young man" +
                        "in ancient India who abandons both asceticism and material wealth on a lifelong," +
                        "personal quest to find true spiritual enlightenment and self-realization.",
                "Hermann Hesse", "978-0-553-20884-9", 152);
        Book b2 = new Book("Siddhartha (Modern Edition)",new Date(17,10,2022),
                "Hermann Hesse's philosophical novel Siddhartha tells the story of a young man" +
                        "in ancient India who abandons both asceticism and material wealth on a lifelong," +
                        "personal quest to find true spiritual enlightenment and self-realization.",
                "Hermann Hesse", "978-0-553-20884-10", 155);
        Movie m1 = new Movie("Marvel's The Avengers",new Date(4,5,2012),"American Superhero film based on," +
                "the Marvel Comics superhero team the Avengers.","Joss Whedon",143);

        //Adding Media to the catalog database
        Search.getSINGLETON().addMedia(b1);
        Search.getSINGLETON().addMedia(b2);
        Search.getSINGLETON().addMedia(m1);

        //Example of Functional Requirement 1: Title Search
        Scanner input = new Scanner(System.in);
        String currentSearch;
        System.out.print("Search for media: ");
        currentSearch = input.nextLine();

        //Using the user's input: currentSearch, search the catalog and return the found titles as an Arraylist
        //Then display the Arraylist
        Search.getSINGLETON().displayResults(Search.getSINGLETON().searchByTitle(currentSearch));

    }

}

