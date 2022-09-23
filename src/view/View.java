package view;

import java.util.Scanner;

public class View {
    private Scanner console;

    public View() {
        console = new Scanner(System.in);
    }

    public void showMessage(String message){
        System.out.println(message);
    }

    public int readInt(String message) {
        System.out.println(message);
        return console.nextInt();
    }
}
