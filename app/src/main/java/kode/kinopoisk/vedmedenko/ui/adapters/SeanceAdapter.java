package kode.kinopoisk.vedmedenko.ui.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kode.kinopoisk.vedmedenko.R;
import kode.kinopoisk.vedmedenko.core.rest.models.getSeance.SeanceItem;
import kode.kinopoisk.vedmedenko.injection.ActivityContext;
import kode.kinopoisk.vedmedenko.ui.activities.MapsActivity;
import kode.kinopoisk.vedmedenko.ui.activities.base.BaseActivity;

public class SeanceAdapter extends RecyclerView.Adapter<SeanceAdapter.SeanceHolder> {

    private ArrayList<SeanceItem> seanceItems;
    private final Context context;

    @Inject
    public SeanceAdapter(@NonNull @ActivityContext Context context) {
        seanceItems = new ArrayList<>();
        this.context = context;
    }

    @Override
    public SeanceAdapter.SeanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.seance_card, parent, false);
        return new SeanceAdapter.SeanceHolder(view);
    }

    @Override
    public void onBindViewHolder(final SeanceAdapter.SeanceHolder holder, final int position) {
        holder.cinemaNameText.setText(seanceItems.get(position).cinemaName);
        holder.cinemaAddressText.setText(seanceItems.get(position).address);

        ArrayList<String> seances = seanceItems.get(position).seances;
        String seancesString = "";

        for (String s : seances) {
            if (seances.indexOf(s) != seances.size() - 1)
                seancesString += s + ", ";
            else
                seancesString += s;
        }

        holder.seancesText.setText(seancesString);

        holder.seanceContainer.setOnClickListener((v -> {
            launchMapsActivity(position);
        }));
    }

    private void launchMapsActivity(int position) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        Dialog dialog = googleApiAvailability.getErrorDialog((BaseActivity) context,
                googleApiAvailability.isGooglePlayServicesAvailable(context), 1);

        if (dialog != null) {
            dialog.show();
        } else {
            context.startActivity(MapsActivity.getStartIntent(context, seanceItems.get(position).cinemaName,
                    Double.valueOf(seanceItems.get(position).lat),
                    Double.valueOf(seanceItems.get(position).lon)));
        }
    }

    @Override
    public int getItemCount() {
        return seanceItems.size();
    }

    public void setSeanceItems(@NonNull ArrayList<SeanceItem> seanceItems) {
        this.seanceItems = seanceItems;
        notifyDataSetChanged();
    }

    class SeanceHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.seance_container)
        View seanceContainer;

        @BindView(R.id.text_cinema_name)
        TextView cinemaNameText;

        @BindView(R.id.text_cinema_address)
        TextView cinemaAddressText;

        @BindView(R.id.text_seances)
        TextView seancesText;

        public SeanceHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

