package dz.souhilazidane.booksrecommender;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class BooksFragment extends Fragment {

    ArrayList<Integer> BookId;
    ArrayList<String> BookISBN;
    ArrayList<String> BookTitle;
    ArrayList<String> BookAuthor;
    ArrayList<Integer> BookYear;
    ArrayList<String> BookPublisher;
    ArrayList<String> BookCover;



    public BooksFragment(ArrayList<String> a, ArrayList<String> b, ArrayList<String> c, ArrayList<Integer> d, ArrayList<String> e, ArrayList<String> f, ArrayList<Integer> g) {
        this.BookISBN=a;
        this.BookTitle=b;
        this.BookAuthor=c;
        this.BookYear=d;
        this.BookPublisher=e;
        this.BookCover=f;
        this.BookId=g;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView recycler = (RecyclerView)inflater.inflate(R.layout.fragment_books,container,false);


        BookCaptionedImagesAdapter adapter = new BookCaptionedImagesAdapter(BookTitle,BookAuthor,BookISBN);
        recycler.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);

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


        return recycler;
    }

}
