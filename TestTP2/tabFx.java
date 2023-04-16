import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
public class tabFx extends Application {

    private String selectedValue;

    @Override
    public void start(Stage stage) throws Exception {

        // creation du tableau JavaFX
        TableView<Person> tableView = new TableView<>();

        // creation des colonnes
        TableColumn<Person, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Person, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));



        // On ajoute les colonnes au tableau
        tableView.getColumns().addAll(firstName,lastName);

        // Entrer des donnees dans le tabView
        ObservableList<Person> data = FXCollections.observableArrayList(
                new Person("John", "Doe"),
                new Person("Jane", "Doe"),
                new Person("Bob", "Smith"));
        tableView.setItems(data);

        // Ajout d'un evenement lorsque l'utilisateur clique sur le tableau
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // get selected value from table view
                Person selectedPerson = tableView.getSelectionModel().getSelectedItem();
                String name = selectedPerson.getFirstName();
                String lastname = selectedPerson.getLastName();
                selectedValue = name + "" + lastname;
            }
        });

        // Bouton pour afficher la valeur
        Button button = new Button("Stocker la valeur");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Stocke la valeur
                System.out.println(selectedValue);
            }
        });

        // creation BorderPane avec le bouton et le tableau
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setBottom(button);

        // Creation de la scene
        Scene scene = new Scene(borderPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Person class : pour le test du tableau
    public static class Person {
        private String firstName;
        private String lastName;

        public Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}
