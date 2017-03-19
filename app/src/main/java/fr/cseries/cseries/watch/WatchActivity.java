package fr.cseries.cseries.watch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import fr.cseries.cseries.CSeriesAPI;
import fr.cseries.cseries.R;
import fr.cseries.cseries.auth.SQLiteHandler;

public class WatchActivity extends AppCompatActivity {

	private static String TAG = WatchActivity.class.getSimpleName();
	private RequestQueue mRequestQueue;

	JCVideoPlayerStandard player;

	String  serie;
	String  saison;
	String  episode;
	long    time;
	Boolean reprise;

	String serieText;
	String saisonText;
	String episodeText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_watch_episode);

		Intent mIntent = getIntent();
		final String serie = mIntent.getStringExtra("serie");
		final String saison = mIntent.getStringExtra("saison");
		String episode = mIntent.getStringExtra("episode");
		Integer time = mIntent.getIntExtra("time", 0);
		Boolean reprise = mIntent.getBooleanExtra("reprise", false);

		this.serie = serie;
		this.saison = saison;
		this.episode = episode;
		this.time = time;
		this.reprise = reprise;

		final Toolbar bar = (Toolbar) findViewById(R.id.watch_action_bar);
		bar.setTitle("");
		setSupportActionBar(bar);

		checkVideoLink(serie, saison, episode);
	}

	@Override
	protected void onPause() {
		super.onPause();

		this.player.getMediaManager().mediaPlayer.pause();
	}

	@Override
	protected void onResume() {
		SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(this).edit();
		e.putString("last_activity", getClass().getSimpleName());
		e.commit();

		super.onResume();
	}


	// REQUEST

	private void checkVideoLink(final String serie, final String saison, final String episode) {
		String tag_string_req = "req_watch";

		StringRequest strReq = new StringRequest(Request.Method.GET, CSeriesAPI.API_URL_WATCH + "?serie=" + serie + "&saison=" + saison + "&episode=" + episode + "&user=" + new SQLiteHandler(getApplicationContext()).getUserDetails().get("username"), new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jo = new JSONObject(response);

					serieText = jo.getString("serie");
					saisonText = jo.getString("saison");
					episodeText = jo.getString("episode");
					time = jo.getInt("time");

					TextView t = (TextView) findViewById(R.id.watch_serie_name);
					t.setText(serieText);
					TextView t2 = (TextView) findViewById(R.id.watch_saison_name);
					t2.setText(saisonText);

					JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.custom_videoplayer_standard);
					player = jcVideoPlayerStandard;
					jcVideoPlayerStandard.setUp(jo.getString("url_video"), serieText + " | " + saisonText + " | " + episodeText);

					if (reprise) {
						player.getMediaManager().mediaPlayer.start();
					}
				} catch (JSONException e) {
					Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "Erreur: " + error.getMessage());
				Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<>();
				params.put("serie", serie);
				params.put("saison", saison);
				params.put("episode", episode);

				return params;
			}
		};

		this.addToRequestQueue(strReq, tag_string_req);
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

}
