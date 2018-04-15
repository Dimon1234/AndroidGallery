package dimon.com.androidgallery;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity  extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private List<GalleryItem> galleryItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.photo_gallery_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        new FetchItemTask().execute();
        setUpAdapter();
    }

    private void setUpAdapter()
    {
        recyclerView.setAdapter(new PhotoAdapter(galleryItemList));
    }


    private class PhotoHolder extends RecyclerView.ViewHolder {
        private ImageView itemImageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.gallery_item);
        }
    }

    private class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {

        private List<GalleryItem> list;

        public PhotoAdapter(List<GalleryItem> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(GalleryActivity.this);
            View view = inflater.inflate(R.layout.gallery_item, parent,  false);
            return new PhotoHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PhotoHolder holder, int position) {
            GalleryItem galleryItem = list.get(position);
            String url = galleryItem.getURL();
            Log.d(TAG, url);
            Picasso.get().load(url).into(holder.itemImageView);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchItemTask extends AsyncTask<Void,Void, List<GalleryItem>>
    {

        @Override
        protected List<GalleryItem> doInBackground(Void... voids) {
            return new TestFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<GalleryItem> list) {
            galleryItemList = list;
            setUpAdapter();
        }
    }
}