package dz.souhilazidane.booksrecommender;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class UserCaptionedImagesAdapter extends RecyclerView.Adapter<UserCaptionedImagesAdapter.ViewHolder> {

    private ArrayList<String> first;
    private ArrayList<String> family;
    private ArrayList<String> year;
    private ArrayList<String> field;
    private Listener listener;

    interface Listener {
        void onClick(int position);
    }

    public UserCaptionedImagesAdapter(ArrayList<String> first, ArrayList<String> family, ArrayList<String> year, ArrayList<String> field) {
        this.first=first;
        this.family=family;
        this.year=year;
        this.field=field;
    }

    @Override
    public int getItemCount(){
        return first.size();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    @Override
    public UserCaptionedImagesAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    public void onBindViewHolder(ViewHolder holder,final int position){
        CardView cardView = holder.cardView;
        ImageView user_cover = (ImageView)cardView.findViewById(R.id.info_image);

        user_cover.setContentDescription(first.get(position)+" "+family.get(position));
        user_cover.setImageDrawable(ContextCompat.getDrawable(cardView.getContext(), R.drawable.ic_account));

        TextView user_full= (TextView)cardView.findViewById(R.id.info_text);
        TextView user_year = (TextView)cardView.findViewById(R.id.info_text2);
        TextView user_field = (TextView)cardView.findViewById(R.id.info_text3);
        user_full.setText(first.get(position)+" "+family.get(position));
        user_year.setText("Grade: "+year.get(position));
        user_field.setText("Field: "+field.get(position));

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
