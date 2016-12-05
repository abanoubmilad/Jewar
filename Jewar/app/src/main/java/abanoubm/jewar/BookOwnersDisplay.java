package abanoubm.jewar;

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

    private class SearchTask extends AsyncTask<String, Void, ArrayList<BookOwner>> {
        @Override
        protected void onPreExecute() {
            loading.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<BookOwner> doInBackground(String... params) {
            return new ArrayList<BookOwner>();
        }

        @Override
        protected void onPostExecute(ArrayList<BookOwner> owners) {
            if (getApplicationContext() == null)
                return;
            if (owners != null) {
                mAdapter.clearThenAddAll(owners);
                if (owners.size() == 0) {
                    Toast.makeText(getApplicationContext(), "no book owners found!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), "error while trying to search! try again!", Toast.LENGTH_SHORT).show();
            }
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
    }


}
