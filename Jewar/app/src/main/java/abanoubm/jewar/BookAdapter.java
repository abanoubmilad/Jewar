package abanoubm.jewar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends AbstractAdapter<Book> {

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
            holder.status = (TextView) convertView.findViewById(R.id.status);
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


        holder.status.setText(book.getStatus() == DB.BOOK_STATUS_OWNED ?
                "own list" : book.getStatus() == DB.BOOK_STATUS_SEEKING ?
                "wish list" : "add to list!");


        if (book.getPhotoURL().length() > 0)
            Picasso.with(getContext()).load(book.getPhotoURL()).placeholder(R.mipmap.ic_def).into(holder.photo);
        else
        holder.photo.setBackgroundResource(R.mipmap.ic_def);

//        if (selected == position)
//            holder.root.setBackgroundColor(
//                    ContextCompat.getColor(getContext(), R.color.colorAccent));
//        else
//            holder.root.setBackgroundResource(R.drawable.dynamic_bg);

        return convertView;
    }

    private static class ViewHolder {
        TextView title, author, rating, status;
        ImageView photo;
        View root;
    }


}