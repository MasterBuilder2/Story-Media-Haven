import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class TitleSearch extends Application {
    private Stage primaryStage;
    private Scene searchScene;
    private ArrayList<Media> currentResults = new ArrayList<>();
    public static User currentUser = new User("Tester1","private@gmail.com","1234Password");

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
        resultsList.setVisible(false);

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
                resultsList.setVisible(false);

                // Switch the window to the Media Page scene (passing SearchScene so Back works)
                primaryStage.setScene(MediaPageScene(selectedMedia));
            }
        });


        search.setOnAction(e -> performSearch.run());
        searchInput.setOnAction(e -> performSearch.run());
        choiceBox.setOnAction(e -> performSearch.run());
        reviews.setOnAction(e->{primaryStage.setScene(reviewsScene());});
        favorites.setOnAction(e->{primaryStage.setScene(favoritesScene());});

        //display
        primaryStage.setTitle("Story Media Haven");
        primaryStage.setScene(searchScene);
        primaryStage.show();

    }

    private Scene favoritesScene(){
        Pane favoritesRoot = new Pane();

        Button search_discover = createSquareButton("Search & Discover",0,0);
        Button favorites = createSquareButton("Favorites",0,200);
        Button reviews = createSquareButton("My Reviews", 0,400);
        Button account = createSquareButton("Account Dashboard", 0,600);

        search_discover.setOnAction(e->{primaryStage.setScene(searchScene);});
        reviews.setOnAction(e->{primaryStage.setScene(reviewsScene());});

        ListView<String> favoritesList = new ListView<>();
        favoritesList.setPrefSize(1040, 500);
        favoritesList.setLayoutX(300);
        favoritesList.setLayoutY(200);
        favoritesList.setVisible(true);

        ArrayList<Media> favoriteBookmarks = currentUser.getBookmarks();

        for(Media bookmark : favoriteBookmarks){
            favoritesList.getItems().add(bookmark.getTitle());
        }


        favoritesList.getSelectionModel().selectedIndexProperty().addListener((obs, oldIndex, newIndex) -> {
            int index = newIndex.intValue();

            // Check against bookmarks size, not currentResults
            if (index >= 0 && index < favoriteBookmarks.size()) {
                // Get the media from bookmarks, not currentResults
                Media selectedMedia = favoriteBookmarks.get(index);

                favoritesList.getSelectionModel().clearSelection();

                // Note: You probably don't need favoritesList.setVisible(false) here
                // since you are switching the entire scene anyway, unless you reuse this list later.
                primaryStage.setScene(MediaPageScene(selectedMedia));
            }
        });

        favoritesRoot.getChildren().addAll(search_discover,favorites,reviews,account,favoritesList);

        return new Scene(favoritesRoot,1440,800);

    }

    private Scene reviewsScene()
    {
        Pane reviewsRoot = new Pane();
        Button search_discover = createSquareButton("Search & Discover",0,0);
        Button favorites = createSquareButton("Favorites",0,200);
        Button reviews = createSquareButton("My Reviews", 0,400);
        Button account = createSquareButton("Account Dashboard", 0,600);

        search_discover.setOnAction(e->{primaryStage.setScene(searchScene);});
        favorites.setOnAction(e->{primaryStage.setScene(favoritesScene());});

        VBox reviewsFeedVBox = new VBox(10);
        ScrollPane reviewScrollPane = new ScrollPane(reviewsFeedVBox);
        reviewScrollPane.setFitToWidth(true);
        reviewScrollPane.setLayoutX(200);
        reviewScrollPane.setLayoutY(0);
        reviewScrollPane.setPrefSize(1140, 800);

        // Populate reviews
        if (currentUser.getReviews().isEmpty()) {
            reviewScrollPane.setVisible(false);
            reviewScrollPane.setManaged(false);
        } else {
            for (Review r : currentUser.getReviews()) {
                reviewsFeedVBox.getChildren().add(createReviewCard(r, r.getMedia(), currentUser, reviewsFeedVBox, reviewScrollPane));
            }
        }

        reviewsRoot.getChildren().addAll(search_discover, favorites, reviews, account);
        reviewsRoot.getChildren().addAll(reviewScrollPane);
        return new Scene(reviewsRoot, 1440, 800);
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

        TextArea Synopsis = new TextArea();
        Synopsis.setEditable(false);
        Synopsis.setFocusTraversable(false);
        Synopsis.setLayoutX(300);
        Synopsis.setLayoutY(100);
        Synopsis.setPrefSize(300,300);
        Synopsis.setWrapText(true);
        Synopsis.setText("Synopsis \n" + media.getSynopsis());

        TextArea addInfo = new TextArea();
        addInfo.setEditable(false);
        addInfo.setFocusTraversable(false);
        addInfo.setLayoutX(300);
        addInfo.setLayoutY(400);
        addInfo.setPrefSize(300,100);
        addInfo.setWrapText(true);

        Button addBookmark = new Button("Bookmark");
        if(currentUser.getBookmarks().isEmpty() || !currentUser.getBookmarks().contains(media)){
            addBookmark.setText("Bookmark");
        }
        else {
            addBookmark.setText("Unbookmark");
        }

        addBookmark.setLayoutX(300);
        addBookmark.setLayoutY(500);
        addBookmark.setPrefSize(300,100);

        addBookmark.setOnAction(e -> {
            if(currentUser.getBookmarks().isEmpty() || !currentUser.getBookmarks().contains(media)) {
                currentUser.addBookmark(media);
                addBookmark.setText("Unbookmark");
            }
            else{
                currentUser.removeBookmark(media);
                addBookmark.setText("Bookmark");
            }

        });

        if(media instanceof Book)
        {
            mediaInfo.setText(media.getTitle() + ", " + ((Book) media).getAuthor() + ", " + media.getReleaseDate().toString());
            addInfo.setText("Page count: " + ((Book) media).getPageCount() + "\n" + "ISBN: " + ((Book) media).getISBN());

        }
        else if (media instanceof Movie)
        {
            mediaInfo.setText(media.getTitle() + ", " + ((Movie) media).getDirector() + ", " + media.getReleaseDate().toString());
            addInfo.setText("Runtime Minutes: " + ((Movie) media).getRuntimeMinutes() + "\n");
        }

        VBox reviewFeedVBox = new VBox(10);
        ScrollPane reviewScrollPane = new ScrollPane(reviewFeedVBox);
        reviewScrollPane.setFitToWidth(true);
        reviewScrollPane.setLayoutX(600);
        reviewScrollPane.setLayoutY(150);
        reviewScrollPane.setPrefSize(640, 250);

        // Populate reviews
        if (media.getReviewList().isEmpty()) {
            reviewScrollPane.setVisible(false);
            reviewScrollPane.setManaged(false);
        } else {
            for (Review r : media.getReviewList()) {
                reviewFeedVBox.getChildren().add(createReviewCard(r, media, currentUser, reviewFeedVBox, reviewScrollPane));
            }
        }

        TextField averageRating = new TextField();
        averageRating.setEditable(false);
        averageRating.setFocusTraversable(false);
        averageRating.setLayoutX(600);
        averageRating.setLayoutY(100);
        averageRating.setPrefSize(640,50);
        averageRating.setText("Average rating: " + media.getAverageRating());

        mediaRoot.getChildren().addAll(search_discover, favorites, reviews, account,mediaInfo,Synopsis,addInfo,reviewScrollPane,averageRating,
                addBookmark);



        search_discover.setOnAction(e -> {
            primaryStage.setScene(searchScene);
        });

        reviews.setOnAction(e->{primaryStage.setScene(reviewsScene());});
        favorites.setOnAction(e->{primaryStage.setScene(favoritesScene());});

        return new Scene(mediaRoot, 1440, 800);
    }

    private VBox createReviewCard(Review review, Media media, User currentUser, VBox reviewFeedVBox, ScrollPane reviewScrollPane) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 12; -fx-background-radius: 6;");

        // 1. Header with Author, Rating, Edit, and Delete Buttons
        Label authorLabel = new Label(review.getOwner().getUsername() + " • " + review.getRating() + "/5");
        authorLabel.setStyle("-fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button editBtn = new Button("Edit");
        Button deleteBtn = new Button("Remove Review"); // <-- NEW: Delete button
        deleteBtn.setStyle("-fx-text-fill: red;");

        boolean isOwner = review.getOwner().equals(currentUser);
        editBtn.setVisible(isOwner);
        editBtn.setManaged(isOwner);
        deleteBtn.setVisible(isOwner);
        deleteBtn.setManaged(isOwner);

        HBox header = new HBox(10, authorLabel, spacer, editBtn, deleteBtn);

        // 2. Read-Only Paragraph View
        Label reviewText = new Label(review.getComment());
        reviewText.setWrapText(true);

        // 3. Inline Edit View (Rating Dropdown + Text Box)
        ComboBox<Integer> ratingDropdown = new ComboBox<>(); // <-- NEW: Rating selector
        ratingDropdown.getItems().addAll(1, 2, 3, 4, 5);
        ratingDropdown.setValue(review.getRating());

        HBox ratingEditRow = new HBox(8, new Label("Rating:"), ratingDropdown);
        ratingEditRow.setVisible(false);
        ratingEditRow.setManaged(false);

        TextArea editArea = new TextArea(review.getComment());
        editArea.setWrapText(true);
        editArea.setPrefRowCount(3);
        editArea.setVisible(false);
        editArea.setManaged(false);

        Button saveBtn = new Button("Save");
        Button cancelBtn = new Button("Cancel");
        HBox editActions = new HBox(8, saveBtn, cancelBtn);
        editActions.setVisible(false);
        editActions.setManaged(false);

        // 4. Button Actions
        editBtn.setOnAction(e -> {
            reviewText.setVisible(false);
            reviewText.setManaged(false);
            editBtn.setVisible(false);
            editBtn.setManaged(false);
            deleteBtn.setVisible(false);
            deleteBtn.setManaged(false);

            ratingEditRow.setVisible(true);
            ratingEditRow.setManaged(true);
            editArea.setVisible(true);
            editArea.setManaged(true);
            editActions.setVisible(true);
            editActions.setManaged(true);
        });

        cancelBtn.setOnAction(e -> {
            ratingDropdown.setValue(review.getRating());
            editArea.setText(review.getComment());
            toggleReadMode(reviewText, editBtn, deleteBtn, ratingEditRow, editArea, editActions);
        });

        saveBtn.setOnAction(e -> {
            String updatedText = editArea.getText();
            int updatedRating = ratingDropdown.getValue();

            review.setComment(updatedText);
            review.setRating(updatedRating); // <-- NEW: Updates rating in model

            reviewText.setText(updatedText);
            authorLabel.setText(review.getOwner().getUsername() + " • " + updatedRating + "/5"); // <-- NEW: Updates rating on screen
            media.updateRating();

            toggleReadMode(reviewText, editBtn, deleteBtn, ratingEditRow, editArea, editActions);
        });

        // <-- NEW: Delete button logic
        deleteBtn.setOnAction(e -> {
            media.getReviewList().remove(review);
            currentUser.getReviews().remove(review);
            reviewFeedVBox.getChildren().remove(card);

            if (media.getReviewList().isEmpty()) {
                reviewScrollPane.setVisible(false);
                reviewScrollPane.setManaged(false);
            }
            media.updateRating();
        });

        card.getChildren().addAll(header, reviewText, ratingEditRow, editArea, editActions);
        return card;
    }

    private void toggleReadMode(Label text, Button editBtn, Button deleteBtn, HBox ratingRow, TextArea area, HBox actions) {
        ratingRow.setVisible(false);
        ratingRow.setManaged(false);
        area.setVisible(false);
        area.setManaged(false);
        actions.setVisible(false);
        actions.setManaged(false);

        text.setVisible(true);
        text.setManaged(true);
        editBtn.setVisible(true);
        editBtn.setManaged(true);
        deleteBtn.setVisible(true);
        deleteBtn.setManaged(true);
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
        User u1 = new User("Username1","username@gmail.com","password");
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

        //m1.addReview(new Review(4,"Nice",new Date(),currentUser));
        //m1.addReview(new Review(5,"Nice",new Date(),currentUser));
        currentUser.submitReview(m1,5, "I really like it");
        u1.submitReview(m1,3, "Not really my kind of story");

        Search.getSINGLETON().addMedia(b1);
        Search.getSINGLETON().addMedia(b2);
        Search.getSINGLETON().addMedia(m1);



        Application.launch(args);
    }
}

