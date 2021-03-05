package com.rest.controller.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private String title;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String phoneNumber;
    private String dob;
    private String gender;

    public User(){};



    public User(String userName, String password, String title, String firstName,
                String lastName, String emailAddress, String phoneNumber, String dob, String gender) {
        this.userName = userName;
        this.password = password;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.dob = dob;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return Objects.equals(this.id, user.id) && Objects.equals(this.userName, user.userName)
                && Objects.equals(this.firstName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.firstName, this.lastName);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + this.id + ", username='" + this.userName + '\'' + ", title='" + this.title
                + ", firstName='" + this.firstName + '\''
                + ", lastName='" + this.lastName + '\''
                + ", emailAddress='" + this.emailAddress + '\''
                + ", phoneNumber='" + this.phoneNumber + '\''
                + ", dob='" + this.dob + '\''
                + ", gender='" + this.gender + '\''
                + '}';
    }
}


