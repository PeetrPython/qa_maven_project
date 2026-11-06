package models;

import exceptions.TestDataException;

public class Customer {

    private final String firstName;
    private final String lastName;
    private final String postalCode;

    private Customer(String firstName, String lastName, String postalCode) {
        if (firstName == null) {
            throw new TestDataException("firstName не может быть null");
        }

        if (lastName == null) {
            throw new TestDataException("lastName не может быть null");
        }

        if (postalCode == null) {
            throw new TestDataException("postalCode не может быть null");
        }

        this.firstName = firstName;
        this.lastName = lastName;
        this.postalCode = postalCode;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String firstName;
        private String lastName;
        private String postalCode;

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder postalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Customer build() {
            return new Customer(firstName,lastName,postalCode);
        }
    }


    public String getFirstName() {return firstName;}

    public String getLastName(){
        return lastName;
    }

    public String getPostalCode(){
        return postalCode;
    }

}
