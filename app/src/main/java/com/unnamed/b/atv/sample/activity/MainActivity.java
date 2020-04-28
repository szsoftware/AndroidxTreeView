package com.unnamed.b.atv.sample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.unnamed.b.atv.sample.databinding.ActivityMainBinding;
import com.unnamed.b.atv.sample.fragment.CustomViewHolderFragment;
import com.unnamed.b.atv.sample.fragment.FolderStructureFragment;
import com.unnamed.b.atv.sample.fragment.SelectableTreeFragment;
import com.unnamed.b.atv.sample.fragment.TwoDScrollingArrowExpandFragment;
import com.unnamed.b.atv.sample.fragment.TwoDScrollingFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final LinkedHashMap<String, Class<?>> listItems = new LinkedHashMap<>();
        listItems.put("Folder Structure Example", FolderStructureFragment.class);
        listItems.put("Custom Holder Example", CustomViewHolderFragment.class);
        listItems.put("Selectable Nodes", SelectableTreeFragment.class);
        listItems.put("2d scrolling", TwoDScrollingFragment.class);
        listItems.put("Expand with arrow only", TwoDScrollingArrowExpandFragment.class);


        final List<String> list = new ArrayList<>(listItems.keySet());
        final ListView listview = binding.listview;
        final SimpleArrayAdapter adapter = new SimpleArrayAdapter(this, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener((parent, view, position, id) -> {
            Class<?> clazz = listItems.values().toArray(new Class<?>[]{})[position];
            Intent i = new Intent(MainActivity.this, SingleFragmentActivity.class);
            i.putExtra(SingleFragmentActivity.FRAGMENT_PARAM, clazz);
            MainActivity.this.startActivity(i);
        });

    }

    private static class SimpleArrayAdapter extends ArrayAdapter<String> {
        public SimpleArrayAdapter(Context context, List<String> objects) {
            super(context, android.R.layout.simple_list_item_1, objects);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
}