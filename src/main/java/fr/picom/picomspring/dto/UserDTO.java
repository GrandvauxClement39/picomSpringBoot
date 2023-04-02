package fr.picom.picomspring.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {
    @NotEmpty(message = "Veuillez renseigner votre nom")
    private String lastName;

    @NotEmpty(message = "Veuillez renseigner votre prénom")
    private String firstName;

    @Email(message = "Veuillez saisir un email correct")
    @NotEmpty(message = "Veuillez renseigner votre email")
    private String email;

    private String password;

    private String phoneNumber;

    @NotEmpty(message = "Veuillez renseigner votre numéro siret")
    @Size(min = 13, max = 15, message = "Votre numéro siret doit avoir 14 chiffres")
    private String numSiret;

    private String companyName;

    private String roadName;

    private String postalCode;

    private String city;

    public UserDTO() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNumSiret() {
        return numSiret;
    }

    public void setNumSiret(String numSiret) {
        this.numSiret = numSiret;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
