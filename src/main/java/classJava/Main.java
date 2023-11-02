package classJava;

import view.PrincipalView;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Entrer votre mot de passe : ");
        String pass = myObj.nextLine();
        ConnectDB conn = new ConnectDB(pass);
        new PrincipalView();
    }
}
