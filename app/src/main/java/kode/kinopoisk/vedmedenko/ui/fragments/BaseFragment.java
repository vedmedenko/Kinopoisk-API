package kode.kinopoisk.vedmedenko.ui.fragments;

import android.support.v4.app.Fragment;

import kode.kinopoisk.vedmedenko.injection.components.DaggerFragmentComponent;
import kode.kinopoisk.vedmedenko.injection.components.FragmentComponent;
import kode.kinopoisk.vedmedenko.injection.modules.FragmentModule;
import kode.kinopoisk.vedmedenko.ui.activities.base.BaseActivity;

public class BaseFragment extends Fragment {

    private FragmentComponent mFragmentComponent;

    protected FragmentComponent fragmentComponent() {
        if (mFragmentComponent == null) {
            mFragmentComponent = DaggerFragmentComponent.builder()
                    .fragmentModule(new FragmentModule(this))
                    .activityComponent(((BaseActivity) getActivity()).activityComponent())
                    .build();
        }
        return mFragmentComponent;
    }

}
