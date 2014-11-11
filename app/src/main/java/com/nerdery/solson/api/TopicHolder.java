package com.nerdery.solson.api;

import com.nerdery.solson.R;
import com.nerdery.solson.model.Topic;

import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by solson on 11/11/14.
 */
public class TopicHolder implements ViewHolder {

    @InjectView(R.id.list_item_topic_title)
    TextView title;

    private View parentView;
    private List<Topic> topics;

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    @Override
    public void bindView(View parentView) {
        ButterKnife.inject(this, parentView);
        this.parentView = parentView;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }
}
