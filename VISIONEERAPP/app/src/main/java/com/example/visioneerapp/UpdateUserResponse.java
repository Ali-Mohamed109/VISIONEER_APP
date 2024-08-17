package com.example.visioneerapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UpdateUserResponse {
    @SerializedName("message")
    private User message;

    public User getMessage() {
        return message;
    }

    public void setMessage(User message) {
        this.message = message;
    }

    public static class User {
        @SerializedName("_id")
        private String id;
        @SerializedName("email")
        private String email;
        @SerializedName("username")
        private String username;
        @SerializedName("gender")
        private String gender;
        @SerializedName("ai_image")
        private List<String> aiImage;
        @SerializedName("isAdmin")
        private boolean isAdmin;
        @SerializedName("createdAt")
        private String createdAt;
        @SerializedName("updatedAt")
        private String updatedAt;
        @SerializedName("__v")
        private int version;

        // Add getters and setters here


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public List<String> getAiImage() {
            return aiImage;
        }

        public void setAiImage(List<String> aiImage) {
            this.aiImage = aiImage;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}
