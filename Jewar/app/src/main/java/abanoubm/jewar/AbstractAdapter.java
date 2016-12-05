package abanoubm.jewar;

import android.content.Context;
import android.os.Build;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class AbstractAdapter<T> extends ArrayAdapter<T> {

    public AbstractAdapter(Context context, int resource, ArrayList<T> entries) {
        super(context, resource, entries);
    }

    public void clearThenAddAll(ArrayList<T> list) {
        setNotifyOnChange(false);
        clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.addAll(list);
        } else {
            for (T element : list)
                super.add(element);

        }
        setNotifyOnChange(true);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<T> list) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.addAll(list);
        } else {
            setNotifyOnChange(false);
            for (T element : list)
                super.add(element);
            setNotifyOnChange(true);
            notifyDataSetChanged();
        }
    }
    public void appendAllOnTop(ArrayList<T> list) {
        setNotifyOnChange(false);
        for (int i = list.size() - 1; i > -1; i--)
            super.insert(list.get(i), 0);
        setNotifyOnChange(true);
        notifyDataSetChanged();

    }
}