package ut.ie.baloot3;

import com.google.gson.Gson;
import java.net.URI;
import java.net.URISyntaxException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.lang.reflect.Type;
import ut.ie.baloot3.models.User;
import ut.ie.baloot3.models.Provider;
import ut.ie.baloot3.models.Comment;
import ut.ie.baloot3.models.Commodity;
import java.util.ArrayList;
import com.google.gson.reflect.TypeToken;
import ut.ie.baloot3.models.Store;

public class FetchDataFromApi {

    public static void getData() {
        Gson gson = new Gson();
        URI apiAddress;
        try {
            apiAddress = new URI("http://5.253.25.110:5000/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        // Add Users
        String response;
        try {
            response = Jsoup.connect(apiAddress.resolve("/api/users").toString())
                    .ignoreContentType(true)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        List<JsonElement> toBeAddedUsers = JsonParser.parseString(response).getAsJsonArray().asList();
        Type UsersArrayListType = new TypeToken<ArrayList<User>>() {
        }.getType();
        ArrayList<User> toBeAddedUsers = gson.fromJson(response, UsersArrayListType);
        for (var user : toBeAddedUsers) {
            System.out.println(gson.toJson(Store.getInstance().addUser(gson.toJson(user))));
        }

        // Add Providers
        try {
            response = Jsoup.connect(apiAddress.resolve("/api/providers").toString())
                    .ignoreContentType(true)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Type ProvidersArrayListType = new TypeToken<ArrayList<Provider>>() {
        }.getType();
        ArrayList<Provider> toBeAddedProviders = gson.fromJson(response, ProvidersArrayListType);
        for (var provider : toBeAddedProviders) {
            System.out.println(gson.toJson(Store.getInstance().addProvider(gson.toJson(provider))));
        }

        // Add Commodities
        try {
            response = Jsoup.connect(apiAddress.resolve("/api/commodities").toString())
                    .ignoreContentType(true)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Type CommoditiesArrayListType = new TypeToken<ArrayList<Commodity>>() {
        }.getType();
        ArrayList<Commodity> toBeAddedCommodities = gson.fromJson(response, CommoditiesArrayListType);
        for (var commodity : toBeAddedCommodities) {
            System.out.println(gson.toJson(Store.getInstance().addCommodity(gson.toJson(commodity))));
        }

        // Add Comments
        try {
            response = Jsoup.connect(apiAddress.resolve("/api/comments").toString())
                    .ignoreContentType(true)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Type CommentsArrayListType = new TypeToken<ArrayList<Comment>>() {
        }.getType();
        ArrayList<Comment> toBeAddedComments = gson.fromJson(response, CommentsArrayListType);
        for (var comment : toBeAddedComments) {
            System.out.println(gson.toJson(Store.getInstance().addComment(gson.toJson(comment))));
        }

        // Add Discount Codes
        try {
            response = Jsoup.connect(apiAddress.resolve("/api/discount").toString())
                    .ignoreContentType(true)
                    .execute()
                    .body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonArray jsonArray = JsonParser.parseString(response).getAsJsonArray();
        for (var discountJsonElement : jsonArray.asList()) {
            JsonObject discountJsonObject = discountJsonElement.getAsJsonObject();
            String discountCode = discountJsonObject.get("discountCode").getAsString();
            int discountPercentage = discountJsonObject.get("discount").getAsInt();
            Store.getInstance().addDiscountCode(discountCode, discountPercentage);
        }
    }
}
