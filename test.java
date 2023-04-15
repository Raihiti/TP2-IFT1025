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
public class test extends Application {

    private String selectedValue;

    @Override
    public void start(Stage stage) throws Exception {

        // create table view
        TableView<Person> tableView = new TableView<>();

        // create columns
        TableColumn<Person, String> firstName = new TableColumn<>("First Name");
        firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Person, String> lastName = new TableColumn<>("Last Name");
        lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));



        // add columns to table view
        tableView.getColumns().addAll(firstName,lastName);

        // add data to table view
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
        Button button = new Button("Store Selected Value");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // store selected value in variable
                System.out.println(selectedValue);
            }
        });

        // create border pane and add table view and button to it
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(tableView);
        borderPane.setBottom(button);

        // create scene and set it on the stage
        Scene scene = new Scene(borderPane, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Person class
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