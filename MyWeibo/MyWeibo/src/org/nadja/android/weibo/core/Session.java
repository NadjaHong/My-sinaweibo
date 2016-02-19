package org.nadja.android.weibo.core;

import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.nadja.android.weibo.util.Preferences;

/**
 * utility class for storing and retrieving Weibo session data.
 */
public class Session {
    /**
     * Stores the session data on disk.
     *
     * @param context Activity 上下文环境
     * @param token Oauth2AccessToken
     */
    public static void save(Context context, Oauth2AccessToken token) {
        SharedPreferences pref = Preferences.get(context);
        Editor editor = pref.edit();
        editor.putString(Preferences.ACCESS_TOKEN, token.getToken());
        editor.putLong(Preferences.EXPIRES_IN, token.getExpiresTime());
        editor.commit();
    }

    /**
     * Clears the saved session data.
     *
     * @param context
     */
    public static void clearSavedSession(Context context) {
        SharedPreferences pref = Preferences.get(context);
        Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * Loads the session data from disk.
     *
     * @param context
     * @return Oauth2AccessToken
     */
    public static Oauth2AccessToken restore(Context context) {
        Oauth2AccessToken token = new Oauth2AccessToken();
        SharedPreferences pref = Preferences.get(context);
        token.setToken(pref.getString(Preferences.ACCESS_TOKEN, ""));
        token.setExpiresTime(pref.getLong(Preferences.EXPIRES_IN, 0));
        return token;
    }
}
