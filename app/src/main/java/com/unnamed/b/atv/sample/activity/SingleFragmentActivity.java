package com.unnamed.b.atv.sample.activity;

import android.app.Fragment;
import android.os.Bundle;

import com.unnamed.b.atv.sample.R;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Bogdan Melnychuk on 2/12/15.
 */
public class SingleFragmentActivity extends AppCompatActivity {
    public final static String FRAGMENT_PARAM = "fragment";

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_single_fragment);

        Bundle b = getIntent().getExtras();
        Class<?> fragmentClass = (Class<?>) b.get(FRAGMENT_PARAM);
        if (bundle == null) {
            Fragment f = Fragment.instantiate(this, fragmentClass.getName());
            f.setArguments(b);
            getFragmentManager().beginTransaction().replace(R.id.fragment, f, fragmentClass.getName()).commit();
        }
    }
}
