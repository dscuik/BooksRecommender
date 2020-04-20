package dz.souhilazidane.booksrecommender;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity implements TaskInterface {

    public static final String EXTRA_USER_ID = "id";
    public static final String EXTRA_USER_FIRST = "first_name";
    public static final String EXTRA_USER_FAMILY = "family_name";
    public static final String EXTRA_USER_YEAR = "year";
    public static final String EXTRA_USER_FIELD = "field";

    ArrayList<String> BookTitle = new ArrayList<>();
    ArrayList<String> BookAuthor = new ArrayList<>();
    ArrayList<String> BookRating = new ArrayList<>();

    ListView books;
    HashMap<String,String> map;
    ArrayList<HashMap<String,String>> array;
    SimpleAdapter adapter;
    static ProgressBar progress;
    TextView ratings_dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        books = (ListView) findViewById(R.id.books);
        progress = (ProgressBar)findViewById(R.id.progress);
        ratings_dis = (TextView)findViewById(R.id.ratings_dis);
        //display details of the user
        String user_first = (String)getIntent().getExtras().get(EXTRA_USER_FIRST);
        String user_family = (String)getIntent().getExtras().get(EXTRA_USER_FAMILY);
        String user_year = (String) getIntent().getExtras().get(EXTRA_USER_YEAR);
        String user_field = (String) getIntent().getExtras().get(EXTRA_USER_FIELD);
        int id_user = (Integer) getIntent().getExtras().get(EXTRA_USER_ID);

        TextView full = (TextView)findViewById(R.id.info_text);
        TextView details = (TextView)findViewById(R.id.info_text2);

        full.setText(user_first+" "+user_family);
        details.setText("Grade: "+user_year+"\nField: "+user_field);

        URL url = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/getUserRatings.php?id_user="+id_user);
        new ConnectionTask(UserDetailActivity.this).execute(url);

    }

    //up navigation doing the same thing as back navigation
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnFinished(String response) {

        List<String> list = JSONUtils.getRatings(response);
        int i=0;

        if(list.size()>0) {
            while (i < list.size()) {

                BookTitle.add(list.get(i));
                BookAuthor.add(list.get(i + 1));
                BookRating.add(list.get(i + 2));
                i = i + 3;

            }
        }

        if(BookTitle.size()>0) {

            map = new HashMap<>();
            array = new ArrayList<>();


            map.put("title","title");
            map.put("author","author");
            map.put("rating","rating");

            array.add(map);


            adapter = new SimpleAdapter(getApplicationContext(),
                    array,R.layout.row_list,
                    new String[]{"title","author","rating"},
                    new int[]{R.id.title,R.id.author,R.id.rating});


            books.setAdapter(adapter);

            for(int j=0;j<BookTitle.size();j++) {
                HashMap<String, String> map1 = new HashMap<>();

                map1.put("title", BookTitle.get(j));
                map1.put("author", BookAuthor.get(j));
                map1.put("rating", BookRating.get(j));

                array.add(map1);

                books.setAdapter(adapter);
            }

        }else {
            ratings_dis.setVisibility(View.VISIBLE);
        }


    }
}
