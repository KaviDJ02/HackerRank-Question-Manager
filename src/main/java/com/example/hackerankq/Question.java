package com.example.hackerankq;

public class Question {

    private String title;
    private String difficulty;
    private String subdomain;
    private String status;
    private String dateSolved;
    private String solvedBy;



    // Constructor
    public Question(String title, String difficulty, String subdomain, String status, String dateSolved, String solvedBy) {
        this.title = title;
        this.difficulty = difficulty;
        this.subdomain = subdomain;
        this.status = status;
        this.solvedBy = solvedBy;

        this.dateSolved = dateSolved;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getSubdomain() {
        return subdomain;
    }

    public void setSubdomain(String subdomain) {
        this.subdomain = subdomain;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSolvedBy() {
        return solvedBy;
    }

    public void setSolvedBy(String solvedBy) {
        this.solvedBy = solvedBy;
    }

    public String getDateSolved() {
        return dateSolved;
    }

    public void setDateSolved(String dateSolved) {
        this.dateSolved = dateSolved;
    }

    // Override toString to display the question in the ListView
    @Override
    public String toString() {
        return String.format("Title: %s [ %s ] , Solved By: %s at %s" ,
                title, difficulty, solvedBy, dateSolved);
    }
}
