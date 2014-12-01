package com.nerdery.solson.adapter;

import com.nerdery.solson.R;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.dependencyinjection.annotations.ForActivity;
import com.nerdery.solson.model.RedditLink;
import com.nerdery.solson.repository.RedditLinkRepository;
import com.nerdery.solson.viewholder.LinksViewHolder;
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
 * Adapter for a list of RedditLink objects.
 *
 * @author solson
 */
public class RedditLinkAdapter extends BaseAdapter {

    /**
     * Data repostiory for links.
     */
    @Inject
    RedditLinkRepository mRedditLinkRepository;

    /**
     * Activity context
     */
    private Context mContext;

    /**
     * List of RedditLink objects.
     */
    private List<RedditLink> mLinksList;

    /**
     * The layout inflater.
     */
    private LayoutInflater mInflater;

    /**
     * The date/time formatter.
     */
    private DateTimeFormatter mDateTimeFormatter;

    @Inject
    public RedditLinkAdapter(@ForActivity Context context) {
        mContext = context;
        ((BaseActivity) context).inject(this);
        mInflater = LayoutInflater.from(context);

        // Create the list of links.
        mLinksList = new ArrayList<RedditLink>();

        // Create the date/time formatter.
        mDateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy");
    }

    /**
     * Returns the size of the list.
     *
     * @return int number of links.
     */
    @Override
    public int getCount() {
        if (mLinksList != null) {
            return mLinksList.size();
        } else {
            return 0;
        }
    }

    /**
     * Returns the RedditLink object at the specified position.
     *
     * @param position int Index of desired link object.
     * @return RedditLink object.
     */
    @Override
    public RedditLink getItem(int position) {
        return mLinksList.get(position);
    }

    /**
     * Returns the ID of the link at the specified position.
     *
     * @param position int Index of desired link ID.
     * @return long ID of object.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Returns the view for the RedditLink object at the specified position.
     *
     * @param position    Index of RedditLink object.
     * @param convertView The view.
     * @param parent      The parent.
     * @return View The view for the link.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LinksViewHolder linksViewHolder;

        // Create or update the view
        if (null == convertView) {
            linksViewHolder = new LinksViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_link_list, parent, false);
            convertView.setTag(linksViewHolder);
        } else {
            linksViewHolder = (LinksViewHolder) convertView.getTag();
        }
        linksViewHolder.bindView(convertView);

        // Get the link object
        RedditLink link = getItem(position);

        // Get string resources
        String postedResources = mContext.getString(R.string.link_list_posted_info);
        String numComments = mContext.getString(R.string.link_list_num_comments);

        // Set values
        linksViewHolder.setTitleText(link.getTitle());
        linksViewHolder.setCommentsText(String.format(numComments, link.getNumComments()));
        linksViewHolder.setPostedText(
                String.format(postedResources, mDateTimeFormatter.print(link.getCreatedUtc()),
                        link.getAuthor()));
        linksViewHolder.setScoreText(Integer.toString(link.getScore()));

        /*
         * Load the image with Picasso.
         *
         * How sweet is this library? Well done, square.
         *
         * TO DEBUG IMG LOCATION: Picasso.with(mContext).setIndicatorsEnabled(true);
         */
        if ((link.getThumbnail() != null) && !link.getThumbnail().isEmpty()) {
            Picasso.with(mContext).load(link.getThumbnail()).into(linksViewHolder.getImageView());
        }

        return convertView;
    }

    /**
     * Returns the last link in the list.
     *
     * @return RedditLink the last link in the list.
     */
    public RedditLink getLastItem() {
        int size = mLinksList.size();
        return mLinksList.get(size - 1);
    }

    /**
     * Sets the list of RedditLink objects.
     *
     * @param linksList List of RedditLink objects.
     */
    public void setList(List<RedditLink> linksList) {
        mLinksList = linksList;
    }

    /**
     * Adds an individual RedditLink object to the list.
     *
     * @param link A RedditLink object.
     */
    public void addLink(RedditLink link) {
        mLinksList.add(link);
    }

    /**
     * Removes all data from the list.
     */
    public void clear() {
        mLinksList.clear();
    }
}
