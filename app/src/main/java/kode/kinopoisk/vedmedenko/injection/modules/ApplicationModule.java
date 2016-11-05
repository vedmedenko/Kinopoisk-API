package kode.kinopoisk.vedmedenko.injection.modules;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;
import kode.kinopoisk.vedmedenko.injection.ApplicationContext;

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

}
