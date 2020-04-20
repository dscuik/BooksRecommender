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
import java.util.List;

public class UsersFragment extends Fragment {

    //arrays of the users
    ArrayList<Integer> UserId;
    ArrayList<String> UserFirst;
    ArrayList<String> UserFamily;
    ArrayList<String> UserYear;
    ArrayList<String> UserField;

    public UsersFragment(ArrayList<Integer> UserId,ArrayList<String> UserFirst,ArrayList<String> UserFamily,ArrayList<String> UserYear,ArrayList<String> UserField) {
        this.UserId=UserId;
        this.UserFirst=UserFirst;
        this.UserFamily=UserFamily;
        this.UserYear=UserYear;
        this.UserField=UserField;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RecyclerView recycler = (RecyclerView)inflater.inflate(R.layout.fragment_users,container,false);


        UserCaptionedImagesAdapter adapter = new UserCaptionedImagesAdapter(UserFirst,UserFamily,UserYear,UserField);
        recycler.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(layoutManager);

        adapter.setListener(new UserCaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(),UserDetailActivity.class);

                intent.putExtra(UserDetailActivity.EXTRA_USER_FIRST,UserFirst.get(position));
                intent.putExtra(UserDetailActivity.EXTRA_USER_FAMILY,UserFamily.get(position));
                intent.putExtra(UserDetailActivity.EXTRA_USER_YEAR,UserYear.get(position));
                intent.putExtra(UserDetailActivity.EXTRA_USER_FIELD,UserField.get(position));
                intent.putExtra(UserDetailActivity.EXTRA_USER_ID,UserId.get(position));


                getActivity().startActivity(intent);
            }
        });


        return recycler;
    }

}
