package dz.souhilazidane.booksrecommender;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONUtils {

    public static List<Book> getBooks(String response) {

        ArrayList<Book> books = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(response);
            JSONArray objects = root.getJSONArray("book");

            for (int i = 0; i < objects.length(); i++) {
                JSONObject book = objects.getJSONObject(i);
                int id_book = book.getInt("id_book");
                String ISBN = book.getString("ISBN");
                String title = book.getString("title");
                String author = book.getString("author");
                int year_of_publication = book.getInt("year_of_publication");
                String publisher = book.getString("publisher");
                String imageURL_L = book.getString("imageURL_L");

                books.add(new Book(id_book,ISBN, title, author, year_of_publication, publisher, imageURL_L));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<User> getUsers(String response) {

        ArrayList<User> users = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(response);
            JSONArray objects = root.getJSONArray("user");

            for (int i = 0; i < objects.length(); i++) {
                JSONObject book = objects.getJSONObject(i);
                int id_user = book.getInt("id_user");
                String first_name = book.getString("first_name");
                String family_name = book.getString("family_name");
                String year = book.getString("year");
                String field = book.getString("field");

                users.add(new User(id_user,first_name, family_name, year, field));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<String> getRatings(String response) {

        ArrayList<String> ratings = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(response);
            JSONArray objects = root.getJSONArray("rating");

            for (int i = 0; i < objects.length(); i++) {
                JSONObject book = objects.getJSONObject(i);

                String title = book.getString("title");
                String author = book.getString("author");
                String rating = book.getString("rating");

                ratings.add(title);
                ratings.add(author);
                ratings.add(rating);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ratings;
    }
}
