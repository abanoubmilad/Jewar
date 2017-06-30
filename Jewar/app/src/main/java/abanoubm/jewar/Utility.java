package abanoubm.jewar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Utility {

    public static void clearLogin(Context context) {
        context.getSharedPreferences("login",
                Context.MODE_PRIVATE).edit().clear().apply();
    }
    // convert from byte array to bitmap
    public static Bitmap getBitmap(byte[] image) {
        if (image == null)
            return null;
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static String getLoginPassword(Context context) {
        return context.getSharedPreferences("login",
                Context.MODE_PRIVATE).getString("password", "");
    }

    public static String getLoginEmail(Context context) {
        return context.getSharedPreferences("login",
                Context.MODE_PRIVATE).getString("email", "");
    }

    public static void setLogin(Context context, String email, String password) {
        context.getSharedPreferences("login",
                Context.MODE_PRIVATE).edit().putString("email", email).putString("password", password).apply();
    }
}
