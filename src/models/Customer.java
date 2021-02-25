package models;

import java.util.Objects;

public class Customer {
    	private int id;
    	private String first_name;
    	private String last_name;
    	private String address;
    	private String phone_number;

    public Customer() {
    }

    public Customer(int id, String first_name, String last_name, String adress, String phone_number) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = adress;
        this.phone_number = phone_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return first_name + " " + last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer klijent = (Customer) o;
        return Objects.equals(first_name, klijent.first_name) &&
                Objects.equals(last_name, klijent.last_name) &&
                Objects.equals(address, klijent.address) &&
                Objects.equals(phone_number, klijent.phone_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first_name, last_name, address, phone_number);
    }
}
