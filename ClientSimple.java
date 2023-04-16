import server.models.RegistrationForm;
import server.models.Course;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientSimple {
    private static Socket clientSocket;
    private static ObjectOutputStream objectOutputStream;
    private static ObjectInputStream objectInputStream;
    private static String sessionChoice = "Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:\n1. Automne\n2. Hiver\n3. Ete\n> Choix:";
    private static final String decision = "Que souhaitez-vous faire ?\n1. Consulter les cours offerts pour une autre session\n2. Inscription à un cours\n> Choix:";
    private static ArrayList<Course> coursesList;
    private static final Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
            System.out.print(sessionChoice);
            String session = load(scan.nextInt());
            boolean registered = false;
            int action = scan.nextInt();
            while (!registered) {
                if (action == 1) {
                    System.out.print(sessionChoice);
                    session = load(scan.nextInt());
                    action = scan.nextInt();
                } else if (action == 2) {
                    if (!registration(session)) {
                        action = 1;
                    } else {
                        registered = true;
                    }
                }
            }



        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String load(int choice) throws IOException, ClassNotFoundException {
        clientSocket = new Socket("127.0.0.1", 1337);
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        String session = "";
        if (choice == 1) {
            session = "Automne";
        } else if (choice == 2) {
            session = "Hiver";
        } else if (choice == 3) {
            session = "Ete";
        }
        objectOutputStream.writeObject("CHARGER " + session);

        System.out.println("Les cours offerts pendant la session d'" + session.toLowerCase() + " sont:");
        coursesList = (ArrayList<Course>) objectInputStream.readObject();
        int j = 0;
        for (int i = 0; coursesList.size() - 1 >= i; i++) {
            j++;
            System.out.println(j + ". " + coursesList.get(i).getCode() + "\t" + coursesList.get(i).getName());
        }
        System.out.print(decision);
        return(session);
    }

    public static boolean registration(String session) throws IOException, ClassNotFoundException {

        scan.nextLine();
        System.out.print("> Entrez votre prénom: ");
        String prenom = scan.nextLine();
        System.out.print("> Entrez votre nom: ");
        String nom = scan.nextLine();
        System.out.print("> Entrez votre email (étudiant): ");
        String email = scan.nextLine();
        System.out.print("> Entrez votre matricule: ");
        String matricule = scan.nextLine();
        System.out.print("> Entrez le code du cours: ");
        String code = scan.nextLine();

        String name = "";
        boolean validate = true;
        for (Course c : coursesList) {
            if (c.getCode().equals(code)) {
                name = c.getName();
                validate = true;
                break;
            } else {
                validate = false;
            }
        }
        if (validate) {
            Course course = new Course(name, code, session);
            RegistrationForm formular = new RegistrationForm(prenom, nom, email, matricule, course);

            try {
                clientSocket = new Socket("127.0.0.1", 1337);
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

                objectOutputStream.writeObject("INSCRIRE");
                objectOutputStream.writeObject(formular);

                //Recevoir la confirmation d'inscription
                System.out.print(objectInputStream.readObject().toString());
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Erreur, le cours choisi n'existe pas dans la liste disponible pour la session. Réessayez.\n");
        }
        return false;
    }
}