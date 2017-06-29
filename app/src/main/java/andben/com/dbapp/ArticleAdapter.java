package andben.com.dbapp;

import android.content.Context;
import android.content.Intent;
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

public class ArticleAdapter extends BaseAdapter {

    public final String TAG = ArticleAdapter.class.getSimpleName();
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

    private static class ViewHolder {
        ImageView articleImageView;
        TextView headerTextView;
    }
}