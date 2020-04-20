package dz.souhilazidane.booksrecommender;


import android.os.AsyncTask;
import android.view.View;

import java.net.URL;

public class ConnectionTask extends AsyncTask<URL,Void,String> {


    private TaskInterface task;

    public ConnectionTask(TaskInterface task) {
        this.task=task;
    }

    @Override
    protected void onPreExecute() {
        if(LoginActivity.progress != null) LoginActivity.progress.setVisibility(View.VISIBLE);
        if(UserActivity.progress != null) UserActivity.progress.setVisibility(View.VISIBLE);
        if(BookDetailActivity.progress != null) BookDetailActivity.progress.setVisibility(View.VISIBLE);
        if(UserDetailActivity.progress != null) UserDetailActivity.progress.setVisibility(View.VISIBLE);
        if(SearchActivity.progress != null) SearchActivity.progress.setVisibility(View.VISIBLE);
        if(PasswordActivity.progress != null) PasswordActivity.progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected String doInBackground(URL... urls) {
        return HttpUtils.GetData(urls[0]);
    }

    @Override
    protected void onPostExecute(String results) {
        if(LoginActivity.progress != null) LoginActivity.progress.setVisibility(View.INVISIBLE);
        if(UserDetailActivity.progress != null) UserDetailActivity.progress.setVisibility(View.INVISIBLE);
        if(UserActivity.progress != null) UserActivity.progress.setVisibility(View.INVISIBLE);
        if(SearchActivity.progress != null) SearchActivity.progress.setVisibility(View.INVISIBLE);
        if(PasswordActivity.progress != null) PasswordActivity.progress.setVisibility(View.INVISIBLE);
        task.OnFinished(results);
    }

}
