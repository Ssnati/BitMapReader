package view;

import java.util.Scanner;

public class View {
    private Scanner scanner;

    public View() {
        scanner = new Scanner(System.in);
    }

    public void showMessage(String message){
        System.out.println(message);
    }
}
