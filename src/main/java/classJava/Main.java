package classJava;

import java.util.Scanner;

import view.PrincipalView;


/**
 * Classe Main.
 */
public class Main {

  /**
   * Méthode main.
   *
   * @param args
   */
  public static void main(String[] args) {
    Scanner myObj = new Scanner(System.in);
    System.out.println("Entrer votre mot de passe : ");
    String pass = myObj.nextLine();
    new PrincipalView(pass);
  }
}
