package ut.ie.baloot3.models;

import java.util.Collections;
import java.util.HashMap;

public class Comment {
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", commodityId=" + commodityId +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    public Response vote(String username, int vote) {
        votes.put(username, vote);
        return new Response();
    }

    public int getLikes() {
        return Collections.frequency(votes.values(), 1);
    }

    public int getDislikes() {
        return Collections.frequency(votes.values(), -1);
    }

    public int id;

    public String username;

    public String userEmail;

    public int commodityId;

    public String text;

    public String date;

    public HashMap<String, Integer> votes = new HashMap<String, Integer>();
}
