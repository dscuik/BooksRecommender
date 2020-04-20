package dz.souhilazidane.booksrecommender;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements TaskInterface {

    //arrays of the recommended books
    static ArrayList<Integer> BookId = new ArrayList<>();
    static ArrayList<String> BookISBN = new ArrayList<>();
    static ArrayList<String> BookTitle = new ArrayList<>();
    static ArrayList<String> BookAuthor = new ArrayList<>();
    static ArrayList<Integer> BookYear = new ArrayList<>();
    static ArrayList<String> BookPublisher = new ArrayList<>();
    static ArrayList<String> BookCover = new ArrayList<>();

    BookCaptionedImagesAdapter adapter;
    RecyclerView recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recycler = (RecyclerView)inflater.inflate(R.layout.fragment_home,container,false);

                UserActivity.recommendation_text.setVisibility(View.INVISIBLE);

                URL url = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/recommended_itemitem.php?id_user="+UserActivity.id);
                new ConnectionTask(this).execute(url);

                URL url2 = HttpUtils.CreateLoginURL("http://"+UserActivity.ip+"/android_connect/recommended_useruser.php?id_user="+UserActivity.id);
                new ConnectionTask(this).execute(url2);

                adapter = new BookCaptionedImagesAdapter(BookTitle, BookAuthor, BookISBN);
                recycler.setAdapter(adapter);

                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recycler.setLayoutManager(layoutManager);


        return recycler;
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
            UserActivity.recommendation_text.setVisibility(View.INVISIBLE);
            adapter = new BookCaptionedImagesAdapter(BookTitle, BookAuthor, BookISBN);
            recycler.setAdapter(adapter);

            adapter.setListener(new BookCaptionedImagesAdapter.Listener() {
                public void onClick(int position) {
                    Intent intent = new Intent(getActivity(),BookDetailActivity.class);

                    intent.putExtra(BookDetailActivity.EXTRA_BOOK_ID,BookId.get(position));
                    intent.putExtra(BookDetailActivity.EXTRA_BOOK_ISBN,BookISBN.get(position));
                    intent.putExtra(BookDetailActivity.EXTRA_BOOK_TITLE,BookTitle.get(position));
                    intent.putExtra(BookDetailActivity.EXTRA_BOOK_AUTHOR,BookAuthor.get(position));
                    intent.putExtra(BookDetailActivity.EXTRA_BOOK_YEAR,BookYear.get(position));
                    intent.putExtra(BookDetailActivity.EXTRA_BOOK_PUBLISHER,BookPublisher.get(position));
                    intent.putExtra(BookDetailActivity.EXTRA_BOOK_COVER,BookCover.get(position));


                    getActivity().startActivity(intent);
                }
            });

        } else {
            UserActivity.recommendation_text.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        BookId = new ArrayList<>();
        BookISBN = new ArrayList<>();
        BookTitle = new ArrayList<>();
        BookAuthor = new ArrayList<>();
        BookYear = new ArrayList<>();
        BookPublisher = new ArrayList<>();
        BookCover = new ArrayList<>();
    }
}

