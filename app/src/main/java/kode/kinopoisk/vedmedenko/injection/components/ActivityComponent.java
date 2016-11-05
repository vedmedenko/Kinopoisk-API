package kode.kinopoisk.vedmedenko.injection.components;

import dagger.Component;
import kode.kinopoisk.vedmedenko.injection.PerActivity;
import kode.kinopoisk.vedmedenko.injection.modules.ActivityModule;
import kode.kinopoisk.vedmedenko.ui.activities.DetailActivity;
import kode.kinopoisk.vedmedenko.ui.activities.MainActivity;
import kode.kinopoisk.vedmedenko.ui.activities.SeancesActivity;
import kode.kinopoisk.vedmedenko.ui.fragments.DetailFragment;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(DetailActivity activity);
    void inject(SeancesActivity activity);
}
