package dz.souhilazidane.booksrecommender;


import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

public class BookDetailActivity extends AppCompatActivity implements TaskInterface {

    public static final String EXTRA_BOOK_ID = "id";
    public static final String EXTRA_BOOK_ISBN = "isbn";
    public static final String EXTRA_BOOK_TITLE = "title";
    public static final String EXTRA_BOOK_AUTHOR = "author";
    public static final String EXTRA_BOOK_YEAR = "year";
    public static final String EXTRA_BOOK_PUBLISHER = "publisher";
    public static final String EXTRA_BOOK_COVER = "cover";

    RatingBar myratingbar,globalratingbar;
    Float rating=0f;
    boolean get,get2;
    static ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        //toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progress = (ProgressBar)findViewById(R.id.progress);

        //display details of the book
        String book_isbn = (String)getIntent().getExtras().get(EXTRA_BOOK_ISBN);
        String book_title = (String)getIntent().getExtras().get(EXTRA_BOOK_TITLE);
        String book_author = (String)getIntent().getExtras().get(EXTRA_BOOK_AUTHOR);
        int book_year = (Integer) getIntent().getExtras().get(EXTRA_BOOK_YEAR);
        String book_publisher = (String)getIntent().getExtras().get(EXTRA_BOOK_PUBLISHER);
        String book_cover = "r"+book_isbn.toLowerCase();

        TextView title = (TextView)findViewById(R.id.info_text);
        TextView author = (TextView)findViewById(R.id.info_text2);
        TextView description = (TextView)findViewById(R.id.description);
        ImageView cover = (ImageView)findViewById(R.id.info_image);


        title.setText(book_title);
        author.setText(book_author);
        description.setText("ISBN:  "+book_isbn+"\n"+
                "YEAR OF PUBLISHEMENT:  "+book_year+"\n"+
                "PUBLISHER:  "+book_publisher+"\n");

        int resourceId = getResources().getIdentifier(book_cover, "drawable",
                getApplicationContext().getPackageName());

        if(resourceId!=0) {
            cover.setImageResource(resourceId);
        } else {
            cover.setImageResource(R.drawable.ic_book);
        }


        //the user rating listener
        /*RatingBar.OnRatingBarChangeListener barChangeListener = new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar rBar, float fRating, boolean fromUser) {
                int rating = (int) fRating;
                String message = null;
                switch(rating) {
                    case 1: message = "Sorry you're really upset with us"; break;
                    case 2: message = "Sorry you're not happy"; break;
                    case 3: message = "Good enough is not good enough"; break;
                    case 4: message = "Thanks, we're glad you liked it."; break;
                    case 5: message = "Awesome - thanks!"; break;
                }
                Toast.makeText(BookDetailActivity.this,
                        message,
                        Toast.LENGTH_LONG).show();
            }
        };
        */

        myratingbar = (RatingBar)findViewById(R.id.my_rating_bar);
        //myratingbar.setOnRatingBarChangeListener(barChangeListener);

        String id_user = UserActivity.id;
        int id_book = (Integer) getIntent().getExtras().get(EXTRA_BOOK_ID);

        get=true;
        URL url = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/getUserRating.php?id_user="+id_user+"&id_book="+Integer.toString(id_book));
        new ConnectionTask(BookDetailActivity.this).execute(url);

        get2=true;
        URL url2 = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/getBookGlobalRating.php?id_book="+Integer.toString(id_book));
        new ConnectionTask(BookDetailActivity.this).execute(url2);



    }

    //the user rate the book and click on DONE
    public void onRate(View view) {

        String id_user = UserActivity.id;
        String rating = Float.toString(myratingbar.getRating());
        int id_book = (Integer) getIntent().getExtras().get(EXTRA_BOOK_ID);

        //upload the rating to a database
        get=false;
        URL url = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/AddUserRating.php?id_user="+id_user+"&id_book="+id_book+"&rating="+rating);
        new ConnectionTask(BookDetailActivity.this).execute(url);


        //thank the user
        String name= UserActivity.name;
        Toast.makeText(BookDetailActivity.this, "Thank you "+name+"!", Toast.LENGTH_LONG).show();


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

        if (get) {
            try {
                JSONObject mJSONFile = new JSONObject(response);
                int msg = mJSONFile.getInt("message");

                if (msg == 1) {
                    String ratingJSON = mJSONFile.getString("rating");
                    rating = Float.parseFloat(ratingJSON);
                }

                myratingbar.setRating(rating);
                if(BookDetailActivity.progress != null) BookDetailActivity.progress.setVisibility(View.INVISIBLE);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        if(get2) {

            try {
                JSONObject mJSONFile = new JSONObject(response);
                int msg = mJSONFile.getInt("message");

                if (msg == 1) {
                    String rating_avg = mJSONFile.getString("avg(rating)");

                    globalratingbar = (RatingBar)findViewById(R.id.gl_rating_bar);
                    globalratingbar.setRating(Float.parseFloat(rating_avg));
                    if(BookDetailActivity.progress != null) BookDetailActivity.progress.setVisibility(View.INVISIBLE);

                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }




    }
}
