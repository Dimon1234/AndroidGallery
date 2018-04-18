package dimon.com.androidgallery;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity  extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private List<GalleryCardItem> cardItems = new ArrayList<>();
    private List<GalleryItem> galleryItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        new FetchItemTask().execute();
        setUpAdapter();
    }

    private void setUpAdapter()
    {
        recyclerView.setAdapter(new CardViewAdapter(cardItems));
    }


    private class CardViewHolder extends RecyclerView.ViewHolder
    {
        private GalleryCardItem cardItem;

        public CardViewHolder(View itemView) {
            super(itemView);
            cardItem = new GalleryCardItem();
            cardItem.setCardView((CardView) itemView.findViewById(R.id.card_view));
            cardItem.setTextView((TextView) itemView.findViewById(R.id.card_title));
            RecyclerView recyclerView = itemView.findViewById(R.id.photo_gallery_recycler_view);
            recyclerView.setLayoutManager(new GridLayoutManager(GalleryActivity.this, 3));
            cardItem.setRecyclerView(recyclerView);
        }

    }
    private class CardViewAdapter extends RecyclerView.Adapter<CardViewHolder> {

        private List<GalleryCardItem> list;

        public CardViewAdapter(List<GalleryCardItem> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(GalleryActivity.this);
            View view = inflater.inflate(R.layout.card_view_item, parent,  false);
            return new CardViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

            holder.cardItem.getRecyclerView().setAdapter(new PhotoAdapter(galleryItemList.subList(0, 6)));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }



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
            for(int i = 0; i < 10; i++)
            {
                GalleryCardItem galleryCardItem = new GalleryCardItem();
                galleryCardItem.setList(list.subList(0,2));
                cardItems.add(galleryCardItem);
            }

            setUpAdapter();
        }
    }
}