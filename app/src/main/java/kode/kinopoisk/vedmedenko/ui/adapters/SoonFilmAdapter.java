package kode.kinopoisk.vedmedenko.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kode.kinopoisk.vedmedenko.R;
import kode.kinopoisk.vedmedenko.core.rest.models.getSoonFilms.SoonFilm;
import kode.kinopoisk.vedmedenko.injection.ActivityContext;
import kode.kinopoisk.vedmedenko.ui.activities.DetailActivity;
import kode.kinopoisk.vedmedenko.ui.activities.MainActivity;
import kode.kinopoisk.vedmedenko.ui.activities.SeancesActivity;
import kode.kinopoisk.vedmedenko.ui.activities.views.MainMvpView;
import timber.log.Timber;

public class SoonFilmAdapter extends RecyclerView.Adapter<SoonFilmAdapter.MovieHolder> {

    private List<SoonFilm> mMovies;
    private Context context;

    @Inject
    public SoonFilmAdapter(@NonNull @ActivityContext Context context) {
        mMovies = new ArrayList<>();
        this.context = context;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.movie_card, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieHolder holder, final int position) {
        final SoonFilm movie = mMovies.get(position);

        holder.titleText.setText(movie.nameRU);
        holder.genreText.setText(movie.genre);
        holder.yearText.setText(movie.premiereRU);
        holder.ratingText.setText(movie.rating);

        Glide.with(context)
                .load(context.getString(R.string.posterURL, movie.id))
                .into(holder.movieImage);

        holder.movieContainer.setOnClickListener(view -> {
            context.startActivity(DetailActivity.getStartIntent(context, mMovies.get(position).id));
        });

        holder.buttonSeances.setOnClickListener(view -> {
            context.startActivity(SeancesActivity.getStartIntent(context, mMovies.get(position).id,
                    ((MainActivity) context).mainPresenter.getCityID(((MainActivity) context).getSelectedCity())));
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setSoonFilms(List<SoonFilm> movies) {

        mMovies.clear();

        if (!((MainMvpView) context).getGenreSort().equals("")) {
            try {
                for (SoonFilm movie : movies) {
                    if (movie.genre.contains(((MainMvpView) context).getGenreSort())) {
                        mMovies.add(movie);
                    }
                }
            } catch (NullPointerException e) {
                Timber.d("Oops, empty genre.");
            }
        } else {
            mMovies = movies;
        }

        if (((MainMvpView) context).getRatingSort()) {
            Collections.sort(mMovies, (o1, o2) -> {
                try {
                    return Double.valueOf(o2.rating.split(" ")[0].replace("%", "")).
                            compareTo(Double.valueOf(o1.rating.split(" ")[0].replace("%", "")));
                } catch (NullPointerException e) {
                    if (o1.rating == null) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
        }

        notifyDataSetChanged();
    }

    public void addAll(List<SoonFilm> movies) {

        if (!((MainMvpView) context).getGenreSort().equals("")) {
            for (SoonFilm movie : movies) {
                if (movie.genre.contains(((MainMvpView) context).getGenreSort())) {
                    mMovies.add(movie);
                }
            }
        } else {
            mMovies.addAll(movies);
        }

        if (((MainMvpView) context).getRatingSort()) {
            Collections.sort(mMovies, (o1, o2) -> {
                try {
                    return Double.valueOf(o2.rating.split(" ")[0].replace("%", "")).
                            compareTo(Double.valueOf(o1.rating.split(" ")[0].replace("%", "")));
                } catch (NullPointerException e) {
                    if (o1.rating == null) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            });
        }

        notifyDataSetChanged();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movie_container)
        View movieContainer;

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

        @BindView(R.id.buttonSeances)
        TextView buttonSeances;

        public MovieHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
