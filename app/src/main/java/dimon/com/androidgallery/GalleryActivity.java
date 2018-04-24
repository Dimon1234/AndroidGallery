package dimon.com.androidgallery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

public class GalleryActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = findViewById(R.id.text);

        token = getIntent().toUri(Intent.URI_ALLOW_UNSAFE);
        token = token.substring(token.indexOf("access_token="));
        token = token.substring(token.indexOf("=") + 1, token.indexOf("&"));
        textView.setText(token);
    }


}