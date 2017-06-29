package andben.com.listtester;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class SinglePageActivity extends AppCompatActivity {

    private ActionBar actionBar;
    private ImageView mSinglePageImageView;
    private TextView mHeaderTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_page);

        setActionBar();

        mSinglePageImageView = (ImageView) findViewById(R.id.singlePageImageView);
        mHeaderTextView = (TextView) findViewById(R.id.headerTextView);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("image");
        String header = intent.getStringExtra("header");
        Picasso.with(this).load(imageUrl).into(mSinglePageImageView);

        mHeaderTextView.setText(Html.fromHtml(header));
    }

    private void setActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeAsUpIndicator(R.drawable.crop);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
