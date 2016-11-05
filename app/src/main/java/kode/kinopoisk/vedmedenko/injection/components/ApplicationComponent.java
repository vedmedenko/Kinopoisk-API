package kode.kinopoisk.vedmedenko.injection.components;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import kode.kinopoisk.vedmedenko.core.rest.KinopoiskApi;
import kode.kinopoisk.vedmedenko.core.rest.RestModule;
import kode.kinopoisk.vedmedenko.core.services.LoadCitiesService;
import kode.kinopoisk.vedmedenko.core.services.LoadDataService;
import kode.kinopoisk.vedmedenko.core.services.LoadFilmService;
import kode.kinopoisk.vedmedenko.core.services.LoadSeancesService;
import kode.kinopoisk.vedmedenko.core.services.LoadSoonFilmsService;
import kode.kinopoisk.vedmedenko.injection.ApplicationContext;
import kode.kinopoisk.vedmedenko.injection.modules.ApplicationModule;

@Singleton
@Component(modules = {ApplicationModule.class, RestModule.class})
public interface ApplicationComponent {

    void inject(LoadSoonFilmsService service);
    void inject(LoadDataService service);
    void inject(LoadCitiesService service);
    void inject(LoadFilmService service);
    void inject(LoadSeancesService service);

    @ApplicationContext
    Context context();

    Application application();

    KinopoiskApi KinopoiskApi();
}
