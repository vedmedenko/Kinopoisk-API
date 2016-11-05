package kode.kinopoisk.vedmedenko.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;

import butterknife.ButterKnife;
import kode.kinopoisk.vedmedenko.R;
import kode.kinopoisk.vedmedenko.ui.adapters.DetailAdapter;
import kode.kinopoisk.vedmedenko.utils.ConstantsManager;

public class DetailFragment extends BaseFragment {

    @BindView(R.id.recycler_detail)
    RecyclerView mDetailRecycler;

    @Inject
    DetailAdapter mDetailAdapter;

    private ArrayList<String> mItems;

    public static DetailFragment newInstance(@NonNull ArrayList<String> items) {
        DetailFragment detailFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ConstantsManager.DF_ARG_MAP, items);
        detailFragment.setArguments(args);
        return detailFragment;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
        mItems = (ArrayList<String>) getArguments().getSerializable(ConstantsManager.DF_ARG_MAP);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        mDetailRecycler.setAdapter(mDetailAdapter);
        return fragmentView;
    }

    private void setupRecyclerView() {
        mDetailRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDetailAdapter.setDetails(mItems);
        mDetailRecycler.setVisibility(View.VISIBLE);
    }

}
