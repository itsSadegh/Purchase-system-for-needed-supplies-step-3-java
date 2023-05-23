package ut.ie.baloot3.models;

import java.util.ArrayList;
import java.util.HashSet;

public class User {
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", address='" + address + '\'' +
                ", credit=" + credit +
                '}';
    }

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public String getBirthDate() { return birthDate; }

    public String getAddress() { return address; }

    public int getCredit() { return credit; }

    public void addCredit(int credit) {
        this.credit += credit;
    }

    public boolean doesItemExistInBuyList(int commodityId) { return buyList.contains(commodityId); }

    public void addToBuyList(int commodityId) {
        buyList.add(commodityId);
    }

    public void addToPurchasedList(int commodityId) {
        purchasedList.add(commodityId);
    }

    public void removeFromBuyList(int commodityId) {
        buyList.remove(commodityId);
    }

    public HashSet<Integer> getBuyList() { return buyList; }

    public void emptyBuyList() {
        buyList = new HashSet<>();
    }

    public ArrayList<Integer> getPurchasedList() { return purchasedList; }

    public boolean isPasswordCorrect(String password) {
        return password.equalsIgnoreCase(this.password);
    }

    public int getActiveDiscountPercentage() {
        return activeDiscountPercentage;
    }

    public void setActiveDiscountPercentage(int activeDiscountPercentage) {
        this.activeDiscountPercentage = activeDiscountPercentage;
    }

    public void addToUsedDiscountCodes(String code) {
        usedDiscountCodes.add(code);
    }

    boolean isDiscountCodeUsed(String code) {
        return usedDiscountCodes.contains(code);
    }

    public String getCurrentDiscountCode() {
        return currentDiscountCode;
    }

    public void setCurrentDiscountCode(String currentDiscountCode) {
        this.currentDiscountCode = currentDiscountCode;
    }

    private String username;

    private String password;

    private String email;

    private String birthDate;

    private String address;

    private int credit;

    private int activeDiscountPercentage = 0;

    private String currentDiscountCode = null;

    private HashSet<Integer> buyList = new HashSet<Integer>();

    private ArrayList<Integer> purchasedList = new ArrayList<>();

    private HashSet<String> usedDiscountCodes = new HashSet<String>();
}
