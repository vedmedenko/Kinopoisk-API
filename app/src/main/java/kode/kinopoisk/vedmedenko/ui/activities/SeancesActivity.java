package kode.kinopoisk.vedmedenko.ui.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kode.kinopoisk.vedmedenko.R;
import kode.kinopoisk.vedmedenko.core.rest.models.getSeance.Seance;
import kode.kinopoisk.vedmedenko.ui.activities.base.BaseActivity;
import kode.kinopoisk.vedmedenko.ui.activities.presenters.SeancePresenter;
import kode.kinopoisk.vedmedenko.ui.activities.views.SeanceMvpView;
import kode.kinopoisk.vedmedenko.ui.adapters.SeanceAdapter;
import kode.kinopoisk.vedmedenko.utils.ConstantsManager;
import kode.kinopoisk.vedmedenko.utils.NetworkUtil;
import timber.log.Timber;

public class SeancesActivity extends BaseActivity implements SeanceMvpView {

    @BindView(R.id.card_view)
    View cardView;

    @BindView(R.id.text_title)
    TextView titleText;

    @BindView(R.id.text_genre)
    TextView genreText;

    @BindView(R.id.text_premier)
    TextView yearText;

    @BindView(R.id.text_rating)
    TextView ratingText;

    @BindView(R.id.image_movie)
    ImageView movieImage;

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerSeances)
    RecyclerView recyclerSeances;

    @BindView(R.id.label_seances)
    TextView seancesText;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.text_view_hint)
    TextView hint;

    @Inject
    SeanceAdapter mSeanceAdapter;

    @Inject
    SeancePresenter mSeancePresenter;

    private String date, filmId;
    private int cityID;

    public static Intent getStartIntent(@NonNull Context context, @NonNull String filmID, int cityID) {
        Intent intent = new Intent(context, SeancesActivity.class);
        intent.putExtra(ConstantsManager.FILM_ID, filmID);
        intent.putExtra(ConstantsManager.CITY_ID, cityID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent().inject(this);

        setContentView(R.layout.activity_seances);
        ButterKnife.bind(this);

        mSeancePresenter.attach(this);

        setupToolbar();
        setupRecyclerView();

        filmId = getIntent().getStringExtra(ConstantsManager.FILM_ID);
        cityID = getIntent().getIntExtra(ConstantsManager.CITY_ID, -1);

        if (NetworkUtil.isNetworkConnected(this)) {
            mSeancePresenter.loadSeances(filmId, cityID, date);
            showProgress(true);
        } else {
            showHint(R.string.text_no_data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSeancePresenter.detach();
    }

    private void setupToolbar() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH) + 1;

        date = ((day < 10) ? "0" + day : day) + "." + ((month < 10) ? "0" + month : month) + "." + year;

        getSupportActionBar().setTitle(getString(R.string.label_seances, date));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_seances, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_datePicker:
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH) + 1;

                new DatePickerDialog(this, (view, y, m, d) -> {
                    date = ((d < 10) ? "0" + d : d) + "." + ((m < 10) ? "0" + m : m) + "." + y;
                    getSupportActionBar().setTitle(getString(R.string.label_seances, date));
                    if (NetworkUtil.isNetworkConnected(this)) {
                        mSeancePresenter.loadSeances(filmId, cityID, date);
                        showProgress(true);
                    } else {
                        showHint(R.string.text_no_data);
                    }
                }, year, month, day).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRecyclerView() {
        recyclerSeances.setLayoutManager(new LinearLayoutManager(this));
        recyclerSeances.setAdapter(mSeanceAdapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            if (NetworkUtil.isNetworkConnected(this)) {
                mSeancePresenter.loadSeances(filmId, cityID, date);
                showProgress(true);
            } else {
                showHint(R.string.text_no_data);
            }
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    @Override
    public void setupContent(@NonNull Seance seance) {
        titleText.setText(seance.nameRU);
        genreText.setText(seance.genre);
        yearText.setText(seance.year);
        ratingText.setText(seance.rating);

        Glide.with(this)
                .load(getString(R.string.posterURL, seance.filmID))
                .into(movieImage);

        cardView.setOnClickListener(view -> startActivity(DetailActivity.getStartIntent(this, seance.filmID)));

        if (seance.items.size() == 0) {
            seancesText.setText(getResources().getString(R.string.seances_empty_items));
        } else {
            seancesText.setText(getResources().getString(R.string.text_view_label_seances));
            mSeanceAdapter.setSeanceItems(seance.items);
        }
    }

    @Override
    public void showProgress(boolean flag) {
        if (flag) {
            swipeRefreshLayout.setVisibility(View.GONE);
            cardView.setVisibility(View.GONE);
            hint.setVisibility(View.GONE);
            seancesText.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            cardView.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            seancesText.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showHint(@StringRes int resid) {
        hint.setText(getResources().getString(resid));
        hint.setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);
        cardView.setVisibility(View.GONE);
        swipeRefreshLayout.setVisibility(View.GONE);
        seancesText.setVisibility(View.GONE);
    }
}
