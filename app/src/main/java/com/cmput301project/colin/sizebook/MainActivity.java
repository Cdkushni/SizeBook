package com.cmput301project.colin.sizebook;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * This class is the main activity of the project where all the views are handled.
 * User interactions are caught here with click listeners and dialogs are used for input
 * Buttons are also referenced here for deletion and addition of record entries
 * Saved data for the entries are saved in the for of "json" files that are stored in the
 * Emulator accessible from Android Device Monitor
 *
 * @author Colin Kushniruk
 * @version 1.0
 * @since 1.0
 */

public class MainActivity extends AppCompatActivity {

    /**
     * The file that the entries are saved in.
     * In JSON format.
     * @see #loadFromFile()
     * @see #saveInFile()
     */
    private static final String FILENAME = "SzBkFile.sav";

    /**
     * These are the datasets used for managing data stored in all of the entries
     */
    private ExpandableListView listView;
    private ExpListAdapter listAdapter;
    private List<String> listDataHeader;
    private List<String> tempDataHeader;
    private HashMap<String,List<String>> listHash;
    private HashMap<String,List<String>> tempHash;
    private List<customerRecord> custrecordsList;
    private List<customerRecord> tempCustRecords;

    /**
     * This is the input used for dialog data input
     */
    private EditText input;

    /**
     * This is the counter that is used to display the number of entries currently in existance
     */
    private TextView counter;

    /**
     * These integers are used for various counting trackers
     */
    private int currentItemIndex = 0;
    private int currentRecordIndex = 0;
    private int currentGroupSelection = -1;

    /**
     * These integers are used for the date picker
     */
    private int mYear, mMonth, mDay;

    /**
     * Called when the activity first starts
     * Holds the onclick events for all of the expandable list adapter's children
     * When a child is clicked, it will check what index that child is.
     *
     * <ul>
     *     <li>Index 0: Name - will use a standard EditText with no key restrictions</li>
     *     <li>Index 1: Date - will use a Date dialog to allow date entry in 'yyyy-MM-dd' form</li>
     *     <li>Index 2 - 7: Measurements(Inches) - will use a numeric input and convert to one decimal place</li>
     *     <li>Index 8: Comment - will use a standard EditText with no key restrictions</li>
     * </ul>
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get list view
        listView = (ExpandableListView)findViewById(R.id.lvExp);
        // preparing list data
        loadFromFile();
        //initData();

        //listAdapter = new ExpListAdapter(this,listDataHeader,listHash, custrecordsList);
        // setting list adapter
        //listView.setAdapter(listAdapter);
        // Listview on group click listener
        listView.setOnGroupClickListener(new OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, final int groupPosition, long l) {
                currentGroupSelection = groupPosition;
                return false;
            }
        });
        // Listview on child click listener
        listView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        final int groupPosition, final int childPosition, long id) {
                //selected item
                    AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
                    if (childPosition == 0){
                        alert.setTitle("Customer Name");
                        alert.setMessage("Enter a Name: ");
                        input = new EditText(MainActivity.this);
                    }else if(childPosition == 8){
                        alert.setTitle(custrecordsList.get(groupPosition).getRecord(childPosition));
                        alert.setMessage("Enter a Comment: ");
                        input = new EditText(MainActivity.this);
                    }

                    else if(childPosition == 1){
                        // Date Field code taken from http://stackoverflow.com/questions/39051210/how-to-give-input-date-field-for-registration-form-in-android
                        final Calendar myCalendar = Calendar.getInstance();
                        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                myCalendar.set(Calendar.YEAR, year);
                                myCalendar.set(Calendar.MONTH, month);
                                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                                String myFormat = "yyyy-MM-dd";
                                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                                //String value1 = sdf.format(myCalendar.getTime());
                            }
                        };
                        final Calendar c = Calendar.getInstance();

                        mYear = c.get(Calendar.YEAR);
                        mMonth = c.get(Calendar.MONTH);
                        mDay = c.get(Calendar.DAY_OF_MONTH);
                        // Launch Date Picker Dialog
                        DatePickerDialog dpd = new DatePickerDialog(MainActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        // Display Selected date in textbox


                                        if (year < mYear)
                                            view.updateDate(mYear,mMonth,mDay);

                                        if (monthOfYear < mMonth && year == mYear)
                                            view.updateDate(mYear,mMonth,mDay);

                                        if (dayOfMonth < mDay && year == mYear && monthOfYear == mMonth)
                                            view.updateDate(mYear,mMonth,mDay);
                                        String strMonth = String.format("%02d", monthOfYear+1);
                                        String strDay = String.format("%02d", dayOfMonth);
                                        String value = (year + "-" + strMonth + "-" + strDay);
                                        custrecordsList.get(groupPosition).setRecord(childPosition, value);
                                        List<String> NewData = new ArrayList<>();
                                        for (int i = 0; i < 9; i++) {
                                            NewData.add(custrecordsList.get(groupPosition).getRecord(i));
                                        }

                                        listHash.put(listDataHeader.get(groupPosition), NewData);
                                        listAdapter.notifyDataSetChanged();
                                        listAdapter.notifyDataSetInvalidated();
                                        saveInFile();
                                        listView.expandGroup(groupPosition, true);

                                    }
                                }, mYear, mMonth, mDay);
                        dpd.getDatePicker().setMinDate(1973);
                        dpd.show();

                    }else{
                        alert.setTitle(custrecordsList.get(groupPosition).getRecord(childPosition));
                        alert.setMessage("Enter a Measurement(inches): ");
                        input = new EditText(MainActivity.this);
                        if(childPosition != 8) {
                            input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                            input.setRawInputType(Configuration.KEYBOARD_12KEY);
                        }
                    }

                if (childPosition != 1) {
                    alert.setView(input);
                    alert.setButton(AlertDialog.BUTTON_POSITIVE, "Add",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String value = input.getText().toString();

                                    if (childPosition > 1 && childPosition < 8) {
                                        //Decimal formatting found: http://stackoverflow.com/questions/12027900/java-double-input///
                                        BigDecimal convertDec;
                                        DecimalFormat dFormat = new DecimalFormat("#.0");
                                        convertDec = new BigDecimal(value);
                                        value = dFormat.format(convertDec.doubleValue());
                                    }

                                    custrecordsList.get(groupPosition).setRecord(childPosition, value);
                                    List<String> NewData = new ArrayList<>();
                                    for (int i = 0; i < 9; i++) {
                                        NewData.add(custrecordsList.get(groupPosition).getRecord(i));
                                    }

                                    listHash.put(listDataHeader.get(groupPosition), NewData);
                                    listAdapter.notifyDataSetChanged();
                                    listAdapter.notifyDataSetInvalidated();
                                    saveInFile();
                                    dialog.dismiss();
                                    listView.expandGroup(groupPosition, true);
                                }
                            });
                    alert.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
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


    /**
     * Called when the delete button is clicked.
     * If there is no entry selected when this function is called
     * a dialog box will show up that will tell the user to select an entry first.
     * If there is a entry selected, this function will remove that entry and update the adapter
     * Also decrements the entry counter to keep track.
     * @param view
     */
    public void onDeleteClick(View view) {
        if (currentGroupSelection > -1) {
            AlertDialog deleteAlert = new AlertDialog.Builder(MainActivity.this).create();
            deleteAlert.setTitle("Are you sure you wish to delete this entry?");
            deleteAlert.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // recreate list with current record index
                            tempCustRecords = new ArrayList<customerRecord>();
                            tempDataHeader = new ArrayList<String>();
                            tempHash = new HashMap<String, List<String>>();

                            for (int c = 0; c < currentRecordIndex; c++) {
                                if (c != currentGroupSelection) {
                                    tempCustRecords.add(custrecordsList.get(c));
                                    tempDataHeader.add(listDataHeader.get(c));
                                    List<String> NewData = new ArrayList<>();
                                    for (int g = 0; g < 9; g++) {
                                        NewData.add(custrecordsList.get(c).getRecord(g));
                                    }
                                    tempHash.put(listDataHeader.get(c), listHash.get(listDataHeader.get(c)));
                                }
                            }
                            custrecordsList = tempCustRecords;
                            listDataHeader = tempDataHeader;
                            listHash = tempHash;

                            currentItemIndex--;
                            currentRecordIndex--;
                            currentGroupSelection = -1;

                            listAdapter = new ExpListAdapter(MainActivity.this, listDataHeader, listHash);
                            listView.setAdapter(listAdapter);

                            counter = (TextView) findViewById(R.id.itemCount);
                            counter.setText(Integer.toString(currentRecordIndex));

                            listAdapter.notifyDataSetChanged();
                            listAdapter.notifyDataSetInvalidated();
                            saveInFile();

                            dialogInterface.dismiss();
                        }
                    });
            deleteAlert.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            deleteAlert.show();
        }else{
            final AlertDialog badDeleteAlert = new AlertDialog.Builder(MainActivity.this).create();
            badDeleteAlert.setTitle("No Entry Selected");
            badDeleteAlert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            badDeleteAlert.dismiss();
                        }
                    });
            badDeleteAlert.show();
        }
    }

    /**
     * Called when the add button is clicked.
     * If there are currently no entries in the view, then this function will do an initiation of
     * the lists used to store all the data used for holding entry data.
     * If there are currently entries in the view, then the function can just add a new set of data
     * to the lists holding the entry data.
     * Also increments the entry counter to keep track
     * @param view
     */
    public void onAddClick(View view) {
        if (currentRecordIndex > 0) {
            customerRecord newRecord = new customerRecord("New Entry " + Integer.toString(currentRecordIndex));
            custrecordsList.add(newRecord);

            listDataHeader.add(custrecordsList.get(currentRecordIndex).getName());
            List<String> NewData = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                NewData.add(custrecordsList.get(currentRecordIndex).getRecord(i));
            }

            listHash.put(listDataHeader.get(currentItemIndex), NewData);
            listAdapter.notifyDataSetChanged();
            listAdapter.notifyDataSetInvalidated();
            for (int g = 0; g < currentRecordIndex; g++) {
                listView.collapseGroup(g);
            }
            listView.expandGroup(currentRecordIndex);
            currentRecordIndex++;
            currentItemIndex++;

            counter = (TextView) findViewById(R.id.itemCount);
            counter.setText(Integer.toString(currentRecordIndex));
        }else{
            custrecordsList = new ArrayList<customerRecord>();
            listDataHeader = new ArrayList<String>();
            listHash = new HashMap<String, List<String>>();

            customerRecord newRecord = new customerRecord("New Entry");
            custrecordsList.add(newRecord);

            listDataHeader.add(custrecordsList.get(currentRecordIndex).getName());
            List<String> NewData = new ArrayList<>();
            for (int i = 0; i < 9; i++){
                NewData.add(custrecordsList.get(currentRecordIndex).getRecord(i));
            }
            listHash.put(listDataHeader.get(currentItemIndex), NewData);
            currentItemIndex++;
            currentRecordIndex++;

            listAdapter = new ExpListAdapter(MainActivity.this, listDataHeader, listHash);
            listView.setAdapter(listAdapter);

            listAdapter.notifyDataSetChanged();
            listAdapter.notifyDataSetInvalidated();

            counter = (TextView) findViewById(R.id.itemCount);
            counter.setText(Integer.toString(currentRecordIndex));
        }
        saveInFile();
    }

    /**
     * Loads Entries from file in the form of the listHash that was stored.
     * Uses the listHash's keys to repopulate the listDataHeader string array
     * Uses the listHash's values to repopulate the custrecordsList with all the customerRecord
     * objects.
     * Uses the size of the list hash to reinitiate the currentRecordIndex and currentItemIndex
     * Uses the size of the listhash to reinitiate the entry counter
     * @exception FileNotFoundException if the file is not created
     */
    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();

            // Taken from http://stackoverflow.com/questions/12384064/gson-convert-from-json-to-a-typed-arraylistt
            // 2017-01-26 17:53:59
            // read in listHash and read items out of it for init
            listHash = gson.fromJson(in, new TypeToken<HashMap<String, List<String>>>(){}.getType());

            fis.close();

            custrecordsList = new ArrayList<customerRecord>();
            for (List<String> value : listHash.values()){
                customerRecord tempRecord = new customerRecord(value.get(0));
                for (int v = 1; v < 9; v++){
                    tempRecord.setRecord(v, value.get(v));
                }
                custrecordsList.add(tempRecord);
            }
            listDataHeader = new ArrayList<String>();
            for (String key : listHash.keySet()){
                listDataHeader.add(key);
            }
            currentRecordIndex = listHash.size();
            currentItemIndex = listHash.size();
            listAdapter = new ExpListAdapter(MainActivity.this, listDataHeader, listHash);
            listView.setAdapter(listAdapter);
            counter = (TextView) findViewById(R.id.itemCount);
            counter.setText(Integer.toString(currentRecordIndex));

        } catch (FileNotFoundException e) {
            custrecordsList = new ArrayList<customerRecord>();
            listDataHeader = new ArrayList<String>();
            listHash = new HashMap<String, List<String>>();
            listAdapter = new ExpListAdapter(MainActivity.this, listDataHeader, listHash);
            listView.setAdapter(listAdapter);
            counter = (TextView) findViewById(R.id.itemCount);
            counter.setText(Integer.toString(currentRecordIndex));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Saves Entries through the HashMap object in file in JSON format.
     * @throws FileNotFoundException if folder does not exist
     */
    private void saveInFile() {
        try {
            // save ExpListAdapter so we can read all data back out of it when we load
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter((fos)));

            Gson gson = new Gson();

            gson.toJson(listHash, out);// died here

            // can load listDataHeader items from listHash's keys
            // can get currentRecordIndex from size of listHash
            out.flush();

            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

}
