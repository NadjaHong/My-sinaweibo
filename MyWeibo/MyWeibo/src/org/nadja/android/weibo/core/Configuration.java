package org.nadja.android.weibo.core;

/**
 * Configuration for weibo sdk api.
 */
public interface Configuration {

    String CONSUMER_KEY = "1279044609";

    String REDIRECT_CALLBACK_URL = "http://www.weibo.com";

    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";

    String API_SERVER  = "https://api.weibo.com/2";
}
