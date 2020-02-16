package net.class101.server1.console;

import java.util.Scanner;

public class OrderInputView {

    private final Scanner scanner;

    public OrderInputView() {
        scanner = new Scanner(System.in);
    }

    public String inputTopMenu() {
        return scanner.nextLine().trim();
    }

    public String inputItemId() {
        return scanner.nextLine().trim();
    }

    public String inputItemAmount() {
        return scanner.nextLine().trim();
    }
}
