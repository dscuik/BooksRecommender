package dz.souhilazidane.booksrecommender;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,TaskInterface {

    public final static String EXTRA_USER_ID = "id_user";
    public final static String EXTRA_STUDENT_USERNAME = "username";
    public final static String EXTRA_STUDENT_1NAME = "first_name";
    public final static String EXTRA_STUDENT_2NAME = "family_name";
    public static String name = "";
    public static String id = "";
    public static boolean a,b;
    static ProgressBar progress;
    static TextView recommendation_text;
    Toolbar toolbar;
    static String ip="192.168.43.62";


    ArrayList<Integer> BookId = new ArrayList<>();
    ArrayList<String> BookISBN = new ArrayList<>();
    ArrayList<String> BookTitle = new ArrayList<>();
    ArrayList<String> BookAuthor = new ArrayList<>();
    ArrayList<Integer> BookYear = new ArrayList<>();
    ArrayList<String> BookPublisher = new ArrayList<>();
    ArrayList<String> BookCover = new ArrayList<>();

    ArrayList<Integer> UserId = new ArrayList<>();
    ArrayList<String> UserFirst = new ArrayList<>();
    ArrayList<String> UserFamily = new ArrayList<>();
    ArrayList<String> UserYear = new ArrayList<>();
    ArrayList<String> UserField = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        progress = (ProgressBar)findViewById(R.id.progress);

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.nav_open_drawer,
                R.string.nav_close_drawer
        );


        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);



        //display username and full name in the navigation header
        TextView student_name = (TextView)headerView.findViewById(R.id.nav_student_name);
        TextView student_username = (TextView)headerView.findViewById(R.id.nav_student_usename);

        String l = (String) getIntent().getExtras().get(EXTRA_USER_ID);
        String i = (String) getIntent().getExtras().get(EXTRA_STUDENT_USERNAME);
        String j = (String) getIntent().getExtras().get(EXTRA_STUDENT_1NAME);
        String k = (String) getIntent().getExtras().get(EXTRA_STUDENT_2NAME);
        student_name.setText(j+" "+k);
        student_username.setText("@"+i);

        name=j;
        id=l;


        Fragment fragment=new HomeFragment();


        FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
        ft.add(R.id.main_content,fragment);
        ft.commit();

        recommendation_text = (TextView)findViewById(R.id.recommendation_text);


        drawer.openDrawer(GravityCompat.START);
        //drawer.closeDrawer(GravityCompat.START);

    }

    //search method
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);

        MenuItem search = menu.findItem(R.id.search);

        search.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(UserActivity.this,SearchActivity.class);
                startActivity(intent);
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id=item.getItemId();
        Fragment fragment = null;
        Intent intent = null;
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        EmptyFragment empty = new EmptyFragment();
        FragmentTransaction fte = getSupportFragmentManager().beginTransaction();
        a=false; b=false;

        switch (id) {
            case R.id.nav_books:
                fte.replace(R.id.main_content, empty);
                fte.commit();
                a=true; b=false;
                URL url = HttpUtils.CreateLoginURL("http://"+ip+"/android_connect/getAllBooks.php");
                new ConnectionTask(UserActivity.this).execute(url);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Books");
                UserActivity.recommendation_text.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_users:
                empty = new EmptyFragment();
                fte = getSupportFragmentManager().beginTransaction();
                fte.replace(R.id.main_content, empty);
                fte.commit();
                b=true; a=false;
                URL url2 = HttpUtils.CreateLoginURL("http://"+ip+"/android_connect/getAllUsers.php");
                new ConnectionTask(UserActivity.this).execute(url2);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle("Users");
                UserActivity.recommendation_text.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_logout:
                UserActivity.recommendation_text.setVisibility(View.INVISIBLE);
                intent=new Intent(this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Toast.makeText(UserActivity.this,"goodbye "+getIntent().getExtras().get(EXTRA_STUDENT_1NAME)+"!",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                break;
            case R.id.nav_settings:
                intent=new Intent(this,SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_contact:
                drawer.closeDrawer(GravityCompat.START);
                intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "about the app");
                intent.putExtra(Intent.EXTRA_EMAIL, "ourappmail@gmail.com");
                startActivity(intent);
                break;
            default:

                toolbar.setTitle("Home");
                fragment=new HomeFragment();
                FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_content,fragment);
                ft.commit();
                drawer.closeDrawer(GravityCompat.START);
        }

        return true;
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);

        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }


    @Override
    public void OnFinished(String response) {

        if(a) {
            List<Book> list = JSONUtils.getBooks(response);


            for (int i = 0; i < list.size(); i++) {

                BookId.add(list.get(i).getId_book());
                BookISBN.add(list.get(i).getISBN());
                BookTitle.add(list.get(i).getTitle());
                BookAuthor.add(list.get(i).getAuthor());
                BookYear.add(list.get(i).getYear_of_publication());
                BookPublisher.add(list.get(i).getPublisher());
                BookCover.add(list.get(i).getImageURL_L());


            }

            BooksFragment fragment = new BooksFragment(BookISBN, BookTitle, BookAuthor, BookYear, BookPublisher, BookCover, BookId);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_content, fragment);
            ft.commit();

        } else if(b) {

            List<User> list2 = JSONUtils.getUsers(response);


            for (int i = 0; i < list2.size(); i++) {

                UserId.add(list2.get(i).getId_user());
                UserFirst.add(list2.get(i).getFirst());
                UserFamily.add(list2.get(i).getFamily());
                UserYear.add(list2.get(i).getYear());
                UserField.add(list2.get(i).getField());


            }

            UsersFragment fragment = new UsersFragment(UserId,UserFirst, UserFamily, UserYear, UserField);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_content, fragment);
            ft.commit();
        }
    }

}
