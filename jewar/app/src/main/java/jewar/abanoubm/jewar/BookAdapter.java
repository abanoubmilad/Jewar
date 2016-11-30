package jewar.abanoubm.jewar;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BookAdapter extends ArrayAdapter<Book> {

    private int selected = -1;

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        Book book = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_book, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.rating = (TextView) convertView.findViewById(R.id.rating);
            holder.photo = (ImageView) convertView.findViewById(R.id.photo);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.root = convertView.findViewById(R.id.root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rating.setText(book.getRating());
        holder.author.setText(book.getAuthor());
        holder.title.setText(book.getTitle());

        if (book.getPhotoURL().length() > 0)
            Picasso.with(getContext()).load(book.getPhotoURL()).placeholder(R.mipmap.ic_def).into(holder.photo);
        else
            holder.photo.setBackgroundResource(R.mipmap.ic_def);

        if (selected == position)
            holder.root.setBackgroundColor(
                    ContextCompat.getColor(getContext(), R.color.colorAccent));
        else
            holder.root.setBackgroundResource(R.drawable.dynamic_bg);

        return convertView;
    }

    private static class ViewHolder {
        TextView title, author, rating;
        ImageView photo;
        View root;
    }

//    public void setSelectedIndex(int pos) {
//        selected = pos;
//        notifyDataSetChanged();
//    }

    public void clearThenAddAll(ArrayList<Book> list) {
        setNotifyOnChange(false);
        clear();
        for (Book Book : list)
            super.add(Book);

        setNotifyOnChange(true);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Book> list) {
        setNotifyOnChange(false);
        for (Book book : list)
            super.add(book);

        setNotifyOnChange(true);
        notifyDataSetChanged();
    }

    public void appendAllOnTop(ArrayList<Book> list) {
        setNotifyOnChange(false);
        for (int i = list.size() - 1; i > -1; i--)
            super.insert(list.get(i), 0);
        setNotifyOnChange(true);
        notifyDataSetChanged();

    }
}