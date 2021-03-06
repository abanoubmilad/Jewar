package abanoubm.jewar;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class HTTPClient {
    private static HTTPClient http;
    private static OkHttpClient client;

    private HTTPClient() {
        client = new OkHttpClient();
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        client.setCookieHandler(cookieManager);

    }

    public static HTTPClient getInstance() {
        if (http == null)
            http = new HTTPClient();
        return http;
    }

    public String getStrResponse(String url) {

        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            return null;
        }

    }

    public JSONObject get(String url) {

        Request request = new Request.Builder().url(url).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String str = response.body().string();
            System.out.println("response before: " + str);
            str = str.replaceAll(">.+<", "><").replaceAll("<.+>", "");
            System.out.println("response: " + str);
            if (response.isSuccessful())
                try {
                    return new JSONObject(str);
                } catch (JSONException e) {
                    return null;
                }
            return null;
        } catch (IOException e) {
            return null;
        }

    }

    public JSONObject POST(String url, RequestBody body) {

        Request request = new Request.Builder().url(url).post(body).build();
        Response response;
        try {
            response = client.newCall(request).execute();
            String str = response.body().string();
            System.out.println("response before: " + str);
            str = str.replaceAll(">.+<", "><").replaceAll("<.+>", "");
            System.out.println("response: " + str);
            if (response.isSuccessful())
                try {
                    return new JSONObject(str);
                } catch (JSONException e) {
                    return null;
                }
            return null;
        } catch (IOException e) {
            return null;
        }

    }

}
