package com.example.rss.presentation.global;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rss.R;

import java.util.List;
import java.util.Map;

public class AdapterExpandable extends BaseExpandableListAdapter {

    private List<? extends Map<String, String>> mGroupData;
    private int mGroupLayout;
    private String[] mGroupFrom;
    private int[] mGroupTo;

    private List<? extends List<? extends Map<String, String>>> mChildData;
    private int mChildLayout;
    private int mLastChildLayout;
    private String[] mChildFrom;
    private int[] mChildTo;

    private LayoutInflater mInflater;

    public AdapterExpandable(Context context,
                             List<? extends Map<String, String>> groupData,
                             int groupLayout,
                             String[] groupFrom,
                             int[] groupTo,
                             List<? extends List<? extends Map<String, String>>> childData,
                             int childLayout, String[] childFrom,
                             int[] childTo) {
        mGroupData = groupData;
        mGroupLayout = groupLayout;
        mGroupFrom = groupFrom;
        mGroupTo = groupTo;

        mChildData = childData;
        mChildLayout = childLayout;
        mLastChildLayout = childLayout;
        mChildFrom = childFrom;
        mChildTo = childTo;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getGroupCount() {
        return mGroupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mChildData.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mGroupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = mInflater.inflate(mGroupLayout, parent, false);
        } else {
            v = convertView;
        }

        TextView textView = v.findViewById(android.R.id.text1);
        ImageView imageView = v.findViewById(R.id.imageView2);

        textView.setText(mGroupData.get(groupPosition).get(mGroupFrom[0]));

        if (isExpanded)
            imageView.setImageResource(R.drawable.ic_arrow_dropdown_black_24dp);
        else
            imageView.setImageResource(R.drawable.ic_arrow_collapse_black_24dp);

        return v;
    }

    private View newChildView(boolean isLastChild, ViewGroup parent) {
        return mInflater.inflate((isLastChild) ? mLastChildLayout : mChildLayout, parent, false);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v;
        if (convertView == null) {
            v = newChildView(isLastChild, parent);
        } else {
            v = convertView;
        }
        TextView textView = v.findViewById(android.R.id.text1);
        textView.setText(mChildData.get(groupPosition).get(childPosition).get(mChildFrom[0]));
        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
