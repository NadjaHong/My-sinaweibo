package org.nadja.android.weibo.ui;
/*
 * 首页顶部菜单
 */
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;

import org.nadja.android.weibo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuFragment extends SherlockListFragment {

    //String[] list_items = getSherlockActivity().getResources().getStringArray(R.array.slide_list_items);
    private String[] mListItems = new String[] {"Home", "Exit"};

    private int[] mIcons = new int[] {
        R.drawable.ic_menu_home,
        R.drawable.ic_menu_exit,
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < mListItems.length; i++) {
            Map<String, Object> listItem = new HashMap<String, Object>();
            listItem.put("values", mListItems[i]);
            listItem.put("images", mIcons[i]);
            listItems.add(listItem);
        }
        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listItems,
                R.layout.slide, new String[] { "values", "images" },
                new int[] { R.id.slide_list_title, R.id.slide_list_icon });
        setListAdapter(adapter);
    }
}

