package com.example.alexr.todolist;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CompanyViewModel companyViewModel;
    public static final int NEW_COMPANY_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Intent intent = new Intent(MainActivity.this, NewCompanyActivity.class);
        startActivityForResult(intent, NEW_COMPANY_ACTIVITY_REQUEST_CODE);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        ); */

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CompanyListAdapter adapter = new CompanyListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        companyViewModel = ViewModelProviders.of(this).get(CompanyViewModel.class);

        companyViewModel.getAllCompanies().observe(this, new Observer<List<Company>>() {
            @Override
            public void onChanged(@Nullable final List<Company> companies) {
                // Update the cached copy of the words in the adapter.
                adapter.setCompanies(companies);
            }
        });


        /*
        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile"};


        final ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        } */

        /*
        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, list); */
        recyclerView.setAdapter(adapter);
        /*
        recyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                System.out.println(item);
                launchInfo(item);
                list.remove(item);
                adapter.notifyDataSetChanged();
            }

        });*/
        adapter.setOnClick(new OnCompanyClicked() {
            @Override
            public void onCompanyClick(Company company) {

                new CompanyRoomDatabase.DeleteEntryAsync(CompanyRoomDatabase.getDatabase(MainActivity.this)).execute(company.getId());

                System.out.println("Company Id: " + company.getId());

                adapter.notifyDataSetChanged();
            }
        });
    }

    public void launchInfo(String item) {
        Intent intt = new Intent(this, DescriptionActivity.class);
        intt.putExtra("Tech",item);
        startActivity(intt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_COMPANY_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Company company = new Company(0, data.getStringExtra(NewCompanyActivity.EXTRA_REPLY));
            companyViewModel.insert(company);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }
}
