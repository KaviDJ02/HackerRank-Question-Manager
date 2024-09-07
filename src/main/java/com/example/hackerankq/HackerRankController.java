package com.example.hackerankq;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.SQLException;

import static com.example.hackerankq.DatabaseQuery.dateTimeNow;

public class HackerRankController {

    // FXML UI elements
    @FXML
    private AnchorPane loginPane;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginStatusLabel;
    @FXML
    private Label msgLabel;
    @FXML
    private AnchorPane managerPane;
    @FXML
    private TextField questionTitle;
    @FXML
    private ChoiceBox<String> difficultyChoice;
    @FXML
    private TextField subdomain;
    @FXML
    private ChoiceBox<String> solvedStatusChoice;
    @FXML
    private ListView<String> solvedQuestionsList;

    DatabaseQuery databaseQuery = new DatabaseQuery();

    public static String user;

    // Initialize the controller
    @FXML
    private void initialize() throws SQLException {
        //difficultyChoice.getItems().addAll("Easy", "Medium", "Hard");
        //solvedStatusChoice.getItems().addAll("Solved", "Attempted", "Not Solved");

        // Initially show login pane and hide manager pane
        loginPane.setVisible(true);
        managerPane.setVisible(false);

        loadSolvedQuestions();
    }

    // Handle login functionality
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            if (databaseQuery.validateUser(username, password)) {
                loginStatusLabel.setText("Login successful!");
                loginPane.setVisible(false);
                managerPane.setVisible(true);
                user = username;
            } else {
                loginStatusLabel.setText("Invalid credentials. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            loginStatusLabel.setText("An error occurred. Please try again.");
        }
    }

    // Handle adding a new question
    @FXML
    private void handleAddQuestion() throws SQLException {
        String title = questionTitle.getText();
        String difficulty = difficultyChoice.getValue();
        String subdomainText = subdomain.getText();
        String solvedStatus = solvedStatusChoice.getValue();
        String solvedBy = user;

        if (title == null || title.trim().isEmpty() ||
                difficulty == null || difficulty.trim().isEmpty() ||
                subdomainText == null || subdomainText.trim().isEmpty() ||
                solvedStatus == null || solvedStatus.trim().isEmpty()) {
            msgLabel.setText("Fields cannot be empty!");
            return;
        }

        if (checkDuplicate(title)) {
            msgLabel.setText("Question already exists!");
            return;
        }

        Question question = new Question(title, difficulty, subdomainText, solvedStatus, dateTimeNow.getCurrentDateTime(), solvedBy);

        // Save the question to the database
        DatabaseQuery.addQuestion(question);
        msgLabel.setText("Question added successfully!");

        // Refresh the ListView for solved questions
        loadSolvedQuestions();

        clearForm();
    }

    private boolean checkDuplicate(String title) {
        for (Question question : databaseQuery.getQuestions()) {
            if (title.equals(question.getTitle())) {
                return true;
            }
        }
        return false;
    }

    // Handle updating a question (logic will depend on how you're fetching and managing questions)
    @FXML
    private void handleUpdateQuestion() throws SQLException {
        // Get the selected question from the ListView
        String selectedQuestion = solvedQuestionsList.getSelectionModel().getSelectedItem();
        if (selectedQuestion == null) {
            System.out.println("No question selected for update.");
            return;
        }

        // Extract the question ID or unique identifier (assuming it's part of the string)
        // This part depends on how you display the question in the ListView
        // For simplicity, let's assume the title is unique and can be used to identify the question
        String title = selectedQuestion.split(" \\[")[0];

        // Get the updated details from the form
        String newTitle = questionTitle.getText();
        String newDifficulty = difficultyChoice.getValue();
        String newSubdomain = subdomain.getText();
        String newSolvedStatus = solvedStatusChoice.getValue();

        if (newTitle != null && newDifficulty != null && newSubdomain != null && newSolvedStatus != null) {
            Question updatedQuestion = new Question(newTitle, newDifficulty, newSubdomain, newSolvedStatus, dateTimeNow.DateTime(),user);

            // Update the question in the database
            //DatabaseQuery.updateQuestion(title, updatedQuestion);

            // Refresh the ListView
            solvedQuestionsList.getItems().clear();
            for (Question question : databaseQuery.getQuestions()) {
                if ("Solved".equals(question.getStatus())) {
                    solvedQuestionsList.getItems().add(question.toString());
                }
            }

            clearForm();
        } else {
            System.out.println("Please fill all fields before updating the question.");
        }
    }

    // Method to load solved questions from the database
    private void loadSolvedQuestions() throws SQLException {
        solvedQuestionsList.getItems().clear();
        for (Question question : databaseQuery.getQuestions()) {
            if ("Solved".equals(question.getStatus())) {
                solvedQuestionsList.getItems().add(question.toString());
            }
        }
    }

    // Clear the form after adding a question
    private void clearForm() {
        questionTitle.clear();
        difficultyChoice.setValue(null);
        subdomain.clear();
        solvedStatusChoice.setValue(null);
    }
}