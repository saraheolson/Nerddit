package com.nerdery.solson.adapter;

import com.nerdery.solson.R;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.view.LinksViewHolder;
import com.nerdery.solson.model.RedditLink;
import com.nerdery.solson.dependencyinjection.annotations.ForActivity;
import com.nerdery.solson.repository.RedditLinkRepository;
import com.squareup.picasso.Picasso;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * @author solson
 */
public class RedditLinkAdapter extends BaseAdapter {

    @Inject
    RedditLinkRepository mRedditLinkRepository;

    private Context mContext;

    private List<RedditLink> mLinksList;

    private LayoutInflater mInflater;

    private DateTimeFormatter mDateTimeFormatter;

    @Inject
    public RedditLinkAdapter(@ForActivity Context context) {
        mContext = context;
        ((BaseActivity) context).inject(this);
        mInflater = LayoutInflater.from(context);
        mLinksList = new ArrayList<RedditLink>();
        mDateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy");
    }

    @Override
    public int getCount() {
        if (mLinksList != null) {
            return mLinksList.size();
        } else {
            return 0;
        }
    }

    @Override
    public RedditLink getItem(int position) {
        return mLinksList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LinksViewHolder linksViewHolder;

        if (null == convertView) {
            linksViewHolder = new LinksViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_link_list, parent, false);
            convertView.setTag(linksViewHolder);
        } else {
            linksViewHolder = (LinksViewHolder) convertView.getTag();
        }
        linksViewHolder.bindView(convertView);

        RedditLink link = getItem(position);

        linksViewHolder.setTitleText(link.getTitle());
        linksViewHolder.setCommentsText(link.getNumComments() + " comments");
        linksViewHolder.setPostedText("Posted on " + mDateTimeFormatter.print(link.getCreatedUtc()) + " by " + link.getAuthor());
        linksViewHolder.setScoreText("Score: " + link.getScore());

        /*
         * Load the image with Picasso.
         *
         * How sweet is this library? Well done, square.
         *
         * TO DEBUG IMG LOCATION: Picasso.with(mContext).setIndicatorsEnabled(true);
         */
        Picasso.with(mContext).load(link.getThumbnail()).into(linksViewHolder.getImageView());

        return convertView;
    }

    public void setList(List<RedditLink> linksList) {
        mLinksList = linksList;
    }

    public void addLink(RedditLink link) {
        mLinksList.add(link);
    }

    public void clear() {
        mLinksList.clear();
    }
}
