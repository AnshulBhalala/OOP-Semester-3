import java.util.*;

class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;

    Account(String n, String o, long b) {
        accountNumber = n;
        ownerName = o;
        balance = b;
    }

    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    public boolean equals(Object o) {
        return o instanceof Account a && accountNumber.equals(a.accountNumber);
    }

    public int hashCode() {
        return accountNumber.hashCode();
    }
}

class Customer implements Cloneable {
    private String name;
    private Address address;

    Customer(String n, Address a) {
        name = n;
        address = a;
    }

    public Address getAddress() {
        return address;
    }

    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    static class Address {
        private String line, city, pincode;

        Address(String l, String c, String p) {
            line = l;
            city = c;
            pincode = p;
        }

        public String getCity() {
            return city;
        }
    }
}

public class MiniBank {
    public static void main(String[] args) {
        Account a1 = new Account("AC0001", "Riya", 4000), a2 = new Account("AC0001", "Riya", 4000);
        System.out.println(a1);
        System.out.println("Equal: " + a1.equals(a2));
        System.out.println("Is Account: " + (a1 instanceof Account));
    }
}
