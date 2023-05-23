package ut.ie.baloot3.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ut.ie.baloot3.FetchDataFromApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Store {
    private static Store singletonStore = null;
    public static Store getInstance() {
        if (singletonStore == null) {
            singletonStore = new Store();
            FetchDataFromApi.getData();
        }
        return singletonStore;
    }
    public Response addUser(String jsonData) {
        User toBeAddedUser = gson.fromJson(jsonData, User.class);
        Response response = new Response();
        // Check username's validity and return an error message if required
        if (!isUsernameValid(toBeAddedUser.getUsername())) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.invalidUsername);
            return response;
        }
        // Construct an appropriate message depending on if we update or add a user
        if (users.containsKey(toBeAddedUser.getUsername())) {
            response.success = true;
            response.data.addProperty("message", ErrorAndSuccessMessages.updatedExistingUser);
        }
        else {
            response.success = true;
            response.data.addProperty("message", ErrorAndSuccessMessages.addedNewUser);
        }
        // Update or add the user
        users.put(toBeAddedUser.getUsername(), toBeAddedUser);
        return response;
    }

    public Response addProvider(String jsonData) {
        Provider toBeAddedProvider = gson.fromJson(jsonData, Provider.class);
        Response response = new Response();
        // Check to see if the given ID is unique
        if (providers.containsKey(toBeAddedProvider.getId())) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.providerIdAlreadyExists);
            return response;
        }
        providers.put(toBeAddedProvider.getId(), toBeAddedProvider);
        response.success = true;
        response.data.addProperty("message", ErrorAndSuccessMessages.addedNewProvider);
        return response;
    }

    public Provider getProvider(int providerId) {
        return providers.get(providerId);
    }

    public Response addCommodity(String jsonData) {
        Commodity toBeAddedCommodity = gson.fromJson(jsonData, Commodity.class);
        Response response = new Response();
        if (commodities.containsKey(toBeAddedCommodity.getId())) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.commodityIdAlreadyExists);
            return response;
        }
        if (!providers.containsKey(toBeAddedCommodity.getProviderId())) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.providerIdDoesNotExist);
            return response;
        }
        commodities.put(toBeAddedCommodity.getId(), toBeAddedCommodity);
        response.success = true;
        response.data.addProperty("message", ErrorAndSuccessMessages.addedNewCommodity);
        return response;
    }

    public Response getCommoditiesList() {
        Response response = new Response();
        response.success = true;
        JsonArray jsonArray = new JsonArray();
        for (var commodity : commodities.values()) {
            jsonArray.add(JsonParser.parseString(gson.toJson(commodity)).getAsJsonObject());
        }
        response.data.add("commoditiesList", jsonArray);
        return response;
    }

    public Response rateCommodity(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        int commodityId = jsonObject.get("commodityId").getAsInt();
        int score = jsonObject.get("score").getAsInt();
        Response response = new Response();

        if (!commodities.containsKey(commodityId)) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.commodityIdDoesNotExist);
            return response;
        }
        if (!users.containsKey(username)) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.usernameDoesNotExist);
            return response;
        }
        // INPUTTING A FLOAT SHOULD BE HANDLED!!
        if (score < 1 || score > 10) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.invalidRatingRange);
            return response;
        }
        commodities.get(commodityId).addOrModifyRating(username, score);
        response.success = true;
        response.data.addProperty("message", ErrorAndSuccessMessages.updatedRating);
        return response;
    }

    public void rateCommodity(int commodityId, String username, int score) {
        commodities.get(commodityId).addOrModifyRating(username, score);
    }

    public Response addToBuyList(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        int commodityId = jsonObject.get("commodityId").getAsInt();
        Response response = new Response();

        if (!commodities.containsKey(commodityId)) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.commodityIdDoesNotExist);
            return response;
        }
        if (!users.containsKey(username)) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.usernameDoesNotExist);
            return response;
        }
        if (commodities.get(commodityId).getInStock() < 1) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.notEnoughStockForTheRequestedCommodity);
            return response;
        }
        if (users.get(username).doesItemExistInBuyList(commodityId)) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.itemAlreadyInBuyList);
            return response;
        }
        if (users.get(username).getCredit() < commodities.get(commodityId).getPrice()) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.notEnoughCredit);
            return response;
        }

        commodities.get(commodityId).decreaseInStock();
        users.get(username).addToBuyList(commodityId);
        response.success = true;
        response.data.addProperty("message", ErrorAndSuccessMessages.addedToBuyList);
        return response;
    }

    public Response removeFromBuyList(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        int commodityId = jsonObject.get("commodityId").getAsInt();
        Response response = new Response();
        if (!users.containsKey(username)) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.usernameDoesNotExist);
            return response;
        }
        if (!users.get(username).doesItemExistInBuyList(commodityId)) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.itemNotInBuyList);
            return response;
        }
        commodities.get(commodityId).increaseInStock();
        users.get(username).removeFromBuyList(commodityId);
        response.success = true;
        response.data.addProperty("message", ErrorAndSuccessMessages.removedFromBuyList);
        return response;
    }

    public Response getCommodityById(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        int id = jsonObject.get("id").getAsInt();
        Response response = new Response();
        if (!commodities.containsKey(id)) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.commodityIdDoesNotExist);
            return response;
        }
        response.success = true;
        Commodity commodity = commodities.get(id);
        response.data.addProperty("id", id);
        response.data.addProperty("name", commodity.getName());
        response.data.addProperty("provider", providers.get(commodity.getProviderId()).getName());
        response.data.addProperty("price", commodity.getPrice());
        JsonArray jsonArray = new JsonArray();
        for (String category : commodity.getCategoriesHashset()) {
            jsonArray.add(category);
        }
        response.data.add("categories", jsonArray);
        response.data.addProperty("rating", commodity.getRating());
        return response;
    }

    public Commodity getCommodityById(int commodityId) {
        return commodities.get(commodityId);
    }

    public Response getCommoditiesByCategory(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        String category = jsonObject.get("category").getAsString();
        Response response = new Response();
        response.success = true;
        JsonArray jsonArray = new JsonArray();
        for (Commodity commodity : commodities.values()) {
            if (commodity.getCategoriesHashset().contains(category)) {
                jsonObject = new JsonObject();
                jsonObject.addProperty("id", commodity.getId());
                jsonObject.addProperty("name", commodity.getName());
                jsonObject.addProperty("providerId", commodity.getProviderId());
                jsonObject.addProperty("price", commodity.getPrice());
                JsonArray categoriesArray = new JsonArray();
                for (String cat : commodity.getCategoriesHashset()) {
                    categoriesArray.add(cat);
                }
                jsonObject.add("categories", categoriesArray);
                jsonObject.addProperty("rating", commodity.getRating());
                jsonArray.add(jsonObject);
            }
        }
        response.data.add("commoditiesListByCategory", jsonArray);
        return response;
    }

    public Response getBuyList(String jsonData) {
        JsonObject jsonObject = JsonParser.parseString(jsonData).getAsJsonObject();
        String username = jsonObject.get("username").getAsString();
        Response response = new Response();
        if (!users.containsKey(username)) {
            response.success = false;
            response.data.addProperty("message", ErrorAndSuccessMessages.usernameDoesNotExist);
            return response;
        }
        JsonArray buyList = new JsonArray();
        User user = users.get(username);
        for (int commodityId : user.getBuyList()) {
            Commodity commodity = commodities.get(commodityId);
            JsonObject buyListItem = new JsonObject();
            buyListItem.addProperty("id", commodity.getId());
            buyListItem.addProperty("name", commodity.getName());
            buyListItem.addProperty("providerId", commodity.getProviderId());
            buyListItem.addProperty("price", commodity.getPrice());
            JsonArray commodityCategories = new JsonArray();
            for (String cat : commodity.getCategoriesHashset()) {
                commodityCategories.add(cat);
            }
            buyListItem.add("categories", commodityCategories);
            buyListItem.addProperty("rating", commodity.getRating());
            buyList.add(buyListItem);
        }
        response.success = true;
        response.data.add("buyList", buyList);
        return response;
    }

    public Response addComment(String jsonData) {
        Comment toBeAddedComment = gson.fromJson(jsonData, Comment.class);
        for (User user : users.values()) {
            if (user.getEmail().equalsIgnoreCase(toBeAddedComment.userEmail)) {
                toBeAddedComment.username = user.getUsername();
            }
        }
        toBeAddedComment.id = getIdForComment();
        comments.put(toBeAddedComment.id, toBeAddedComment);
        return new Response();
    }

    public Response addComment(Comment comment) {
        comment.id = getIdForComment();
        comments.put(comment.id, comment);
        return new Response();
    }

    public ArrayList<Comment> getCommentsForCommodity(int commodityId) {
        ArrayList<Comment> result = new ArrayList<Comment>();
        for (Comment comment : comments.values()) {
            if (comment.commodityId == commodityId) {
                result.add(comment);
            }
        }
        return result;
    }

    public void likeOrDislikeComment(int commentId, String voterUsername, int likeOrDislike) {
        comments.get(commentId).vote(voterUsername, likeOrDislike);
    }

    public ArrayList<Commodity> getCommoditiesArrayList() {
        ArrayList<Commodity> comms = new ArrayList<Commodity>(commodities.values());
        return comms;
    }

    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public boolean isAnyUserLoggedIn() {
        return currentUser != null;
    }

    public void addDiscountCode(String code, int discount) {
        discountCodes.put(code, discount);
    }

    public int getDiscountPercentage(String code) {
        return discountCodes.get(code) == null || currentUser.isDiscountCodeUsed(code) ? 0 : discountCodes.get(code);
    }

    public ArrayList<Commodity> getSuggestedCommodities(int commodityId) {
        HashMap<Integer, Double> idAndScore = new HashMap<>();
        for (Commodity commodity : commodities.values()) {
            if (commodity.getId() == commodityId) {
                continue;
            }
            boolean isInSimilarCategory = false;
            for (String category : getCommodityById(commodityId).getCategoriesHashset()) {
                if (commodity.getCategoriesHashset().contains(category)) {
                    isInSimilarCategory = true;
                    break;
                }
            }
            double score = 11 * (isInSimilarCategory ? 1 : 0) + commodity.getRating();
            idAndScore.put(commodity.getId(), score);
        }
        ArrayList<Commodity> comms = new ArrayList<>();
        for (int i = 0; i < 5 || idAndScore.isEmpty(); i++) {
            int maxKey = 0;
            double maxValue = Integer.MIN_VALUE;
            for (Map.Entry<Integer, Double> entry : idAndScore.entrySet()) {
                if (entry.getValue() > maxValue) {
                    maxValue = entry.getValue();
                    maxKey = entry.getKey();
                }
            }
            comms.add(getCommodityById(maxKey));
            idAndScore.remove(maxKey);
        }
        return comms;
    }

    private boolean isUsernameValid(String username) {
        for (char c : username.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private HashMap<String, User> users = new HashMap<String, User>();

    private HashMap<Integer, Provider> providers = new HashMap<Integer, Provider>();

    private HashMap<Integer, Commodity> commodities = new HashMap<Integer, Commodity>();

    private int commentId = 1;

    private int getIdForComment() { return commentId++;}

    private HashMap<Integer, Comment> comments = new HashMap<Integer, Comment>();

    private HashMap<String, Integer> discountCodes = new HashMap<String, Integer>();

    private Gson gson = new Gson();

    public User currentUser = null;

    private static class ErrorAndSuccessMessages {
        static String invalidUsername = "INVALID_USERNAME";
        static String providerIdAlreadyExists = "PROVIDER_ID_ALREADY_EXISTS";
        static String addedNewUser = "ADDED_NEW_USER";
        static String updatedExistingUser = "UPDATED_EXISTING_USER";
        static String addedNewProvider = "ADDED_NEW_PROVIDER";
        static String commodityIdAlreadyExists = "COMMODITY_ID_ALREADY_EXISTS";
        static String providerIdDoesNotExist = "PROVIDER_ID_DOES_NOT_EXIST";
        static String addedNewCommodity = "ADDED_NEW_COMMODITY";
        static String commodityIdDoesNotExist = "COMMODITY_ID_DOES_NOT_EXIST";
        static String usernameDoesNotExist = "USERNAME_DOES_NOT_EXIST";
        static String invalidRatingRange = "INVALID_RATING_RANGE";
        static String updatedRating = "UPDATED_RATING";
        static String notEnoughStockForTheRequestedCommodity = "NOT_ENOUGH_STOCK_FOR_THE_REQUESTED_COMMODITY";
        static String itemAlreadyInBuyList = "ITEM_ALREADY_IN_BUY_LIST";
        static String notEnoughCredit = "NOT_ENOUGH_CREDIT";
        static String addedToBuyList = "ADDED_TO_BUY_LIST";
        static String removedFromBuyList = "REMOVED_FROM_BUY_LIST";
        static String itemNotInBuyList = "ITEM_NOT_IN_BUY_LIST";
    }
}
