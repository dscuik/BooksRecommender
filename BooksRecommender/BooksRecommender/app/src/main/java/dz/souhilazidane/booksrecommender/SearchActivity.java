package dz.souhilazidane.booksrecommender;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements TaskInterface {

    //arrays of the resulted books
    static ArrayList<Integer> BookId = new ArrayList<>();
    static ArrayList<String> BookISBN = new ArrayList<>();
    static ArrayList<String> BookTitle = new ArrayList<>();
    static ArrayList<String> BookAuthor = new ArrayList<>();
    static ArrayList<Integer> BookYear = new ArrayList<>();
    static ArrayList<String> BookPublisher = new ArrayList<>();
    static ArrayList<String> BookCover = new ArrayList<>();

    TextView search_text;
    TextView searchdisplay_text;
    static ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_text = (TextView)findViewById(R.id.search_text);
        progress = (ProgressBar)findViewById(R.id.progress);
        searchdisplay_text = (TextView)findViewById(R.id.searchdisplay_text);
    }

    public void Search(View view){
        String query = search_text.getText().toString();

        BookId = new ArrayList<>();
        BookISBN = new ArrayList<>();
        BookTitle = new ArrayList<>();
        BookAuthor = new ArrayList<>();
        BookYear = new ArrayList<>();
        BookPublisher = new ArrayList<>();
        BookCover = new ArrayList<>();

        URL url = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/getSearchedBooks.php?search="+query);
        new ConnectionTask(SearchActivity.this).execute(url);

    }

    public void Back(View view) {
        finish();
    }

    @Override
    public void OnFinished(String response) {


        List<Book> list = JSONUtils.getBooks(response);


        for(int i=0;i<list.size();i++) {

            BookId.add(list.get(i).getId_book());
            BookISBN.add(list.get(i).getISBN());
            BookTitle.add(list.get(i).getTitle());
            BookAuthor.add(list.get(i).getAuthor());
            BookYear.add(list.get(i).getYear_of_publication());
            BookPublisher.add(list.get(i).getPublisher());
            BookCover.add(list.get(i).getImageURL_L());

        }

        if(BookId.size()>0) {
            searchdisplay_text.setVisibility(View.INVISIBLE);
        } else {
            searchdisplay_text.setVisibility(View.VISIBLE);
        }

        BooksFragment fragment = new BooksFragment(BookISBN, BookTitle, BookAuthor, BookYear, BookPublisher, BookCover, BookId);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, fragment);
        ft.commit();

    }
}
