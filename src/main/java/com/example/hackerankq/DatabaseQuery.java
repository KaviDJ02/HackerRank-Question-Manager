package com.example.hackerankq;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQuery {

    static DateTimeNow dateTimeNow = new DateTimeNow();

    /*public static void updateQuestion(String oldTitle, Question updatedQuestion) {
        String query = "UPDATE Questions SET title = ?, difficulty = ?, category = ?, status = ? WHERE title = ?";
        try (PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(query)) {
            stmt.setString(1, updatedQuestion.getTitle());
            stmt.setString(2, updatedQuestion.getDifficulty());
            stmt.setString(3, updatedQuestion.getSubdomain());
            stmt.setString(4, updatedQuestion.getStatus());
            stmt.setString(5, oldTitle);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    public static boolean validateUser(String username, String password) throws SQLException {
        if (DatabaseHandler.getInstance().getConnection() == null) {
            System.err.println("Database connection is not established.");
            return false;
        }

        String query = "SELECT COUNT(1) FROM Users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addQuestion(Question question) {
        String query = "INSERT INTO Questions (title, difficulty, subdomain, status, dateSolved, solvedBy) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(query)) {
            stmt.setString(1, question.getTitle());
            stmt.setString(2, question.getDifficulty());
            stmt.setString(3, question.getSubdomain());
            stmt.setString(4, question.getStatus());
            stmt.setString(5, question.getDateSolved());
            stmt.setString(6, question.getSolvedBy());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM Questions";
        try (Statement stmt = DatabaseHandler.getInstance().getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                questions.add(new Question(
                        rs.getString("title"),
                        rs.getString("difficulty"),
                        rs.getString("subdomain"),
                        rs.getString("status"),
                        rs.getString("dateSolved"),
                        rs.getString("solvedBy")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    /*public void markAsSolved(int questionID) {
        String query = "UPDATE Questions SET status = 'completed' WHERE questionID = ?";
        try (PreparedStatement stmt = DatabaseHandler.getInstance().getConnection().prepareStatement(query)) {
            stmt.setInt(1, questionID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}