public class ClientSimple {
    private Socker clientSocket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private String sessionChoice = "Veuillez choisir la session pour laquelle vous voulez consulter la liste des cours:\n1. Automne\n2. Hiver\n 3. Ete\n> Choix:";
    private String decision = "Que souhaitez-vous faire ?\n1. Consulter les cours offerts pour une autre session\n2. Inscription à un cours\n> Choix:";
    private static ArrayList<Course> coursesList;
    public static void main(String[] args) {
        try {
            clientSocket = new Socket("127.0.0.1", 1337);
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            objectOutputStream = new ObjectOutputStream(clienSocket.getOutputStream());

            Scanner scan = new Scanner(System.in);
            System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
            System.out.print(sessionChoice);
            load(scan.nextInt());


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void load(int choice) {
        String session;
        if (choice == 1) {
            session = "Automne";
        } else if (choice == 2) {
            session = "Hiver";
        } else if (choice == 3) {
            session = "Ete";
        }
        objectOutputStream.writeObject("CHARGER " + session);

        objectOutputStream.close();

        System.out.println("Les cours offerts pendant la session d'" + session.lower() + " sont:");
        coursesList = objectInputStream.readObject();
        int j = 0;
        for (int i = 0; coursesList.size(); i++) {
            j++;
            System.out.println(j + ". " + coursesList.get(i).code + "\t" + course.name);
        }
        System.out.print(decision);
        decisionHandler(scan.nextInt(), session);
    }

    public static void decisionHandler(int choice, String session) {
        if (choice == 1) {
            System.out.print(sessionChoice);
            load(scan.nextInt());
        } else if (choice == 2) {
            inscription(String session);
        }
    }
    public static void inscription(String session){
        System.out.println("Entrez votre prénom : ");
        String prenom = scanner.nextLine();
        System.out.println("Entrez votre nom : ");
        String nom = scanner.nextLine();
        System.out.println("Entrez votre email (étudiant) : ");
        String email = scanner.nextLine();
        System.out.println("Entrez votre matricule : ");
        String matricule = scanner.nextLine();
        System.out.println("Entrez le code du cours : ");
        String codeCours = scanner.nextLine();
        Course course = new Course(name, codeCours, session);

        boolean validate = true;
        for (Course c : coursesList) {
            if (c.code == codeCours) {
                validate = true;
                break;
            } else {
                validate = false;
            }
        }
        if (validate) {
            RegistrationForm formular = new RegistrationForm(prenom, nom, email, matricule, course);

            objectOutputStream.writeObject("Inscription "+ formular);
        } else {
            System.out.println("Erreur, le cours choisi n'existe pas dans la liste disponible pour la session.");
        }





        //Recevoir la reponse du serveur
        String reponse = objectInputStream.readUTF();
        System.out.println(reponse);
    }
}
