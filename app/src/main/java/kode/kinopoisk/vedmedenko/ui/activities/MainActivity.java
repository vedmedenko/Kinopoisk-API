package kode.kinopoisk.vedmedenko.ui.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kode.kinopoisk.vedmedenko.R;
import kode.kinopoisk.vedmedenko.core.rest.models.getSoonFilms.SoonFilm;
import kode.kinopoisk.vedmedenko.ui.activities.base.BaseActivity;
import kode.kinopoisk.vedmedenko.ui.activities.presenters.MainPresenter;
import kode.kinopoisk.vedmedenko.ui.activities.views.MainMvpView;
import kode.kinopoisk.vedmedenko.ui.adapters.SoonFilmAdapter;
import kode.kinopoisk.vedmedenko.utils.NetworkUtil;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView {

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recyclerMovies)
    RecyclerView recyclerMovies;

    @BindView(R.id.erlSearchSettings)
    ExpandableRelativeLayout erlSearchSettings;

    @BindView(R.id.spinnerMonth)
    Spinner spinnerMonth;

    @BindView(R.id.spinnerYear)
    Spinner spinnerYear;

    @BindView(R.id.spinnerCountry)
    Spinner spinnerCountry;

    @BindView(R.id.spinnerCity)
    Spinner spinnerCity;

    @BindView(R.id.spinnerGenre)
    Spinner spinnerGenre;

    @BindView(R.id.checkBoxGenreSort)
    CheckBox checkBoxGenreSort;

    @BindView(R.id.checkBoxRatingSort)
    CheckBox checkBoxRatingSort;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    @BindView(R.id.text_view_hint)
    TextView hint;

    @BindView(R.id.buttonReload)
    TextView buttonReload;

    @Inject
    public MainPresenter mainPresenter;

    @Inject
    public SoonFilmAdapter soonFilmAdapter;

    private ArrayAdapter<String> monthAdapter, yearAdapter, countryAdapter, cityAdapter, genreAdapter;
    private boolean layoutOnScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent().inject(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (NetworkUtil.isNetworkConnected(view.getContext())) {
                    mainPresenter.loadCities(((TextView) view).getText().toString());
                    showProgress(true);
                }
                else {
                    showHint(R.string.stringErrorNoNetwork, true);
                    showProgress(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mainPresenter.attach(this);
        showProgress(true);

        layoutOnScreen = false;
        setupRecyclerView();

        monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerMonth.setAdapter(monthAdapter);

        yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerYear.setAdapter(yearAdapter);

        countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerCountry.setAdapter(countryAdapter);

        cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerCity.setAdapter(cityAdapter);

        genreAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        spinnerGenre.setAdapter(genreAdapter);
        spinnerGenre.setEnabled(false);

        checkBoxGenreSort.setOnClickListener(v -> spinnerGenre.setEnabled(((CheckBox) v).isChecked()));

        buttonReload.setOnClickListener(v -> {
            if (NetworkUtil.isNetworkConnected(v.getContext())) {
                mainPresenter.loadData();
            } else {
                showHint(R.string.stringErrorNoNetwork, true);
                showProgress(false);
            }
        });

        erlSearchSettings.setOnClickListener(null);
    }

    private void setupRecyclerView() {
        recyclerMovies.setLayoutManager(new LinearLayoutManager(this));
        recyclerMovies.setAdapter(soonFilmAdapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
                if (!layoutOnScreen) {
                    loadSoonFilms();
                }
                swipeRefreshLayout.setRefreshing(false);
            });
    }

    private void loadSoonFilms() {
        showProgress(true);

        String month = spinnerMonth.getSelectedItem().toString();

        if (Integer.valueOf(month) < 10)
            month = "0" + month;

        try {
            if (NetworkUtil.isNetworkConnected(getBaseContext())) {
                mainPresenter.loadSoonFilms(month + "." + spinnerYear.getSelectedItem().toString(),
                        spinnerCity.getSelectedItem().toString());
            } else {
                showHint(R.string.stringErrorNoNetwork, true);
                showProgress(false);
            }
        } catch (NullPointerException e) {
            showHint(R.string.stringErrorNoNetwork, true);
            showProgress(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_searchSettings:
                erlSearchSettings.toggle();
                layoutOnScreen = !layoutOnScreen;
                if (!layoutOnScreen) {
                    loadSoonFilms();
                } else {

                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showProgress(boolean flag) {
        if (flag) {
            swipeRefreshLayout.setVisibility(View.GONE);
            hint.setVisibility(View.GONE);
            buttonReload.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            swipeRefreshLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showHint(@StringRes int resid, boolean btnReloadVisibility) {
        hint.setText(getResources().getString(resid));
        hint.setVisibility(View.VISIBLE);

        swipeRefreshLayout.setVisibility(View.GONE);

        if (btnReloadVisibility) {
            buttonReload.setVisibility(View.VISIBLE);
        }
        else {
            buttonReload.setVisibility(View.GONE);
        }
    }

    @Override
    public void setSoonFilms(@NonNull List<SoonFilm> movies) {
        soonFilmAdapter.setSoonFilms(movies);
    }

    @Override
    public void addSoonFilms(@NonNull List<SoonFilm> movies) {
        soonFilmAdapter.addAll(movies);
    }

    @Override
    public void setAdapters(@NonNull List<String> months,
                            @NonNull List<String> years,
                            @NonNull List<String> countries,
                            @NonNull List<String> genres) {

        monthAdapter.clear();
        monthAdapter.addAll(months);
        monthAdapter.notifyDataSetChanged();

        yearAdapter.clear();
        yearAdapter.addAll(years);
        yearAdapter.notifyDataSetChanged();

        countryAdapter.clear();
        countryAdapter.addAll(countries);
        countryAdapter.notifyDataSetChanged();

        genreAdapter.clear();
        genreAdapter.addAll(genres);
        genreAdapter.notifyDataSetChanged();

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        spinnerYear.setSelection(years.indexOf(year + ""));
        spinnerMonth.setSelection(months.indexOf((month + 1) + ""));

        erlSearchSettings.expand();
        layoutOnScreen = true;
        swipeRefreshLayout.setVisibility(View.GONE);
    }

    @Override
    public void setCityAdapter(@NonNull List<String> cities) {
        cityAdapter.clear();
        cityAdapter.addAll(cities);
        cityAdapter.notifyDataSetChanged();
    }

    @Override
    public String getGenreSort() {
        if (checkBoxGenreSort.isChecked()) {
            return spinnerGenre.getSelectedItem().toString();
        } else {
            return "";
        }
    }

    @Override
    public boolean getRatingSort() {
        return checkBoxRatingSort.isChecked();
    }

    public String getSelectedCity() {
        return spinnerCity.getSelectedItem().toString();
    }
}
