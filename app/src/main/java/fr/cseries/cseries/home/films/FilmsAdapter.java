package fr.cseries.cseries.home.films;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.cseries.cseries.R;
import fr.cseries.cseries.watch.WatchFilmActivity;

public class FilmsAdapter extends RecyclerView.Adapter<FilmsAdapter.Holder> {

	private Context    mContext;
	private List<Film> filmList;

	public class Holder extends RecyclerView.ViewHolder {
		ImageView cover;

		public Holder(View view) {
			super(view);
			cover = (ImageView) view.findViewById(R.id.serie_cover_image);
		}
	}

	public FilmsAdapter(Context mContext, List<Film> albumList) {
		this.mContext = mContext;
		this.filmList = albumList;
	}

	@Override
	public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_home_serie_card, parent, false);
		return new Holder(itemView);
	}

	@Override
	public void onBindViewHolder(Holder holder, int position) {
		final Film series = filmList.get(position);
		Glide.with(mContext).load(Uri.parse(series.getCover())).into(holder.cover);

		holder.cover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent sw = new Intent(mContext, WatchFilmActivity.class);
				sw.putExtra("film", series.getId());
				sw.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(sw);
			}
		});
	}

	@Override
	public int getItemCount() {
		return filmList.size();
	}
}
