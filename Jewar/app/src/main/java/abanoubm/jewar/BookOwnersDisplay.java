package abanoubm.jewar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class BookOwnersDisplay extends FragmentActivity {
    private ListView lv;
    private BookOwnerAdapter mAdapter;
    private ProgressBar loading;

    private class SearchTask extends AsyncTask<String, Void, APIResponse> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            loading.setVisibility(View.VISIBLE);
            pBar = new ProgressDialog(BookOwnersDisplay.this);
            pBar.setCancelable(false);
            pBar.setTitle("loading");
            pBar.setMessage("signing in ....");
            pBar.show();
        }

        @Override
        protected APIResponse doInBackground(String... params) {
            return JewarApi.get_owners_of_book(params[0]);
        }

        @Override
        protected void onPostExecute(APIResponse response) {
            if (getApplicationContext() == null)
                return;
            switch (response.getStatus()) {
                case -1:
                    Toast.makeText(getApplicationContext(), "check your internet connection!",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), "connection timeout, login first!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(BookOwnersDisplay.this, SignIn.class));

                    break;
                case 7:
                    ArrayList<BookOwner> owners = (ArrayList<BookOwner>) response.getData();
                    if (owners != null) {
                        mAdapter.clearThenAddAll(owners);
                        if (owners.size() == 0) {
                            Toast.makeText(getApplicationContext(), "no book owners found!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "error while trying to search! try again!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
            pBar.dismiss();

            loading.setVisibility(View.GONE);


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_owners_display);


        lv = (ListView) findViewById(R.id.list);
        loading = (ProgressBar) findViewById(R.id.loading);
        mAdapter = new BookOwnerAdapter(getApplicationContext(), new ArrayList<BookOwner>(0));
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {

            }
        });


        String bookID = getIntent().getStringExtra("book_id");
        new SearchTask().execute(bookID);
    }


}
