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
 * This adapter reprents a expandable list adapter extending the BaseExpandableListAdapter
 * It is created with a list of header data and a list hashmap data which is used to
 * populate it's group and children views and to allow for easy updating of the views by user.
 * Created by Colin on 2/3/2017.
 */

public class ExpListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    /**
     * Instantiates the adapter with a dataheader list, a hashmap under the main activity context.
     * @param context
     * @param listDataHeader
     * @param listHashMap
     */
    public ExpListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader; // header data
        this.listHashMap = listHashMap; // child data
    }

    /**
     * Gets the number of groups in the adapter
     * @return
     */
    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    /**
     * gets the number of children in a group
     * @param i
     * @return
     */
    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    /**
     * gets the group object at the index i
     * @param i
     * @return
     */
    @Override
    public Object getGroup(int i) {
            return listHashMap.get(listDataHeader.get(i)).get(0);
    }

    /**
     * gets the child object at the group index i, child i1
     * @param i
     * @param i1
     * @return
     */
    @Override
    public Object getChild(int i, int i1) {
        // i = group position, i1 = item position
        return listHashMap.get(listDataHeader.get(i)).get(i1);
    }

    /**
     * gets the Id of the group passed in
     * @param i
     * @return
     */
    @Override
    public long getGroupId(int i) {
        return i;
    }

    /**
     * gets the id of the child at the index of the group passed in
     * @param i
     * @param i1
     * @return
     */
    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    /**
     * Does not have stable Ids
     * @return
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Sets up default header title for group
     * Inflates view if there is none
     * Sets the text of the listheader to the default header title
     * @param i
     * @param isExpanded
     * @param view
     * @param parent
     * @return
     */
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

    /**
     * gets the default child text
     * inflates the child view if not inflated
     * sets the child text to the default for that child index.
     * @param i
     * @param i1
     * @param isLastChild
     * @param view
     * @param parent
     * @return
     */
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

    /**
     * Children are selectable
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
