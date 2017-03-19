package fr.cseries.cseries.home.series;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.cseries.cseries.R;
import fr.cseries.cseries.saisons.SaisonsActivity;
import fr.cseries.cseries.utils.BlurPicture;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.Holder> {

	private Context      mContext;
	private List<Series> seriesList;

	public class Holder extends RecyclerView.ViewHolder {
		ImageView cover;

		public Holder(View view) {
			super(view);
			cover = (ImageView) view.findViewById(R.id.serie_cover_image);
		}
	}

	public SeriesAdapter(Context mContext, List<Series> albumList) {
		this.mContext = mContext;
		this.seriesList = albumList;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_serie_card, parent, false);
		return new Holder(itemView);
	}

	@Override
	public void onBindViewHolder(Holder holder, int position) {
		final Series series = seriesList.get(position);
		Glide.with(mContext).load(Uri.parse(series.getImage())).into(holder.cover);

		holder.cover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent sw = new Intent(mContext, SaisonsActivity.class);
				sw.putExtra("serie", series.getId());
				sw.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(sw);
			}
		});
	}

	@Override
	public int getItemCount() {
		return seriesList.size();
	}
}
