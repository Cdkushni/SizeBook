package com.cmput301project.colin.sizebook;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Colin on 2/3/2017.
 */

public class ExpListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    public List<customerRecord> getCurrentRecord() {
        return currentRecord;
    }

    public void setCurrentRecord(List<customerRecord> currentRecord) {
        this.currentRecord = currentRecord;
    }

    public List<String> getListDataHeader() {
        return listDataHeader;
    }

    public void setListDataHeader(List<String> listDataHeader) {
        this.listDataHeader = listDataHeader;
    }

    public HashMap<String, List<String>> getListHashMap() {
        return listHashMap;
    }

    public void setListHashMap(HashMap<String, List<String>> listHashMap) {
        this.listHashMap = listHashMap;
    }

    private List<customerRecord> currentRecord;

    public ExpListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader; // header data
        this.listHashMap = listHashMap; // child data
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
            return listHashMap.get(listDataHeader.get(i)).get(0);
    }

    @Override
    public Object getChild(int i, int i1) {
        // i = group position, i1 = item position
        return listHashMap.get(listDataHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean isExpanded, View view, ViewGroup parent) {
        String hdrTitle = (String)getGroup(i);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_group,null);
        }
        TextView lblListHeader = (TextView)view.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);

        lblListHeader.setText(hdrTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean isLastChild, View view, ViewGroup parent) {
        final String childText = (String)getChild(i,i1);
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item,null);
        }

        TextView txtListChild = (TextView)view.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        return view;
    }
    public void removeGroup(int group) {
        //remove children of group

        for (int g = 0; g < 9; g++) {
            listHashMap.remove(listHashMap.get(listDataHeader.get(group)).get(g));
        }
        // remove group
        listHashMap.remove(listDataHeader.get(group));
        listDataHeader.remove(group);
        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
