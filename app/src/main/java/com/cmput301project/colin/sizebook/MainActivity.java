package com.cmput301project.colin.sizebook;

import android.app.ExpandableListActivity;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private List<customerRecord> custrecordsList;
    private int currentItemIndex = 0;
    private int currentRecordIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get list view
        listView = (ExpandableListView)findViewById(R.id.lvExp);
        // preparing list data
        initData();

        listAdapter = new ExpListAdapter(this,listDataHeader,listHash);
        // setting list adapter
        listView.setAdapter(listAdapter);
    }

    public void onAddClick(View view) {
        customerRecord newRecord = new customerRecord("Enter Name" + Integer.toString(currentItemIndex));
        custrecordsList.add(newRecord);

        listDataHeader.add(custrecordsList.get(currentRecordIndex).getName());
        List<String> NewData = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            NewData.add(custrecordsList.get(currentRecordIndex).getRecord(i));
        }
        listHash.put(listDataHeader.get(currentItemIndex), NewData);
        listAdapter = new ExpListAdapter(this, listDataHeader,listHash);
        listView.setAdapter(listAdapter);
        currentRecordIndex++;
        currentItemIndex++;
    }


    private void initData(){
        custrecordsList = new ArrayList<customerRecord>();
        listDataHeader = new ArrayList<String>();
        listHash = new HashMap<String, List<String>>();

        customerRecord newRecord = new customerRecord("Enter Name" + Integer.toString(currentItemIndex));
        custrecordsList.add(newRecord);

        listDataHeader.add(custrecordsList.get(currentRecordIndex).getName());
        List<String> NewData = new ArrayList<>();
        for (int i = 0; i < 8; i++){
            NewData.add(custrecordsList.get(currentRecordIndex).getRecord(i));
        }
        listHash.put(listDataHeader.get(currentItemIndex), NewData);
        currentItemIndex = 1;
        currentRecordIndex++;


    }
}
