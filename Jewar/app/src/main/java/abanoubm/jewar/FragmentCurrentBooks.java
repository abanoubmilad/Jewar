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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentCurrentBooks extends Fragment {
    private ListView lv;
    private BookAdapter mAdapter;
    private DB mDB;
    private ProgressBar loading;
    private TextView alert;
    private Book chosenBook;
    private int chosenStatus;
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
    private class GetTask extends AsyncTask<Void, Void, ArrayList<Book>> {
        @Override
        protected void onPreExecute() {
            loading.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<Book> doInBackground(Void... params) {
            if (mDB == null)
                mDB = DB.getInstant(getContext());
            return mDB.getBooks();
        }

        @Override
        protected void onPostExecute(ArrayList<Book> stories) {
            if (getContext() == null)
                return;
            if (stories != null) {
                mAdapter.clearThenAddAll(stories);
                alert.setVisibility(stories.size() == 0 ? View.VISIBLE : View.GONE);

            } else {
                Toast.makeText(getActivity(), "error while trying to display! try again!", Toast.LENGTH_SHORT).show();
            }
            loading.setVisibility(View.GONE);


        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_current_books, container, false);


        lv = (ListView) root.findViewById(R.id.list);
        alert = (TextView) root.findViewById(R.id.alert);


        loading = (ProgressBar) root.findViewById(R.id.loading);

        mAdapter = new BookAdapter(getActivity(), new ArrayList<Book>(0));
        lv.setAdapter(mAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1,
                                    int position, long arg3) {
                chosenBook = mAdapter.getItem(position);
                final CharSequence[] choice = {"Add to own list", "Add to wish list", "Search for owners", "Search for owners using maps"};
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
                        if (which == 2) {
                            Intent intent = new Intent(getContext(), BookOwnersDisplay.class);
                            intent.putExtra("book_id", chosenBook.getID());
                            startActivity(intent);
                        } else if (which == 3) {
                            Intent intent = new Intent(getContext(), BookOwnersDisplayMap.class);
                            intent.putExtra("book_id", chosenBook.getID());
                            startActivity(intent);
                        } else {
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
        return root;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new GetTask().execute();
        }
    }
}
