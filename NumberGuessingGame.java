import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NumberGuessingGame extends JFrame {

    private int randomNumber;
    private int attempts;
    private int maxAttempts = 5;

    private JTextField guessField;
    private JTextArea feedbackArea;
    private JButton submitButton;
    private JButton newGameButton;
    private JLabel attemptsLabel;

    public NumberGuessingGame() {
        // Set up the JFrame
        super("Number Guessing Game");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 220, 220)); // Background color

        // Generate a random number between 1 and 100
        randomNumber = generateRandomNumber();

        // Create GUI components
        JLabel titleLabel = new JLabel("Number Guessing Game");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));

        guessField = new JTextField(10);
        submitButton = new JButton("Submit Guess");
        newGameButton = new JButton("New Game");
        feedbackArea = new JTextArea();
        feedbackArea.setEditable(false);
        feedbackArea.setFont(new Font("Arial", Font.PLAIN, 14));
        attemptsLabel = new JLabel("Attempts remaining: " + (maxAttempts - attempts));

        // Customize button colors
        submitButton.setBackground(new Color(46, 139, 87)); // Green
        submitButton.setForeground(Color.white);
        newGameButton.setBackground(new Color(70, 130, 180)); // SteelBlue
        newGameButton.setForeground(Color.white);

        // Add components to the JFrame
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setBackground(new Color(220, 220, 220));
        titlePanel.add(titleLabel);
        

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.setBackground(new Color(220, 220, 220));
        inputPanel.add(new JLabel("Your Guess:"));
        inputPanel.add(guessField);
        inputPanel.add(submitButton);
        inputPanel.add(attemptsLabel);
        

        JPanel feedbackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        feedbackPanel.setBackground(new Color(220, 220, 220));
        feedbackPanel.add(feedbackArea);
       

        JPanel newGamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        newGamePanel.setBackground(new Color(220, 220, 220));
        newGamePanel.add(newGameButton);
        
        //title
        add(titlePanel, BorderLayout.NORTH);
        //input
        add(inputPanel, BorderLayout.CENTER);
        //
        add(feedbackPanel, BorderLayout.EAST);
        //
        add(newGamePanel, BorderLayout.SOUTH);
        // Add ActionListener to the submitButton
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkGuess();
            }
        });

        // Add ActionListener to the newGameButton
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // Display initial message
        feedbackArea.append("Guess a number between 1 and 100.\n");

        // Make the JFrame visible
        setVisible(true);
    }

    private int generateRandomNumber() {
        return (int) (Math.random() * 100) + 1;
    }

    private void checkGuess() {
        try {
            int userGuess = Integer.parseInt(guessField.getText());
            attempts++;
            System.out.println(randomNumber);
            if (userGuess == randomNumber) {
                displayResult("Congratulations! You guessed the number in " + attempts + " attempts.");
                new SwingWorker<Void, Void>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        Thread.sleep(15000);
                        return null;
                    }

                    @Override
                    protected void done() {
                        resetGame();
                    }
                }.execute();
            } else if (userGuess < randomNumber) {
                displayResult("Try a higher number");
            } else {
                displayResult("Try a lower number");
            }

            if (attempts >= maxAttempts) {
                displayResult("Sorry! You've reached the maximum number of attempts. The correct number was " + randomNumber + ".");
                resetGame();
            }
        } catch (NumberFormatException ex) {
            displayResult("Invalid input. Please enter a number.");
        } finally {
            attemptsLabel.setText("Attempts remaining: " + (maxAttempts - attempts));
            guessField.setText("");
            guessField.requestFocus();
        }
    }

    private void displayResult(String message) {
        feedbackArea.setText(message);
    }

    private void resetGame() {
        randomNumber = generateRandomNumber();
        attempts = 0;
        displayResult("New game started. Guess a number between 1 and 100.");
        attemptsLabel.setText("Attempts remaining: " + (maxAttempts - attempts));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NumberGuessingGame();
            }
        });
    }
}

