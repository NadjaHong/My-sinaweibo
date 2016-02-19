package org.nadja.android.weibo.util;

import android.content.Context;
import android.content.SharedPreferences;

/*
 *SharedPreferences本地保存
 */
public final class Preferences {

    public static final String PREFERENCES_NAME = "weibo_preferences";

    public static final String ACCESS_TOKEN = "access_token";

    public static final String EXPIRES_IN = "expires_in";

    public static final String PREF_LAST_SYNC_TIME = "last_sync_time";

    public static final String USER_ID = "user_id";

    public static SharedPreferences get(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
    }
}

