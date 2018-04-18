package dimon.com.androidgallery;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestFetcher {
    private static final String TAG = "TestFetcher";
    private static final String API_KEY = "3396f045a3a5b6a8dfb9a1352416a2bf";


    private String getJSONString(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();

        assert response.body() != null;
        String result = response.body().string();

        return result;
    }

    public List<GalleryItem> fetchItems() {
        List<GalleryItem> list = new ArrayList<>();
        try {
            String url = Uri.parse("https://api.flickr.com/services/rest")
                    .buildUpon()
                    .appendQueryParameter("method", "flickr.photos.getRecent")
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("format", "json")
                    .appendQueryParameter("nojsoncallback", "1")
                    .appendQueryParameter("extras", "url_s")
                    .build().toString();

            String json = getJSONString(url);
            JSONObject jsonBody = new JSONObject(json);
            parseItems(list, jsonBody);

            Log.d(TAG, "Executing url\n"+url);

        } catch (IOException e) {
            Log.d(TAG, "Ошибка загрузки данных");
        } catch (JSONException e) {
            Log.d(TAG, "Ошибка парсинга JSON");
        }

        return list;
    }

    private void parseItems(List<GalleryItem> listItems, JSONObject jsonBody) throws JSONException {
        JSONObject photosJsonObject = jsonBody.getJSONObject("photos");
        JSONArray photoJsonArray = photosJsonObject.getJSONArray("photo");


        for (int i = 0; i < photoJsonArray.length(); i++) {
            JSONObject jsonObject = photoJsonArray.getJSONObject(i);
            if (!jsonObject.has("url_s")) {
                continue;
            }
            GalleryItem galleryItem = new GalleryItem();

            galleryItem.setId(jsonObject.getString("id"));
            galleryItem.setTitle(jsonObject.getString("title"));

            galleryItem.setURL(jsonObject.getString("url_s"));
            listItems.add(galleryItem);
        }
    }
}
