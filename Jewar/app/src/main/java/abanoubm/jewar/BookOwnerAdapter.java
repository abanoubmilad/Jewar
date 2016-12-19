package abanoubm.jewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookOwnerAdapter extends AbstractAdapter<BookOwner> {

    public BookOwnerAdapter(Context context, ArrayList<BookOwner> bookOwners) {
        super(context, 0, bookOwners);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        BookOwner owner = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_owner, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.email = (TextView) convertView.findViewById(R.id.email);
            holder.photo = (ImageView) convertView.findViewById(R.id.photo);
            holder.mobile = (TextView) convertView.findViewById(R.id.mobile);
            holder.root = convertView.findViewById(R.id.root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.photo.setImageResource(R.mipmap.ic_photo_def);
        }
//        if (holder.bitmap != null)
//            holder.bitmap.recycle();
//        holder.bitmap = Utility.getBitmap(owner.getPhoto());
//        if (holder.bitmap != null)
//            holder.photo.setImageBitmap(holder.bitmap);
//        else

        holder.name.setText(owner.getName());
        holder.mobile.setText(owner.getMobile());
        holder.email.setText(owner.getEmail());


        return convertView;
    }

    private static class ViewHolder {
        TextView name;
        TextView email;
        TextView mobile;
        ImageView photo;
        View root;

    }

}