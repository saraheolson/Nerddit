package com.nerdery.solson.adapter;

import com.nerdery.solson.R;
import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.dependencyinjection.annotations.ForActivity;
import com.nerdery.solson.model.RedditComment;
import com.nerdery.solson.repository.RedditCommentRepository;
import com.nerdery.solson.view.CommentsViewHolder;

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
public class RedditCommentAdapter extends BaseAdapter {


    @Inject
    RedditCommentRepository mCommentRepository;

    private Context mContext;

    private List<RedditComment> mCommentsList;

    private LayoutInflater mInflater;

    private DateTimeFormatter mDateTimeFormatter;

    @Inject
    public RedditCommentAdapter(@ForActivity Context context) {
        mContext = context;
        ((BaseActivity) context).inject(this);
        mInflater = LayoutInflater.from(context);
        mCommentsList = new ArrayList<RedditComment>();
        mDateTimeFormatter = DateTimeFormat.forPattern("MM/dd/yyyy");
    }

    @Override
    public int getCount() {
        if (mCommentsList != null) {
            return mCommentsList.size();
        } else {
            return 0;
        }
    }

    @Override
    public RedditComment getItem(int position) {
        return mCommentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CommentsViewHolder commentsViewHolder;

        if (null == convertView) {
            commentsViewHolder = new CommentsViewHolder();
            convertView = mInflater.inflate(R.layout.list_item_link_list, parent, false);
            convertView.setTag(commentsViewHolder);
        } else {
            commentsViewHolder = (CommentsViewHolder) convertView.getTag();
        }
        commentsViewHolder.bindView(convertView);

        RedditComment comment = getItem(position);

        commentsViewHolder.setBodyText(comment.getBody());
        return convertView;
    }

    public void setList(List<RedditComment> commentsList) {
        mCommentsList = commentsList;
    }

    public void addCommentToList(RedditComment comment) {
        mCommentsList.add(comment);
    }

    public void clear() {
        mCommentsList.clear();
    }
}

