package com.cmput301project.colin.sizebook;

import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView listView;
    private ExpListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private List<customerRecord> custrecordsList;
    private EditText input;
    private TextView counter;
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
        // Listview on child click listener
        listView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        final int groupPosition, final int childPosition, long id) {
                //selected item
                if (childPosition != 1){
                    AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                    if (childPosition == 0){
                        alert.setTitle("Customer Name");
                        alert.setMessage("Enter a Name: ");
                        input = new EditText(MainActivity.this);
                    }else{
                        alert.setTitle(custrecordsList.get(groupPosition).getRecord(childPosition));
                        alert.setMessage("Enter a Measurement(inches): ");
                        input = new EditText(MainActivity.this);
                        if(childPosition != 8) {
                            input.setInputType(InputType.TYPE_CLASS_NUMBER);
                            input.setRawInputType(Configuration.KEYBOARD_12KEY);
                        }
                    }

                    alert.setView(input);
                    alert.setButton(AlertDialog.BUTTON_POSITIVE, "Add",
                            new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String value = input.getText().toString();

                                    custrecordsList.get(groupPosition).setRecord(childPosition, value);
                                    List<String> NewData = new ArrayList<>();
                                    for (int i = 0; i < 9; i++){
                                        NewData.add(custrecordsList.get(groupPosition).getRecord(i));
                                    }

                                    listHash.put(listDataHeader.get(groupPosition), NewData);
                                    //listAdapter = new ExpListAdapter(MainActivity.this, listDataHeader, listHash);
                                    //listView.setAdapter(listAdapter);
                                    listAdapter.notifyDataSetChanged();
                                    listAdapter.notifyDataSetInvalidated();
                                    dialog.dismiss();
                                    listView.expandGroup(groupPosition,true);
                                }
                            });
                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alert.show();
                }
                return false;
            }
        });

    }

    public void onAddClick(View view) {
        customerRecord newRecord = new customerRecord("New Entry " + Integer.toString(currentRecordIndex));
        custrecordsList.add(newRecord);

        listDataHeader.add(custrecordsList.get(currentRecordIndex).getName());
        List<String> NewData = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            NewData.add(custrecordsList.get(currentRecordIndex).getRecord(i));
        }

        listHash.put(listDataHeader.get(currentItemIndex), NewData);
        listAdapter.notifyDataSetChanged();
        listAdapter.notifyDataSetInvalidated();
        for(int g = 0; g < currentRecordIndex; g++){
            listView.collapseGroup(g);
        }
        listView.expandGroup(currentRecordIndex);
        currentRecordIndex++;
        currentItemIndex++;

        counter = (TextView)findViewById(R.id.itemCount);
        counter.setText(Integer.toString(currentRecordIndex));
    }


    private void initData(){
        custrecordsList = new ArrayList<customerRecord>();
        listDataHeader = new ArrayList<String>();
        listHash = new HashMap<String, List<String>>();

        customerRecord newRecord = new customerRecord("Enter Name");
        custrecordsList.add(newRecord);

        listDataHeader.add(custrecordsList.get(currentRecordIndex).getName());
        List<String> NewData = new ArrayList<>();
        for (int i = 0; i < 9; i++){
            NewData.add(custrecordsList.get(currentRecordIndex).getRecord(i));
        }
        listHash.put(listDataHeader.get(currentItemIndex), NewData);
        currentItemIndex = 1;
        currentRecordIndex++;


    }
}
