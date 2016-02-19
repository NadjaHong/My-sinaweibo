package org.nadja.android.weibo.toolbox;

import java.lang.ref.WeakReference;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;

import org.nadja.android.weibo.R;

/**
 * Implements ImageListener interface to perform fade-In animation of image when
 * download completes.
 */
public class FadeInImageListener implements ImageLoader.ImageListener {

    WeakReference<ImageView> mImageView;
    Context mContext;

    public FadeInImageListener(ImageView image, Context context) {
        mImageView = new WeakReference<ImageView>(image);
        mContext = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mImageView.get() != null) {
            mImageView.get().setImageResource(R.drawable.portrait_image_empty);
        }
    }

    @Override
    public void onResponse(ImageContainer response, boolean arg1) {
        if (mImageView.get() != null) {
            ImageView image = mImageView.get();
            if (response.getBitmap() != null) {
                image.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in));
                image.setImageBitmap(response.getBitmap());
            } else {
                image.setImageResource(R.drawable.portrait_image_empty);
            }
        }
    }
}

