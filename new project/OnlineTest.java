import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class OnlineTest {
    private static String[] questions = {
        "1. What is the capital of France?",
        "2. Who wrote 'Romeo and Juliet'?",
        "3. What is the chemical symbol for water?",
        "4. Which planet is known as the Red Planet?"
    };

    private static String[][] options = {
        {"A. London", "B. Paris", "C. Rome", "D. Madrid"},
        {"A. William Shakespeare", "B. Jane Austen", "C. Charles Dickens", "D. Mark Twain"},
        {"A. H2O", "B. CO2", "C. NaCl", "D. O2"},
        {"A. Mars", "B. Jupiter", "C. Earth", "D. Venus"}
    };

    private static char[] answers = {'B', 'A', 'A', 'A'};
    private static char[] userAnswers;
    private static Timer timer;
    private static boolean timeUp = false;
    private static boolean loggedIn = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Online Test!");

        // Login
        login(scanner);

        if (!loggedIn) {
            System.out.println("Login failed. Exiting...");
            return;
        }

        userAnswers = new char[questions.length];

        // Display questions and options
        for (int i = 0; i < questions.length; i++) {
            System.out.println(questions[i]);
            for (String option : options[i]) {
                System.out.println(option);
            }
            System.out.print("Your answer: ");
            userAnswers[i] = scanner.next().toUpperCase().charAt(0);
        }

        // Start the timer
        startTimer();

        // Wait for the timer to expire or user to finish
        while (!timeUp) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Automatically submit answers when time is up
        submitAnswers();

        // Logout
        logout();
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        // Dummy login validation
        if ("user123".equals(username) && "password".equals(password)) {
            System.out.println("Login successful.");
            loggedIn = true;
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("Time's up! Automatically submitting answers...");
                timeUp = true;
                timer.cancel();
            }
        }, 60000); // 1 minute timer
    }

    private static void submitAnswers() {
        System.out.println("Answers submitted.");
    }

    private static void logout() {
        System.out.println("Logout successful.");
    }
}