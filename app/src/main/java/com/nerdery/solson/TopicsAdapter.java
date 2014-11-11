package com.nerdery.solson;

import com.nerdery.solson.activity.BaseActivity;
import com.nerdery.solson.api.TopicHolder;
import com.nerdery.solson.dependencyinjection.annotations.ForActivity;
import com.nerdery.solson.model.Topic;
import com.nerdery.solson.repository.TopicRepository;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * @author solson
 */
public class TopicsAdapter extends BaseAdapter {

//        private static final String YT_THUMBNAIL_URL_PREFIX = "http://img.youtube.com/vi/";
//        private static final String YT_THUMBNAIL_URL_SUFFIX = "/default.jpg";

        @Inject
        TopicRepository mTopicRepository;

        private Context mContext;
        private List<Topic> mTopicsList;
        private LayoutInflater mInflater;


        private List<Topic> mTopicsToRemove;
        private List<Topic> mBackupTopicsList;

        @Inject
        public TopicsAdapter(@ForActivity Context context) {
            mContext = context;
            ((BaseActivity) context).inject(this);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (mTopicsList != null) {
                return mTopicsList.size();
            }
            else return 0;
        }

        @Override
        public Topic getItem(int position) {
            return mTopicsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TopicHolder topicHolder;

            if (null == convertView) {
                topicHolder = new TopicHolder();
                convertView = mInflater.inflate(R.layout.list_item_topic_list, parent, false);
                convertView.setTag(topicHolder);
            } else {
                topicHolder = (TopicHolder) convertView.getTag();
            }
            topicHolder.bindView(convertView);

            Topic topic = getItem(position);

            topicHolder.setTitle(topic.getTitle());

            return convertView;
        }

        public void setList(List<Topic> topicsList) {
            mTopicsList = topicsList;
        }
}
