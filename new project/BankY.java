import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Bank {
    private List<Account> accounts;
    private final String dataFilePath = "accounts.txt";

    public Bank() {
        this.accounts = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        try {
            File file = new File(dataFilePath);
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",");
                    String accountNumber = parts[0];
                    String name = parts[1];
                    double balance = Double.parseDouble(parts[2]);
                    accounts.add(new Account(accountNumber, name, balance));
                }
                scanner.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void saveData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilePath));
            for (Account account : accounts) {
                writer.write(account.getAccountNumber() + "," + account.getName() + "," + account.getBalance());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccount(String accountNumber, String name, double balance) {
        if (!accountExists(accountNumber)) {
            accounts.add(new Account(accountNumber, name, balance));
            saveData();
            System.out.println("Account created successfully for " + name + " with account number " + accountNumber + ".");
        } else {
            System.out.println("Account number already exists.");
        }
    }

    private boolean accountExists(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return true;
            }
        }
        return false;
    }

    public void deposit(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            saveData();
            System.out.println("Deposited " + amount + " into account " + accountNumber + ".");
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            if (account.getBalance() >= amount) {
                account.withdraw(amount);
                saveData();
                System.out.println("Withdrew " + amount + " from account " + accountNumber + ".");
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transfer(String fromAccount, String toAccount, double amount) {
        Account from = getAccount(fromAccount);
        Account to = getAccount(toAccount);
        if (from != null && to != null) {
            if (from.getBalance() >= amount) {
                from.withdraw(amount);
                to.deposit(amount);
                saveData();
                System.out.println("Transferred " + amount + " from account " + fromAccount + " to account " + toAccount + ".");
            } else {
                System.out.println("Insufficient funds.");
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    public void displayBalance(String accountNumber) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            System.out.println("Balance for account " + accountNumber + ": " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    private Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
}

class Account {
    private String accountNumber;
    private String name;
    private double balance;

    public Account(String accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}

public class BankY {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        while (running) {
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Display Balance");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    System.out.print("Enter account number: ");
                    String accNum = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double balance = scanner.nextDouble();
                    bank.createAccount(accNum, name, balance);
                    break;
                case 2:
                    System.out.print("Enter account number: ");
                    String depositAccNum = scanner.nextLine();
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();
                    bank.deposit(depositAccNum, depositAmount);
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    String withdrawAccNum = scanner.nextLine();
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawAmount = scanner.nextDouble();
                    bank.withdraw(withdrawAccNum, withdrawAmount);
                    break;
                case 4:
                    System.out.print("Enter from account number: ");
                    String fromAccNum = scanner.nextLine();
                    System.out.print("Enter to account number: ");
                    String toAccNum = scanner.nextLine();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = scanner.nextDouble();
                    bank.transfer(fromAccNum, toAccNum, transferAmount);
                    break;
                case 5:
                    System.out.print("Enter account number: ");
                    String displayAccNum = scanner.nextLine();
                    bank.displayBalance(displayAccNum);
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }
}
