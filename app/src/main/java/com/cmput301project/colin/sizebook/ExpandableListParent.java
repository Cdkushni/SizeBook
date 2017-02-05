package com.cmput301project.colin.sizebook;

import java.util.ArrayList;

/**
 * Created by Colin on 2/4/2017.
 */

public class ExpandableListParent {
    private Object parent;
    private ArrayList<Object> parentChildren;

    public ExpandableListParent() {
    }
    public ExpandableListParent(Object parent, ArrayList<Object> parentChildren) {
        this.parent = parent;
        this.parentChildren = parentChildren;
    }

    public Object getParent() {
        return parent;
    }

    public void setParent(Object parent) {
        this.parent = parent;
    }

    public ArrayList<Object> getParentChildren() {
        return parentChildren;
    }

    public void setParentChildren(ArrayList<Object> parentChildren) {
        this.parentChildren = parentChildren;
    }
}
