import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;


public class MaFenetre extends Application {

    private String optionSelectionner;
    private String prenom;
    private String nom;
    private String email;
    private String matricule;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Créer un SplitPane
        SplitPane splitPane = new SplitPane();

        // Créer une partie gauche du SplitPane
        VBox leftPane = new VBox();
        BorderPane highLeftPane = new BorderPane();
        BorderPane lowLeftPane = new BorderPane();
        lowLeftPane.setPrefSize(400, 600);
        leftPane.getChildren().addAll(highLeftPane, lowLeftPane);

        // Créer une partie droite du SplitPane
        BorderPane rightPane = new BorderPane();

        // Ajouter les parties gauche et droite au SplitPane
        splitPane.getItems().addAll(leftPane, rightPane);

        // Diviser la partie gauche en deux parties supplémentaires
        SplitPane leftSplitPane = new SplitPane();
        leftSplitPane.setOrientation(Orientation.VERTICAL);
        leftSplitPane.getItems().addAll(highLeftPane, lowLeftPane);

        // Définir les proportions des parties dans le SplitPane
        splitPane.setDividerPositions(0.5);
        leftSplitPane.setDividerPositions(0.9);

        // Ajouter le SplitPane pour la partie gauche
        leftPane.getChildren().add(leftSplitPane);

//////////////////////////////////////////////////////////////////////////////////////////////////
//Partie sur les interfaces, boutton, tableau, etc.

        // Code pour la partie bas gauche

        // Ajouter du bouton roulette
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Automne", "Été", "Hiver");

        // Ajouter un bouton pour stocker la réponse sélectionnée dans la ComboBox
        Button charger = new Button("Charger");
        charger.setOnAction(event -> {
            optionSelectionner = comboBox.getValue();
            System.out.println("La réponse sélectionnée est : " + optionSelectionner);
        });

        //Placer les boutons
        BorderPane.setAlignment(charger, Pos.CENTER_RIGHT);
        lowLeftPane.setRight(charger);
        BorderPane.setAlignment(comboBox, Pos.CENTER_LEFT);
        lowLeftPane.setLeft(comboBox);
        BorderPane.setMargin(comboBox, new Insets(10));
        BorderPane.setMargin(charger, new Insets(10));





        // Code pour la partie haut gauche

        //Titre du tableau
        Label titreTab = new Label("Liste des cours");
        titreTab.setStyle("-fx-font-size: 20pt;");

        //Positionnement

        highLeftPane.setTop(titreTab);
        BorderPane.setAlignment(titreTab, Pos.CENTER);
        BorderPane.setMargin(titreTab, new Insets(10));

        //Tableau
        TableView<ArrayList> tabCours = new TableView<>();
        highLeftPane.setCenter(tabCours);





        // Code pour la partie droite

        //Titre
        Label titreDroite = new Label("Formulaire d'inscription");
        titreDroite.setStyle("-fx-font-size: 20pt;");
        rightPane.setTop(titreDroite);
        BorderPane.setAlignment(titreDroite, Pos.CENTER);

        // Permet creer une zone de texte et de la contenir dans une variable
        GridPane espace = new GridPane();
        espace.setHgap(10);
        espace.setVgap(20);

        // Chaque case de texte a son texte approprié
        TextField prenomCase = new TextField();
        Label label1 = new Label("Prenom");
        prenomCase.setPrefHeight(10);
        prenomCase.setPrefWidth(200);


        TextField nomCase = new TextField();
        Label label2 = new Label("Nom");
        nomCase.setPrefHeight(10);
        nomCase.setPrefWidth(200);


        TextField emailCase = new TextField();
        Label label3 = new Label("email");
        emailCase.setPrefHeight(10);
        emailCase.setPrefWidth(200);


        TextField matriculeCase = new TextField();
        Label label4 = new Label("matricule");
        matriculeCase.setPrefHeight(10);
        matriculeCase.setPrefWidth(200);


        Button envoyer = new Button("envoyer");
        envoyer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent mouseEvent) {
                prenom = prenomCase.getText();
                nom = nomCase.getText();
                email = emailCase.getText();
                matricule = matriculeCase.getText();
                ArrayList<String> test = new ArrayList<>();
                test.add(prenom);
                test.add(nom);
                test.add(email);
                test.add(matricule);
                for (int i = 0; i < 4; i++){
                    System.out.println(test.get(i));

            }

        }});


        espace.setAlignment(Pos.TOP_CENTER);

        espace.add(label1, 0, 0);
        espace.add(prenomCase, 1, 0);
        espace.add(label2,0 ,1 );
        espace.add(nomCase, 1 ,1);
        espace.add(label3,0 , 2);
        espace.add(emailCase,1 ,2);
        espace.add(label4, 0 ,3);
        espace.add(matriculeCase,1,3);
        espace.add(envoyer,1,4);

        rightPane.setCenter(espace);
        BorderPane.setMargin(espace, new Insets(20,0,0,0));













// creer la fenetre
        // Créer une scène et ajouter le SplitPane
        Scene scene = new Scene(splitPane, 800, 600);

        // Afficher la scène
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}

// Permet creer une zone de texte et de la contenir dans une variable
//
//        TextField textField = new TextField();
//        textField.setOnAction(event -> {
//            userInput = textField.getText();
//            System.out.println(userInput);
//            textField.clear();
//        });
//
//        root.getChildren().add(textField);

// // Ajouter une ComboBox avec trois options dans la partie inférieure de la partie gauche
//        ComboBox<String> comboBox = new ComboBox<>();
//        comboBox.getItems().addAll("Option 1", "Option 2", "Option 3");
//        leftPane.add(comboBox, 0, 2);
//
//        // Ajouter un bouton pour stocker la réponse sélectionnée dans la ComboBox
//        Button button2 = new Button("Stocker la réponse");
//        button2.setOnAction(event -> {
//            selectedOption = comboBox.getValue();
//            System.out.println("La réponse sélectionnée est : " + selectedOption);
// ps : selectedOption est une variable String (private String selectedOption;)

// code pour mettre une bar en bas
//        ScrollPane scrollPane = new ScrollPane(tableau);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);


//Ajout d'un evenement lorsque l'utilisateur clique sur le tableau, on stocke la valeur
//tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                // recuperer la ligne cliquer
//                Person selectedPerson = tableView.getSelectionModel().getSelectedItem();
//                String name = selectedPerson.getFirstName();
//                String lastname = selectedPerson.getLastName();
//                selectedValue = name + lastname;
//            }
//        });
