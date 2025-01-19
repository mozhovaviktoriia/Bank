package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Патерн Builder для створення команд
class CommandBuilder {
    private Account account;
    private double amount;
    private Command command;

    public CommandBuilder setAccount(Account account) {
        this.account = account;
        return this;
    }

    public CommandBuilder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public CommandBuilder deposit() {
        this.command = new DepositCommand(account, amount);
        return this;
    }

    public CommandBuilder withdraw() {
        this.command = new WithdrawCommand(account, amount);
        return this;
    }

    public CommandBuilder display() {
        this.command = new DisplayAccountCommand(account);
        return this;
    }

    public Command build() {
        if (command == null) {
            throw new IllegalStateException("Command is not set!");
        }
        return this.command;
    }
}

// Командний інтерфейс
interface Command {
    void execute();
}

// Базовий клас для складених команд
class CompositeCommand implements Command {
    private final List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        commands.add(command);
    }

    @Override
    public void execute() {
        for (Command command : commands) {
            command.execute();
        }
    }
}

// Конкретна команда: Депозит
class DepositCommand implements Command {
    private final Account account;
    private final double amount;

    public DepositCommand(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        account.deposit(amount);
    }
}

// Конкретна команда: Зняття коштів
class WithdrawCommand implements Command {
    private final Account account;
    private final double amount;

    public WithdrawCommand(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    @Override
    public void execute() {
        account.withdraw(amount);
    }
}

// Конкретна команда: Відображення інформації про рахунок
class DisplayAccountCommand implements Command {
    private final Account account;

    public DisplayAccountCommand(Account account) {
        this.account = account;
    }

    @Override
    public void execute() {
        System.out.println(account);
    }
}

// Інформаційний об'єкт User
class User {
    private String username;
    private String email;
    private String phoneNumber;
    private String address;

    // Конструктор для ініціалізації користувача
    private User(UserBuilder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
    }

    // Статичний клас для побудови об'єкта User (Builder)
    public static class UserBuilder {
        private String username;
        private String email;
        private String phoneNumber;
        private String address;

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    // Getter методи
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    // Setter методи для зміни даних користувача
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
// Shell для виконання команд
public class Shell {
    private static final Logger logger = LogManager.getLogger(Shell.class);

    public static void main(String[] args) {
        logger.info("Starting Shell application");

        // Створення клієнта та рахунку
        Customer customer = new Customer("John Doe", "C001");
        Account account = new Account("A001", 1000.0, customer);

        // Створення об'єкта User через Builder
        User user = new User.UserBuilder()
                .setUsername("johndoe")
                .setEmail("johndoe@example.com")
                .setPhoneNumber("123-456-7890")
                .setAddress("123 Main St")
                .build();

        System.out.println("Created User: " + user);

        // Список користувачів
        List<User> users = new ArrayList<>();
        users.add(user);

        // Консольний інтерфейс
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Bank Shell ---");
            System.out.println("1. Display Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Change User Info");
            System.out.println("5. Select User");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Споживаємо зайвий символ нового рядка після nextInt()
            Command command = null;

            switch (choice) {
                case 1 -> {
                    command = new CommandBuilder().setAccount(account).display().build();
                    command.execute();
                }
                case 2 -> {
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Споживаємо зайвий символ нового рядка після nextDouble()
                    command = new CommandBuilder().setAccount(account).setAmount(depositAmount).deposit().build();
                    command.execute();
                }
                case 3 -> {
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Споживаємо зайвий символ нового рядка після nextDouble()
                    command = new CommandBuilder().setAccount(account).setAmount(withdrawAmount).withdraw().build();
                    command.execute();
                }
                case 4 -> {
                    // Change user details
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new email address: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhone = scanner.nextLine();
                    System.out.print("Enter new address: ");
                    String newAddress = scanner.nextLine();

                    System.out.print("Enter user number to update: ");
                    int userChoice = scanner.nextInt();
                    scanner.nextLine();  // Consume newline character

                    // Check if the chosen user number is valid
                    if (userChoice > 0 && userChoice <= users.size()) {
                        User selectedUser = users.get(userChoice - 1);
                        selectedUser.setUsername(newUsername);
                        selectedUser.setEmail(newEmail);
                        selectedUser.setPhoneNumber(newPhone);
                        selectedUser.setAddress(newAddress);
                        System.out.println("Updated user: " + selectedUser);
                    } else {
                        System.out.println("Invalid user selection.");
                    }
                }

                case 5 -> {
                    // Select a user
                    System.out.println("Select a user:");
                    for (int i = 0; i < users.size(); i++) {
                        System.out.println((i + 1) + ". " + users.get(i).getUsername());
                    }

                    System.out.print("Enter user number: ");
                    int userChoice = scanner.nextInt();
                    scanner.nextLine();  // Consume newline character

                    // Check if the chosen user number is valid
                    if (userChoice > 0 && userChoice <= users.size()) {
                        User selectedUser = users.get(userChoice - 1);
                        System.out.println("Selected User: " + selectedUser);
                    } else {
                        System.out.println("Invalid user selection.");
                    }
                }

                case 6 -> {
                    // Add a new user
                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine();
                    System.out.print("Enter new email address: ");
                    String newEmail = scanner.nextLine();
                    System.out.print("Enter new phone number: ");
                    String newPhone = scanner.nextLine();
                    System.out.print("Enter new address: ");
                    String newAddress = scanner.nextLine();

                    User newUser = new User.UserBuilder()
                            .setUsername(newUsername)
                            .setEmail(newEmail)
                            .setPhoneNumber(newPhone)
                            .setAddress(newAddress)
                            .build();

                    users.add(newUser); // Add the new user to the list
                    System.out.println("New User added: " + newUser);
                }

            }
        }
    }
}

