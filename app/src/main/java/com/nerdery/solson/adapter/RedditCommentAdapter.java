package com.nerdery.solson.adapter;

import com.nerdery.solson.R;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.dependencyinjection.annotations.ForActivity;
import com.nerdery.solson.model.RedditComment;
import com.nerdery.solson.repository.RedditCommentRepository;
import com.nerdery.solson.viewholder.CommentsViewHolder;

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
 * Adapter for a list of RedditComments.
 *
 * @author solson
 */
public class RedditCommentAdapter extends BaseAdapter {


    /**
     * The data repository for comment objects
     */
    @Inject
    RedditCommentRepository mCommentRepository;

    /**
     * The owning activity context
     */
    private Context mContext;

    /**
     * The list of comments.
     */
    private List<RedditComment> mCommentsList;

    /**
     * The layout inflater.
     */
    private LayoutInflater mInflater;

    /**
     * The date/time formatter.
     */
    private DateTimeFormatter mDateTimeFormatter;

    @Inject
    public RedditCommentAdapter(@ForActivity Context context) {
        mContext = context;
        ((BaseActivity) context).inject(this);
        mInflater = LayoutInflater.from(context);

        // Create the comments list.
        mCommentsList = new ArrayList<RedditComment>();

        // Create the date/time formatter.
        mDateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy");
    }

    /**
     * Returns the size of the comments list.
     *
     * @return int size
     */
    @Override
    public int getCount() {
        if (mCommentsList != null) {
            return mCommentsList.size();
        } else {
            return 0;
        }
    }

    /**
     * Returns the comments object at the specified position.
     *
     * @param position int Comment's index
     * @return RedditComment object.
     */
    @Override
    public RedditComment getItem(int position) {
        return mCommentsList.get(position);
    }

    /**
     * Returns the last comment in the list.
     *
     * @return RedditComment the last comment in the list.
     */
    public RedditComment getLastItem() {
        int size = mCommentsList.size();
        return mCommentsList.get(size - 1);
    }

    /**
     * Gets the ID of the item at the given position.
     *
     * @param position int Comment's index
     * @return long Comment's ID.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Creates the view for each comment in the list.
     *
     * @param position    Position of the comment in the list.
     * @param convertView The view.
     * @param parent      The parent.
     * @return View the Comment's view.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CommentsViewHolder commentsViewHolder;

        // Create or update the view.
        if (null == convertView) {
            commentsViewHolder = new CommentsViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_comment, parent, false);
            convertView.setTag(commentsViewHolder);
        } else {
            commentsViewHolder = (CommentsViewHolder) convertView.getTag();
        }
        commentsViewHolder.bindView(convertView);

        // Get the comment object for this position.
        RedditComment comment = getItem(position);

        // Display the body text.
        commentsViewHolder.setBodyText(comment.getBody());

        return convertView;
    }

    /**
     * Set the list of comments.
     *
     * @param commentsList List of RedditComment objects.
     */
    public void setList(List<RedditComment> commentsList) {
        mCommentsList = commentsList;
    }

    /**
     * Add a RedditComment object to the list.
     *
     * @param comment A RedditComment object.
     */
    public void addCommentToList(RedditComment comment) {
        mCommentsList.add(comment);
    }

    /**
     * Remove all comments from the list.
     */
    public void clear() {
        mCommentsList.clear();
    }
}

