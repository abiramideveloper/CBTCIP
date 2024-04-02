import javax.swing.JOptionPane;
import java.util.Random;

public class guessthenumber {
    public static void main(String[] args) {
        Random random = new Random();
        int min = 1;
        int max = 100;
        int totalAttempts = 0;
        int totalScore = 0;
        int rounds = 3;
        int maxAttemptsPerRound = 5;

        JOptionPane.showMessageDialog(null, "Welcome to Guess the Number!");

        for (int round = 1; round <= rounds; round++) {
            int attempts = 0;

            JOptionPane.showMessageDialog(null, "Round " + round + ":\nI have chosen a number between " + min + " and " + max + ". Try to guess it!");

            int randomNumber = random.nextInt(max - min + 1) + min;

            while (attempts < maxAttemptsPerRound) {
                String guessStr = JOptionPane.showInputDialog("Enter your guess:");
                int guess = Integer.parseInt(guessStr);
                attempts++;
                totalAttempts++;

                if (guess == randomNumber) {
                    int score = calculateScore(attempts);
                    JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number in " + attempts + " attempts.\nYour score for this round is: " + score);
                    totalScore += score;
                    break;
                } else if (guess < randomNumber) {
                    JOptionPane.showMessageDialog(null, "Too low. Try again!");
                } else {
                    JOptionPane.showMessageDialog(null, "Too high. Try again!");
                }
            }

            if (attempts >= maxAttemptsPerRound) {
                JOptionPane.showMessageDialog(null, "You've reached the maximum number of attempts. The number was: " + randomNumber + "\nYour score for this round is: 0");
            }
        }

        JOptionPane.showMessageDialog(null, "Game Over!\nTotal score: " + totalScore + "\nTotal attempts: " + totalAttempts);
    }

    public static int calculateScore(int attempts) {
        int baseScore = 100;
        int maxAttempts = 5;
        int maxScore = 50; // Minimum score
        int scoreIncrement = baseScore / maxAttempts;
        int score = baseScore - (attempts - 1) * scoreIncrement;
        return Math.max(score, maxScore);
    }
}