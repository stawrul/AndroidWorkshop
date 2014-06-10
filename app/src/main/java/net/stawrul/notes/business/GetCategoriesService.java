package net.stawrul.notes.business;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import net.stawrul.notes.model.Category;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class GetCategoriesService extends IntentService {

    private static final String TAG = GetCategoriesService.class.getName();

    public GetCategoriesService() {
        super("GetCategoriesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG, "onHandleIntent");
        try {
            URL url = new URL("http://10.0.2.2:9999/categories");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() == 200) {
//                  conn.getInputStream()
                ObjectMapper mapper = new ObjectMapper();
                Category[] categories = mapper.readValue(conn.getInputStream(), Category[].class);
                for (Category c : categories) {

                    Log.e(TAG, c.getName());
                }

            } else {
                Log.e(TAG, "Error: " + conn.getResponseCode());
            }

        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
