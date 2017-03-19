package fr.cseries.cseries.episodes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.cseries.cseries.R;
import fr.cseries.cseries.watch.WatchActivity;

public class EpisodeArrayAdapter extends ArrayAdapter<Episode> {

	private Activity a;
	private static final String TAG = "EpisodeArrayAdapter";

	private List<Episode> seriesList = new ArrayList<>();

	static class SeriesViewHolder {
		TextView name;
		Button   button;
	}

	public EpisodeArrayAdapter(Activity a, Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.a = a;
	}

	@Override
	public void add(Episode episode) {
		seriesList.add(episode);
		super.add(episode);
	}

	@Override
	public int getCount() {
		return this.seriesList.size();
	}

	@Override
	public Episode getItem(int index) {
		return this.seriesList.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SeriesViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_episodes_card, parent, false);
			viewHolder = new SeriesViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.episode_name);
			viewHolder.button = (Button) convertView.findViewById(R.id.episode_see);
			convertView.setTag(viewHolder);
		} else viewHolder = (SeriesViewHolder) convertView.getTag();

		final Episode episode = getItem(position);
		viewHolder.name.setText(episode.getName().toUpperCase());

		viewHolder.button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sw = new Intent(v.getContext(), WatchActivity.class);
				sw.putExtra("serie", episode.getSerie());
				sw.putExtra("saison", episode.getEpisode().replace("Saison ", ""));
				sw.putExtra("episode", episode.getName().replace("Episode ", ""));
				sw.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(sw);
			}
		});

		return convertView;
	}
}
