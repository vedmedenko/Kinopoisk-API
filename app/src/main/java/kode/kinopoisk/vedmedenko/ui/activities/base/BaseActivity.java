package kode.kinopoisk.vedmedenko.ui.activities.base;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.lang.ref.WeakReference;

import kode.kinopoisk.vedmedenko.KinopoiskApplication;
import kode.kinopoisk.vedmedenko.injection.components.ActivityComponent;
import kode.kinopoisk.vedmedenko.injection.components.DaggerActivityComponent;
import kode.kinopoisk.vedmedenko.injection.modules.ActivityModule;

public class BaseActivity extends AppCompatActivity {

    private ActivityComponent mActivityComponent;
    private WeakReference<Context> weakReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weakReference = new WeakReference<>(this);
    }

    public ActivityComponent activityComponent() {
        if (weakReference == null) {
            weakReference = new WeakReference<>(this);
        }

        if (mActivityComponent == null) {
            mActivityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(KinopoiskApplication.get(weakReference).getComponent())
                    .build();
        }
        return mActivityComponent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        weakReference.clear();
        super.onDestroy();
        watchForLeaks();
    }

    private void watchForLeaks() {
        KinopoiskApplication.getRefWatcher(this).watch(this);
    }
}
