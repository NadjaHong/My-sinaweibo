package org.nadja.android.weibo.core.models;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Geo implements Serializable {
    private static final long serialVersionUID = 5703295501784257267L;

    @SerializedName("type")
    public String type;

    public List<Float> coordinates;
}

