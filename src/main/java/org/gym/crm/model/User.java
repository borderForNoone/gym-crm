package org.gym.crm.model;

import java.util.Objects;

public abstract class User {
    private final String firstName;
    private final String lastName;
    private final String username;
    private final String password;
    private final boolean isActive;

    protected User(Builder<?> builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.username = builder.username;
        this.password = builder.password;
        this.isActive = builder.isActive;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return isActive;
    }

    public abstract static class Builder<T extends Builder<T>> {
        private String firstName;
        private String lastName;
        private String username;
        private String password;
        private boolean isActive;

        @SuppressWarnings("unchecked")
        public T firstName(String firstName) {
            this.firstName = firstName;
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        public T lastName(String lastName) {
            this.lastName = lastName;
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        public T username(String username) {
            this.username = username;
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        public T password(String password) {
            this.password = password;
            return (T) this;
        }

        @SuppressWarnings("unchecked")
        public T isActive(boolean isActive) {
            this.isActive = isActive;
            return (T) this;
        }

        public abstract User build();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isActive == user.isActive && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password, isActive);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
