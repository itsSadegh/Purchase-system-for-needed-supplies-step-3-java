package ut.ie.baloot3.models;

import java.util.HashSet;
import java.util.HashMap;

public class Commodity {
    @Override
    public String toString() {
        return "Commodity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", providerId=" + providerId +
                ", price=" + price +
                ", categories=" + categories +
                ", rating=" + rating +
                ", inStock=" + inStock +
                '}';
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getProviderId() { return providerId; }

    public int getInStock() { return inStock; }

    public int getPrice() { return price; }

    public String getCategories() {
        return categories.toString().substring(1, categories.toString().length() - 1);
    }

    public HashSet<String> getCategoriesHashset() { return categories; }

    public float getRating() { return rating; }

    public void addOrModifyRating(String username, int userRating) {
        float sumOfRatings = rating * userRatings.size();
        if (userRatings.containsKey(username)) {
            sumOfRatings += userRating - userRatings.get(username);
            userRatings.replace(username, userRating);
        } else {
            sumOfRatings += userRating;
            userRatings.put(username, userRating);
        }
        rating = sumOfRatings / userRatings.size();
    }

    public void increaseInStock() { inStock++; }

    public void decreaseInStock() { inStock--; }

    private int id;

    private String name;

    private int providerId;

    private int price;

    private HashSet<String> categories = new HashSet<String>();

    private float rating;

    private int inStock;

    private transient HashMap<String, Integer> userRatings = new HashMap<String, Integer>();
}
