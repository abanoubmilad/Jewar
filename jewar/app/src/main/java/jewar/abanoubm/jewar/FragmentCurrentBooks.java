package jewar.abanoubm.jewar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentCurrentBooks extends Fragment {
    private ListView lv;
    private BookAdapter mAdapter;
    private DB mDB;
    private ProgressBar loading;

    private class GetTask extends AsyncTask<Integer, Void, ArrayList<Book>> {
        @Override
        protected void onPreExecute() {
            loading.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<Book> doInBackground(Integer... params) {
            if (mDB == null)
                mDB = DB.getInstant(getContext());
            return mDB.getBooks(params[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<Book> stories) {
            if (getContext() == null)
                return;
            if (stories != null) {
                mAdapter.clearThenAddAll(stories);
                if (stories.size() == 0) {
                    Toast.makeText(getActivity(), "no books exist!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "error while trying to display! try again!", Toast.LENGTH_SHORT).show();
            }
            loading.setVisibility(View.GONE);


        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_books, container, false);


        lv = (ListView) root.findViewById(R.id.list);


        loading = (ProgressBar) root.findViewById(R.id.loading);

        mAdapter = new BookAdapter(getActivity(), new ArrayList<Book>(0));
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {

            }
        });

        return root;
    }


}
