package com.example.android.classify;


/*
    interface that implements an onClick for the group headers within the expandable list view
    The interface is initialized in ExpandableListAdapter.java, and is called when 'add grade' image is clicked
    @param group_id is passed the group position which will be used by the activity in order  to retrieve the correct group
 */
public interface OnImageClickListener {
    public void OnImageClicked(int group_id);
    public void OnChildImageClicked(int group_id, int child_id);
}
