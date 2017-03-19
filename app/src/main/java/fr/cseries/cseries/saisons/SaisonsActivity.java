package fr.cseries.cseries.saisons;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.cseries.cseries.Authentification;
import fr.cseries.cseries.CSeriesAPI;
import fr.cseries.cseries.R;
import fr.cseries.cseries.auth.SQLiteHandler;
import fr.cseries.cseries.auth.SessionManager;

public class SaisonsActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener{

	// CLASS
	private static SaisonsActivity INSTANCE;
	private static final String TAG = "SaisonsActivity";


	private SaisonArrayAdapter saisonArrayAdapter;
	private ListView           listView;
	private RequestQueue       mRequestQueue;

	private List<Saisons> saisonsList = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_saisons_list);

		Intent mIntent = getIntent();
		String serie = mIntent.getStringExtra("serie");

		checkSaisons(serie);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		int id = item.getItemId();

		if (id == R.id.nav_logout) {
			new SQLiteHandler(getApplicationContext()).deleteUsers();
			new SessionManager(getApplicationContext()).setLogin(false);
			finish();
			startActivity(new Intent(this, Authentification.class));
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	private void checkSaisons(final String serie) {
		String tag_string_req = "req_saisons";
		StringRequest strReq = new StringRequest(Request.Method.GET, CSeriesAPI.API_URL_SAISONS + "?serie=" + serie, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jObj = new JSONObject(response);

					JSONArray series = jObj.getJSONArray("saisons");

					for (int a = 0; a < series.length(); a++) {
						String saison = series.getString(a);

						Saisons s = new Saisons(saison, serie);

						saisonsList.add(s);

						listView = (ListView) findViewById(R.id.saisons_listView);

						listView.addHeaderView(new View(SaisonsActivity.this));
						listView.addFooterView(new View(SaisonsActivity.this));

						saisonArrayAdapter = new SaisonArrayAdapter(INSTANCE, getApplicationContext(), R.layout.activity_saisons_card);

						for (Saisons sa : saisonsList) {
							saisonArrayAdapter.add(sa);
						}
						listView.setAdapter(saisonArrayAdapter);
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "Saisons Error: " + error.getMessage());
				Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
			}
		}){
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<>();
				params.put("serie", serie);
				return params;
			}
		};
		this.addToRequestQueue(strReq, tag_string_req);
	}

	// Système de requête

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}
}