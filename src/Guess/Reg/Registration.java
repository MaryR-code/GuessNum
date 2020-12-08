package Guess.Reg;

import java.util.ArrayList;
import java.util.Scanner;

public class Registration {
    public static void main(String[] args) {

    //    RegistrationForm[] regs = new RegistrationForm[100];  // массив из объектов (неудобно)

        ArrayList<RegistrationForm> regs = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("New registration");
            RegistrationForm frm = new RegistrationForm();

            System.out.println("Name: ");
            frm.fullName = scanner.next();

            System.out.println("Age: ");
            frm.age = scanner.nextInt();

            System.out.println("Phone: ");
            frm.phone = scanner.next();

            regs.add(frm);

            System.out.println("Add more?");
            if (scanner.next().equals("no")) {
                break;
            }
        }

        for (RegistrationForm reg : regs) {
            System.out.printf("name: %s, age: %d, phone: %s \n", reg.fullName, reg.age, reg.phone);
        }
    }
}
