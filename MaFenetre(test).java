import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Créer un SplitPane
        SplitPane splitPane = new SplitPane();

        // Créer une partie gauche du SplitPane
        VBox leftPane = new VBox();
        BorderPane highLeftPane = new BorderPane();
        BorderPane lowLeftPane = new BorderPane();
        lowLeftPane.setPrefSize(400,600);
        Label label1 = new Label("Partie gauche du haut");
        highLeftPane.getChildren().add(label1);
        leftPane.getChildren().addAll(highLeftPane,lowLeftPane);

        // Créer une partie droite du SplitPane
        GridPane rightPane = new GridPane();
        Label label3 = new Label("Partie droite");
        rightPane.add(label3, 0, 0);

        // Ajouter les parties gauche et droite au SplitPane
        splitPane.getItems().addAll(leftPane, rightPane);

        // Diviser la partie gauche en deux parties supplémentaires
        SplitPane leftSplitPane = new SplitPane();
        leftSplitPane.setOrientation(Orientation.VERTICAL);
        leftSplitPane.getItems().addAll(highLeftPane,lowLeftPane);

        // Définir les proportions des parties dans le SplitPane
        splitPane.setDividerPositions(0.5);
        leftSplitPane.setDividerPositions(0.9);

        // Ajouter le SplitPane pour la partie gauche
        leftPane.getChildren().add(leftSplitPane);

//////////////////////////////////////////////////////////////////////////////////////////////////
//Partie sur les interfaces, boutton, tableau, etc.\

        // Code pour la partie bas gauche

        // Ajouter du bouton roulette
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Automne", "Été", "Hiver");

        // Ajouter un bouton pour stocker la réponse sélectionnée dans la ComboBox
        Button bouton2 = new Button("Stocker la réponse");
        bouton2.setOnAction(event -> {
            optionSelectionner = comboBox.getValue();
            System.out.println("La réponse sélectionnée est : " + optionSelectionner);
        });

        //Placer les boutons
        BorderPane.setAlignment(bouton2, Pos.CENTER_RIGHT);
        lowLeftPane.setRight(bouton2);
        BorderPane.setAlignment(comboBox, Pos.CENTER_LEFT);
        lowLeftPane.setLeft(comboBox);
        BorderPane.setMargin(comboBox, new Insets(10));
        BorderPane.setMargin(bouton2, new Insets(10));

        // Code pour la partie haut gauche

        //Titre du tableau
        Label titreTab = new Label("Liste des cours");
        titreTab.setStyle("-fx-font-size: 20pt;");

        //Positionnement
        titreTab.setAlignment(Pos.CENTER);
        highLeftPane.setTop(titreTab);
        BorderPane.setAlignment(titreTab, Pos.CENTER);
        BorderPane.setMargin(titreTab, new Insets(10));

        //Tableau
        //TableView<coursesList> tabCours = new TableView<>();

        //Ajout des colonnes
        //TableColumn<>





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
//  VBox root = new VBox();
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
