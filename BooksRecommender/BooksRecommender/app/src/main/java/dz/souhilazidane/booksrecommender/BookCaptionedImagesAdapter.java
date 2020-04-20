package dz.souhilazidane.booksrecommender;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookCaptionedImagesAdapter extends RecyclerView.Adapter<BookCaptionedImagesAdapter.ViewHolder> {

    private ArrayList<String> titles;
    private ArrayList<String> authors;
    private ArrayList<String> ISBNs;

    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    public BookCaptionedImagesAdapter(ArrayList<String> titles, ArrayList<String> authors, ArrayList<String> ISBNs) {
        this.titles=titles;
        this.authors=authors;
        this.ISBNs=ISBNs;
    }

    @Override
    public int getItemCount(){
        return titles.size();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public BookCaptionedImagesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    public void onBindViewHolder(ViewHolder holder,final int position){
        CardView cardView = holder.cardView;
        ImageView book_cover = (ImageView)cardView.findViewById(R.id.info_image);

        String isbn="r"+ISBNs.get(position).toLowerCase();

         int resourceId = cardView.getContext().getResources().getIdentifier(isbn, "drawable",
                cardView.getContext().getPackageName());

         if(resourceId!=0) {
             book_cover.setImageDrawable(ContextCompat.getDrawable(cardView.getContext(), resourceId));
         } else {
             book_cover.setImageDrawable(ContextCompat.getDrawable(cardView.getContext(), R.drawable.ic_book));
         }

        book_cover.setContentDescription(titles.get(position));
        TextView book_title = (TextView)cardView.findViewById(R.id.info_text);
        TextView book_author = (TextView)cardView.findViewById(R.id.info_text2);
        book_title.setText(titles.get(position));
        book_author.setText(authors.get(position));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView=cardView;
        }



    }

}
