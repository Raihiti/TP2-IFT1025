import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.util.ArrayList;
import server.models.RegistrationForm;
import server.models.Course;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.scene.input.MouseEvent;



public class client_fx extends Application {

    private String sessionSelectionner;
    private static Socket clientSocket;

    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static ArrayList<Course> coursesList;
    private String prenom;
    private String nom;
    private String email;
    private String matricule;
    private String nomCours;
    private String codeCours;
    private int IdErreur = 0 ;

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
//Partie sur les interfaces, boutton, tableau, etc.\

        // CODE PARTIE HAUT GAUCHE

        //Titre du tableau
        Label titreTab = new Label("Liste des cours");
        titreTab.setStyle("-fx-font-size: 20pt;");

        //Positionnement
        titreTab.setAlignment(Pos.CENTER);
        highLeftPane.setTop(titreTab);
        BorderPane.setAlignment(titreTab, Pos.CENTER);
        BorderPane.setMargin(titreTab, new Insets(10));


        //TCreation tableau
        TableView<Course> tabCours = new TableView<>();

        //Creation des colonnes
        TableColumn<Course, String> Code = new TableColumn<>("Code");
        Code.setCellValueFactory(new PropertyValueFactory<>("code"));
        TableColumn<Course, String> Cours = new TableColumn<>("Cours");
        Cours.setCellValueFactory(new PropertyValueFactory<>("name"));

        // redimensionnement de colonnes pour que les colonnes prennent tout l'espace disponible
        tabCours.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        tabCours.getColumns().addAll(Code, Cours);


        //Evenement : lorsque l'utilisateur clique sur une ligne du tableau, on stocke la valeur
        tabCours.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Une ligne est sélectionnée, faire quelque chose
                    if (tabCours.getSelectionModel().getSelectedItem() != null) {
                        Course coursSelectionner = tabCours.getSelectionModel().getSelectedItem();
                        codeCours = coursSelectionner.getCode();
                        nomCours = coursSelectionner.getName();
                    }
                }
            });


        highLeftPane.setCenter(tabCours);

        // CODE PARTIE BAS GAUCHE

        // Ajouter du bouton roulette
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Automne", "Ete", "Hiver");

        // Ajouter un bouton pour charger la liste des cours
        Button charger = new Button("Charger");
        charger.setOnAction(event -> {
            sessionSelectionner = comboBox.getValue();
            //Appel du serveur pour recuperer la liste des cours
            try {
                clientSocket = new Socket("127.0.0.1", 1337);
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                objectOutputStream.writeObject("CHARGER " + sessionSelectionner);
                coursesList = (ArrayList<Course>) objectInputStream.readObject();
                if (coursesList.isEmpty()) {
                    System.out.println("vide");
                }
                ObservableList<Course> data = FXCollections.observableArrayList(coursesList);
                tabCours.setItems(data);


            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        });

        //Placer les boutons
        BorderPane.setAlignment(charger, Pos.CENTER_RIGHT);
        lowLeftPane.setRight(charger);
        BorderPane.setAlignment(comboBox, Pos.CENTER_LEFT);
        lowLeftPane.setLeft(comboBox);
        BorderPane.setMargin(comboBox, new Insets(10));
        BorderPane.setMargin(charger, new Insets(10));


        // CODE POUR LA PARTIE DROITE

        //Titre
        Label titreInscription = new Label("Formulaire d'inscription");
        titreInscription.setStyle("-fx-font-size: 20pt;");
        rightPane.setTop(titreInscription);
        BorderPane.setMargin(titreInscription, new Insets(10));
        BorderPane.setAlignment(titreInscription, Pos.CENTER);

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

        //Creation du bouton envoyer puis de l'événement lorsque le bouton est cliqué

        Button envoyer = new Button("envoyer");
        envoyer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent mouseEvent) {
                // Si les champs ne sont pas remplis correctement, traitement des erreurs de champs puis renvoie une erreur
                if (prenomCase.getText().isEmpty() || nomCase.getText().isEmpty() && matriculeCase.getText().isEmpty() && emailCase.getText().isEmpty()) {

                    alerteErreur("Veuillez remplir les champs correctement.");
                } else if (nomCours == null || codeCours == null) {
                    alerteErreur("Veuillez choisir un cours dans la liste des cours en cliquant dessus.");
                }
                if (!emailCase.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    IdErreur = 1;
                }
                if (matriculeCase.getText().length() != 8) {
                    IdErreur = 2;
                }
                if(!emailCase.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") && matriculeCase.getText().length() != 8 ){
                    IdErreur = 3;
                }
                 if (IdErreur != 0){
                    if (IdErreur == 1){
                        alerteErreur("Le champ email n'est pas correct.");
                        IdErreur = 0;
                    } else if (IdErreur == 2) {
                        alerteErreur("Le champ matricule n'est pas correct.");
                        System.out.println(matriculeCase.getText().length());
                        IdErreur = 0;
                    }
                    else{
                        alerteErreur("Le champ mail et le champ matricule sont incorrect.");
                        IdErreur = 0;
                    }

                } else {
                    prenom = prenomCase.getText();
                    nom = nomCase.getText();
                    email = emailCase.getText();
                    matricule = matriculeCase.getText();
                    Course course = new Course(nomCours, codeCours, sessionSelectionner);
                    RegistrationForm formular = new RegistrationForm(prenom, nom, email, matricule, course);

                    try {
                        clientSocket = new Socket("127.0.0.1", 1337);
                        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                        objectOutputStream.writeObject("INSCRIRE");
                        objectOutputStream.writeObject(formular);

                        //Recevoir la confirmation d'inscription
                        alerteConfirm(objectInputStream.readObject().toString());
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        });


        espace.setAlignment(Pos.TOP_CENTER);

        //Ajout et positionnement du texte avec le bouton approprier

        espace.add(label1, 0, 0);
        espace.add(prenomCase, 1, 0);
        espace.add(label2, 0, 1);
        espace.add(nomCase, 1, 1);
        espace.add(label3, 0, 2);
        espace.add(emailCase, 1, 2);
        espace.add(label4, 0, 3);
        espace.add(matriculeCase, 1, 3);
        espace.add(envoyer, 1, 4);

        rightPane.setCenter(espace);
        BorderPane.setMargin(espace, new Insets(20, 0, 0, 0));


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


    public static void alerteErreur(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void alerteConfirm(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation inscription");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}

