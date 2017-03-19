package fr.cseries.cseries.saisons;

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
import fr.cseries.cseries.episodes.EpisodeActivity;

public class SaisonArrayAdapter extends ArrayAdapter<Saisons> {

	private Activity a;
	private static final String TAG = "SaisonArrayAdapter";

	private List<Saisons> seriesList = new ArrayList<>();

	static class SeriesViewHolder {
		TextView name;
		Button   button;
	}

	public SaisonArrayAdapter(Activity a, Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.a = a;
	}

	@Override
	public void add(Saisons series) {
		seriesList.add(series);
		super.add(series);
	}

	@Override
	public int getCount() {
		return this.seriesList.size();
	}

	@Override
	public Saisons getItem(int index) {
		return this.seriesList.get(index);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SeriesViewHolder viewHolder;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.activity_saisons_card, parent, false);
			viewHolder = new SeriesViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.saisons_name);
			viewHolder.button = (Button) convertView.findViewById(R.id.saisons_card_see);
			convertView.setTag(viewHolder);
		} else viewHolder = (SeriesViewHolder) convertView.getTag();

		final Saisons saison = getItem(position);
		viewHolder.name.setText(saison.getName().toUpperCase());

		viewHolder.button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent sw = new Intent(v.getContext(), EpisodeActivity.class);
				sw.putExtra("serie", saison.getSerie());
				sw.putExtra("saison", saison.getName().replace("Saison ", ""));
				sw.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				v.getContext().startActivity(sw);
			}
		});

		return convertView;
	}
}
