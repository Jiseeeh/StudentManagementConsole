package Prompt;

import Abstract.ImplementingClasses.Student;
import Abstract.ImplementingClasses.Teacher;
import Abstract.User;
import Database.AccountsDB;
import Helper.InputHelper;
import Views.AdminView;
import Views.StudentView;
import Views.TeacherView;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class StudentSystem {
    private static final Scanner scan = new Scanner(System.in);
    private static final AccountsDB accounts = AccountsDB.INSTANCE;
    private static final List<User> users = accounts.getUsers();

    public static void prompt() {
        while (true) {
            System.out.println("""
                    \nWhat do you want to do?
                    1 -> login
                    2 -> exit
                    """);

            System.out.print(": ");
            String input = scan.nextLine().trim();

            if (InputHelper.hasLetterInput(input)) continue;

            int choice = Integer.parseInt(input);

            if (choice == 1) login();
            else if (choice == 2) return;
            else {
                System.out.println("""
                                                
                        |-------------------|
                        |* Invalid input!  *|
                        |-------------------|
                        """);
            }
        }
    }

    public static void login() {
        System.out.print("\nEnter your username: ");
        String username = scan.nextLine();
        System.out.print("Enter your password: ");
        String password = scan.nextLine();

        User account = null;
        String accountType = "";

        for (var user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                account = user;
                accountType = account.getType();
                break;
            }
        }
        switch (accountType) {
            case "admin" -> AdminView.show();
            case "student" -> StudentView.show((Student) account);
            case "teacher" -> TeacherView.show((Teacher) account);
            default -> {
                System.out.println("""
                                                
                        |-------------------|
                        |*Account not Found*|
                        |-------------------|
                        """);
                StudentSystem.prompt();
            }
        }
    }

    public static void loadAccounts() {
        try {
            accounts.hasAccounts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
