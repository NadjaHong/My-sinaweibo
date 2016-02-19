package org.nadja.android.weibo;

import android.app.Application;
import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.BitmapAjaxCallback;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.nadja.android.weibo.core.Session;
import org.nadja.android.weibo.toolbox.BitmapLruCache;
import org.nadja.android.weibo.toolbox.OkHttpStack;

/**
 * WeiboApplication.
 */
public class WeiboApplication extends Application {

    /**
     * Log or request TAG.
     */
    public static final String TAG = "Weibo";

    private static WeiboApplication sWeiboApplication;

    private Oauth2AccessToken mOauth2AccessToken;

    @Override
    public void onCreate() {
        super.onCreate();

        sWeiboApplication = this;

        mOauth2AccessToken = Session.restore(sWeiboApplication);

        int memClass = ((ActivityManager) sWeiboApplication.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();
        // Use 1/8th of the available memory for this memory cache.
        int cacheSize = 1024 * 1024 * memClass / 8;
        mImageLoader = new ImageLoader(getRequestQueue(), new BitmapLruCache(cacheSize));

        // set the max number of concurrent network connections, default is 4
        AjaxCallback.setNetworkLimit(8);

        // set the max number of icons (image width <= 50) to be cached in
        // memory, default is 20
        BitmapAjaxCallback.setIconCacheLimit(40);

        // set the max number of images (image width > 50) to be cached in
        // memory, default is 20
        BitmapAjaxCallback.setCacheLimit(40);

        // set the max size of an image to be cached in memory, default is 1600
        // pixels (ie. 400x400)
        BitmapAjaxCallback.setPixelLimit(400 * 400);

        // set the max size of the memory cache, default is 1M pixels (4MB)
        BitmapAjaxCallback.setMaxPixelLimit(2000000);
    }

    @Override
    public void onLowMemory() {

        // clear all memory cached images when system is in low memory
        // note that you can configure the max image cache count, see
        // CONFIGURATION
        BitmapAjaxCallback.clearCache();
    }

    public static synchronized WeiboApplication getWeiboApplication() {
        return sWeiboApplication;
    }

    public Oauth2AccessToken getOauth2AccessToken() {
        return mOauth2AccessToken;
    }

    public void setOauth2AccessToken(Oauth2AccessToken token) {
        this.mOauth2AccessToken = token;
    }

    /**
     * Global request queue for Volley.
     */
    private RequestQueue mRequestQueue;

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        // lazy initialize the request queue, the queue instance will be
        // created when it is accessed for the first time
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new OkHttpStack());
        }

        return mRequestQueue;
    }

    private ImageLoader mImageLoader;

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * Adds the specified request to the global queue, if tag is specified
     * then it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);

        VolleyLog.d("Adding request to queue: %s", req.getUrl());

        getRequestQueue().add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set the default tag if tag is empty
        req.setTag(TAG);

        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important
     * to specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}

