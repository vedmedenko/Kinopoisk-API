package kode.kinopoisk.vedmedenko.injection.components;

import dagger.Component;
import kode.kinopoisk.vedmedenko.injection.PerFragment;
import kode.kinopoisk.vedmedenko.injection.modules.FragmentModule;
import kode.kinopoisk.vedmedenko.ui.fragments.DetailFragment;

@PerFragment
@Component(dependencies = ActivityComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(DetailFragment detailFragment);
}
