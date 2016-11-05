package kode.kinopoisk.vedmedenko;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.WeakReference;

import kode.kinopoisk.vedmedenko.injection.components.ApplicationComponent;
import kode.kinopoisk.vedmedenko.injection.components.DaggerApplicationComponent;
import kode.kinopoisk.vedmedenko.injection.modules.ApplicationModule;
import timber.log.Timber;

public class KinopoiskApplication extends Application {

    private ApplicationComponent applicationComponent;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

        initTimber();
        initLeakCanary();
        initStetho();
    }

    public ApplicationComponent getComponent() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }

        return applicationComponent;
    }

    public static RefWatcher getRefWatcher(Context context) {
        return ((KinopoiskApplication) context.getApplicationContext()).refWatcher;
    }

    public static KinopoiskApplication get(WeakReference<Context> contextWeakReference) {
        return (KinopoiskApplication) contextWeakReference.get().getApplicationContext();
    }

    private void initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void initLeakCanary() {
        refWatcher = LeakCanary.install(this);
    }

    private void initStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
