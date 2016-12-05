package abanoubm.jewar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Utility {

    // convert from byte array to bitmap
    public static Bitmap getBitmap(byte[] image) {
        if (image == null)
            return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
