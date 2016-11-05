package kode.kinopoisk.vedmedenko.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kode.kinopoisk.vedmedenko.R;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailHolder> {

    private ArrayList<String> mDetails;

    @Inject
    public DetailAdapter() {
        mDetails = new ArrayList<>();
    }

    @Override
    public DetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(
                        parent.getContext()).inflate(R.layout.detail_item, parent, false);
        return new DetailHolder(view);
    }

    @Override
    public void onBindViewHolder(final DetailHolder holder, final int position) {
        holder.item.setText(mDetails.get(position).replace("null", "")
                                                    .replace("false", "нет")
                                                    .replace("true", "да"));
    }

    @Override
    public int getItemCount() {
        return mDetails.size();
    }

    public void setDetails(ArrayList<String> details) {
        mDetails = details;
        notifyDataSetChanged();
    }

    class DetailHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item)
        TextView item;

        public DetailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
