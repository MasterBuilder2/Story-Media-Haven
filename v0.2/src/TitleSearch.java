import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TitleSearch extends Application {
    private Stage primaryStage;
    private Scene searchScene;
    private ArrayList<Media> currentResults = new ArrayList<>();

    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        Pane root = new Pane();
        this.searchScene = new Scene(root, 1440, 800);
        //Scene SearchScene = new Scene(root, 1440, 800);

        //Left-hand main button creation
        Button search_discover = createSquareButton("Search & Discover",0,0);
        Button favorites = createSquareButton("Favorites",0,200);
        Button reviews = createSquareButton("My Reviews", 0,400);
        Button account = createSquareButton("Account Dashboard", 0,600);
        root.getChildren().addAll(search_discover, favorites, reviews, account);

        // Search Bar
        TextField searchInput = new TextField();
        searchInput.setPromptText("Enter a title to search..."); // Gray placeholder text

        searchInput.setPrefSize(940, 50);
        searchInput.setLayoutX(300);
        searchInput.setLayoutY(50);

        Button search = new Button("Search");
        search.setPrefSize(100, 50);
        search.setLayoutX(1240);
        search.setLayoutY(50);

        // Filter type
        TextField filterField = new TextField("Filter:");
        filterField.setEditable(false);       // Prevents typing/input
        filterField.setFocusTraversable(false); // Prevents tabbing into it

        filterField.setLayoutX(1140);
        filterField.setLayoutY(125);
        filterField.setPrefSize(100,50);

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("All","Book","Movie");
        choiceBox.setValue("All");
        choiceBox.setPrefSize(100,50);

        VBox filter = new VBox(10,choiceBox); // 10 pixel gap
        filter.setPrefSize(100,50);
        filter.setLayoutX(1240);
        filter.setLayoutY(125);


        // Display Results
        ListView<String> resultsList = new ListView<>();
        resultsList.setPrefSize(1040, 500);
        resultsList.setLayoutX(300);
        resultsList.setLayoutY(200);
        resultsList.setVisible(true);

        search.setOnAction(e -> {
            String typedText = searchInput.getText();
            System.out.println("User searched for: " + typedText);
        });

        // 4. Add both to the root Pane
        root.getChildren().addAll(searchInput, search,resultsList,filter,filterField);

        Runnable performSearch = () -> {

            String query = searchInput.getText().trim();
            ArrayList<Media> initialResults = Search.getSINGLETON().searchByTitle(query);
            String selectedFilter = choiceBox.getValue();
            resultsList.getItems().clear();
            currentResults.clear();

            for (Media m : initialResults) {
                if(selectedFilter.equals("Book"))
                {
                    if(m instanceof Book){
                        currentResults.add(m);
                        resultsList.getItems().add(m.getTitle());
                    }
                }
                else if (selectedFilter.equals("Movie")){
                    if(m instanceof Movie){
                        currentResults.add(m);
                        resultsList.getItems().add(m.getTitle());
                    }
                }
                else
                {
                    currentResults.add(m);
                    resultsList.getItems().add(m.getTitle());
                }
            }

            resultsList.setVisible(true);
        };

        // Redirect to Media Page when the user clicks a row in resultsList
        resultsList.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            int index = newIndex.intValue();

            // Ensure a valid row was clicked
            if (index >= 0 && index < currentResults.size()) {
                Media selectedMedia = currentResults.get(index);

                // Clear selection so clicking the same item later still works
                resultsList.getSelectionModel().clearSelection();

                // Switch the window to the Media Page scene (passing SearchScene so Back works)
                primaryStage.setScene(MediaPageScene(selectedMedia));
            }
        });


        search.setOnAction(e -> performSearch.run());
        searchInput.setOnAction(e -> performSearch.run());
        choiceBox.setOnAction(e -> performSearch.run());

        //display
        primaryStage.setTitle("Story Media Haven");
        primaryStage.setScene(searchScene);
        primaryStage.show();

    }

    private Scene MediaPageScene(Media media) {
        Pane mediaRoot = new Pane();
        //Left-hand main button creation
        Button search_discover = createSquareButton("Search & Discover",0,0);
        Button favorites = createSquareButton("Favorites",0,200);
        Button reviews = createSquareButton("My Reviews", 0,400);
        Button account = createSquareButton("Account Dashboard", 0,600);

        TextField mediaInfo = new TextField();
        mediaInfo.setEditable(false);       // Prevents typing/input
        mediaInfo.setFocusTraversable(false); // Prevents tabbing into it

        mediaInfo.setLayoutX(300);
        mediaInfo.setLayoutY(50);
        mediaInfo.setPrefSize(940,50);

        if(media instanceof Book)
        {
            mediaInfo.setText(media.getTitle() + ", " + ((Book) media).getAuthor() + ", " + media.getReleaseDate().toString());

        }
        else if (media instanceof Movie)
        {
            mediaInfo.setText(media.getTitle() + ", " + ((Movie) media).getDirector() + ", " + media.getReleaseDate().toString());
        }


        mediaRoot.getChildren().addAll(search_discover, favorites, reviews, account,mediaInfo);



        search_discover.setOnAction(e -> {
            primaryStage.setScene(searchScene);
        });

        return new Scene(mediaRoot, 1440, 800);
    }

    private Button createSquareButton(String text, double x, double y) {
        Button btn = new Button(text);

        btn.setPrefSize(200, 200); // 200x200 buttons

        // Set exact top-left corner coordinates
        btn.setLayoutX(x);
        btn.setLayoutY(y);

        return btn;
    }

    public static void main(String[] args)
    {
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

        Search.getSINGLETON().addMedia(b1);
        Search.getSINGLETON().addMedia(b2);
        Search.getSINGLETON().addMedia(m1);

        Application.launch(args);
    }
}

