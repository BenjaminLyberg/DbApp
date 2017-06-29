package andben.com.dbapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Benjamin on 26.06.2017.
 */

// Adapter for ListView'et som brukes i MainActivity.

public class ArticleAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Article> mArticles;

    public ArticleAdapter(Context context, ArrayList<Article> articles){
        mContext = context;
        mArticles = articles;
    }

    @Override
    public int getCount() {
        return mArticles.size();
    }

    @Override
    public Object getItem(int position) {
        return mArticles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.article_list_item, null);
            holder = new ViewHolder();
            holder.articleImageView = (ImageView)convertView.findViewById(R.id.articleImageView);
            holder.headerTextView = (TextView) convertView.findViewById(R.id.headerTextView);
            holder.tagLabelTextView = (TextView) convertView.findViewById(R.id.tagLabelTextView);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Article article = mArticles.get(position);

        if(article.getType().equals("NativeAd")){
            Picasso.with(mContext).load(R.drawable.annonseplass).into(holder.articleImageView);
        } else {
            Picasso.with(mContext).load(article.getImage()).into(holder.articleImageView);
        }

        holder.headerTextView.setText(Html.fromHtml(article.getTitle()));

        // Switch-case for å finne ut hvilken label artikkelen har. Setter
        // label-TextViews farge og tekst basert på dette.

        String label = article.getLabel();
        switch (label){
            case "pluss":
                holder.tagLabelTextView.setBackgroundColor(Color.BLACK);
                holder.tagLabelTextView.setText("DAGBLADET PLUSS");
                break;
            case "dinside":
                holder.tagLabelTextView.setBackgroundColor(Color.BLACK);
                holder.tagLabelTextView.setText("DINSIDE");
                break;
            case "video":
                holder.tagLabelTextView.setBackgroundColor(Color.parseColor("#ed1c24"));
                holder.tagLabelTextView.setText("VIDEO");
                break;
            case "kommentar":
                holder.tagLabelTextView.setBackgroundColor(Color.parseColor("#009999"));
                holder.tagLabelTextView.setText("KOMMENTAR");
                break;
            default:
                holder.tagLabelTextView.setBackgroundColor(Color.WHITE);
        }



        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singlePageIntent = new Intent(mContext, SinglePageActivity.class);
                singlePageIntent.putExtra("image", article.getImage());
                singlePageIntent.putExtra("header", article.getTitle());
                mContext.startActivity(singlePageIntent);
            }
        });


        return convertView;
    }

    // ViewHolder inner class. For å unngå større mengder
    // kall til view.findViewById(int), som kan være kostbart.

    private static class ViewHolder {
        ImageView articleImageView;
        TextView headerTextView;
        TextView tagLabelTextView;
    }
}
