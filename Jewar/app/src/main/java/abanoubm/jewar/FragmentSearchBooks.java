package abanoubm.jewar;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentSearchBooks extends Fragment {
    private ListView lv;
    private BookAdapter mAdapter;
    private DB mDB;
    private ProgressBar loading;
    private TextView alert;

    private Book chosenBook;
    private int chosenStatus;

    private class SearchTask extends AsyncTask<String, Void, ArrayList<Book>> {
        @Override
        protected void onPreExecute() {
            loading.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<Book> doInBackground(String... params) {

            ArrayList<Book> books = GoodReadsHTTPClient.searchBooksGoodReads(params[0]);
            if (mDB == null)
                mDB = DB.getInstant(getContext());
            mDB.updateStatusOfBooks(books);
            return books;
        }

        @Override
        protected void onPostExecute(ArrayList<Book> stories) {
            if (getContext() == null)
                return;
            if (stories != null) {
                mAdapter.clearThenAddAll(stories);
                alert.setVisibility(stories.size() == 0 ? View.VISIBLE : View.GONE);
            } else {
                Toast.makeText(getActivity(), "error while trying to search! try again!", Toast.LENGTH_SHORT).show();
            }
            loading.setVisibility(View.GONE);


        }
    }

    private class UpdateUserBook extends AsyncTask<Void, Void, Integer> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(getContext());
            pBar.setCancelable(false);
            pBar.setTitle("loading");
            pBar.setMessage("saving book to your list ....");
            pBar.show();
        }

        @Override
        protected Integer doInBackground(Void... params) {
            int result = JewarApi.add_list_book(chosenBook.getID(), chosenStatus);
            if (result == 7) {
                chosenBook.setStatus(chosenStatus);
                mDB.insertBook(chosenBook);
            }
            return result;
        }

        @Override
        protected void onPostExecute(Integer status) {
            switch (status.intValue()) {
                case -1:
                    Toast.makeText(getContext(), "check your internet connection!",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(getContext(), "connection timeout, login first!",
                            Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                    startActivity(new Intent(getContext(), SignIn.class));

                    break;
                case 1:
                    Toast.makeText(getContext(), "data not sent",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getContext(), "invalid params",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getContext(), "db error returned false",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "updated",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            pBar.dismiss();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search_books, container, false);


        lv = (ListView) root.findViewById(R.id.list);
        alert = (TextView) root.findViewById(R.id.alert);


        loading = (ProgressBar) root.findViewById(R.id.loading);
        final EditText input = (EditText) root.findViewById(R.id.input);

        mAdapter = new BookAdapter(getActivity(), new ArrayList<Book>(0));
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {
                chosenBook = mAdapter.getItem(position);
                final CharSequence[] choice = {"Add to own list", "Add to wish list","Search for owners"};
                if (chosenBook.getStatus() == DB.BOOK_STATUS_OWNED) {
                    choice[0] = "remove from own list";
                } else if (chosenBook.getStatus() == DB.BOOK_STATUS_SEEKING) {
                    choice[1] = "remove from wish list";
                }
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
                alertBuilder.setNegativeButton("Cancel", null);
                alertBuilder.setTitle(chosenBook.getTitle());
                alertBuilder.setSingleChoiceItems(choice, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==2){
                            Intent intent =new Intent(getContext(),BookOwnersDisplay.class);
                            intent.putExtra("book_id",chosenBook.getID());
                            startActivity(intent);
                        }else {
                            if (chosenBook.getStatus() == DB.BOOK_STATUS_OWNED) {
                                if (which == 0) {
                                    chosenStatus = 2;
                                } else {
                                    chosenStatus = DB.BOOK_STATUS_SEEKING;
                                }
                            } else if (chosenBook.getStatus() == DB.BOOK_STATUS_SEEKING) {
                                if (which == 0) {
                                    chosenStatus = DB.BOOK_STATUS_OWNED;
                                } else {
                                    chosenStatus = 2;

                                }
                            } else {
                                if (which == 0) {
                                    chosenStatus = DB.BOOK_STATUS_OWNED;
                                } else {
                                    chosenStatus = DB.BOOK_STATUS_SEEKING;

                                }
                            }
                            new UpdateUserBook().execute();
                        }
                        dialog.dismiss();
                    }
                });
                alertBuilder.create().show();
            }
        });
        root.findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = input.getText().toString().trim();
                if (str.length() > 0)
                    new SearchTask().execute(str);
            }
        });
        return root;
    }


}
