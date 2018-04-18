package dimon.com.androidgallery;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

public class GalleryCardItem {
    private CardView cardView;
    private TextView textView;
    private RecyclerView recyclerView;
    private List<GalleryItem> list;

    public GalleryCardItem() {
    }

    public List<GalleryItem> getList() {
        return list;
    }

    public void setList(List<GalleryItem> list) {
        this.list = list;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }
}
