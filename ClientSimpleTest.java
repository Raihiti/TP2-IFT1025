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
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;
    private static String sessionChoice = "Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:\n1. Automne\n2. Hiver\n 3. Ete\n> Choix:";
    private static final String decision = "Que souhaitez-vous faire ?\n1. Consulter les cours offerts pour une autre session\n2. Inscription à un cours\n> Choix:";
    private static ArrayList<Course> coursesList;
    private static final Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            clientSocket = new Socket("127.0.0.1", 1337);
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
            System.out.print(sessionChoice);
            load(scan.nextInt());


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void load(int choice) throws IOException, ClassNotFoundException {
        String session = "";
        if (choice == 1) {
            session = "Automne";
        } else if (choice == 2) {
            session = "Hiver";
        } else if (choice == 3) {
            session = "Ete";
        }
        objectOutputStream.writeObject("CHARGER " + session);

        try {
            objectOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Les cours offerts pendant la session d'" + session.toLowerCase() + " sont:");
        coursesList = objectInputStream.readObject();
        int j = 0;
        for (int i = 0; coursesList.size() - 1 >= i; i++) {
            j++;
            System.out.println(j + ". " + coursesList.get(i).getCode() + "\t" + coursesList.get(i).getName());
        }
        System.out.println(decision);
        decisionHandler(scan.nextInt(), session);
    }

    public static void decisionHandler(int choice, String session) throws IOException, ClassNotFoundException {
        if (choice == 1) {
            System.out.print(sessionChoice);
            load(scan.nextInt());
        } else if (choice == 2) {
            inscription(session);
        }
    }
    public static void inscription(String session) throws IOException {
        System.out.println("Entrez votre prénom : ");
        String prenom = scan.nextLine();
        System.out.println("Entrez votre nom : ");
        String nom = scan.nextLine();
        System.out.println("Entrez votre email (étudiant) : ");
        String email = scan.nextLine();
        System.out.println("Entrez votre matricule : ");
        String matricule = scan.nextLine();
        System.out.println("Entrez le code du cours : ");
        String codeCours = scan.nextLine();

        boolean validate = true;
        for (Course c : coursesList) {
            if (c.getCode() == codeCours) {
                String name = c.getName();
                validate = true;
                break;
            } else {
                validate = false;
            }
        }
        if (validate) {
            Course course = new Course(name, codeCours, session);
            RegistrationForm formular = new RegistrationForm(prenom, nom, email, matricule, course);

            try {
                objectOutputStream.writeObject("Inscription "+ formular);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Erreur, le cours choisi n'existe pas dans la liste disponible pour la session.");
        }





        //Recevoir la reponse du serveur
        String reponse = objectInputStream.readUTF();
        System.out.println(reponse);
    }
}
