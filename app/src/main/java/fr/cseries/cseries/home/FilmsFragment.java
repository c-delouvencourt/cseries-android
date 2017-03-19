package fr.cseries.cseries.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.List;

import fr.cseries.cseries.Authentification;
import fr.cseries.cseries.CSeriesAPI;
import fr.cseries.cseries.R;
import fr.cseries.cseries.home.films.Film;
import fr.cseries.cseries.home.films.FilmsAdapter;

public class FilmsFragment extends Fragment {

	private static Authentification INSTANCE;

	private RequestQueue mRequestQueue;

	private List<Film> filmList = new ArrayList<>();

	public FilmsFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.activity_home_films, container, false);

		String tag_string_req = "req_films";
		StringRequest strReq = new StringRequest(Request.Method.GET, CSeriesAPI.API_URL_FILMS, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jObj = new JSONObject(response);

					JSONArray series = jObj.getJSONArray("films");
					for (int a = 0; a < series.length(); a++) {
						JSONObject film = series.getJSONObject(a);

						Film s = new Film(film.getString("ID"));
						s.setTitle(film.getString("title"));
						s.setCover(film.getString("cover"));

						filmList.add(s);
					}

					RecyclerView recyclerView = (RecyclerView) getActivity().findViewById(R.id.films_list);
					recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

					FilmsAdapter mAdapter = new FilmsAdapter(getContext(), filmList);
					recyclerView.setAdapter(mAdapter);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});

		this.addToRequestQueue(strReq, tag_string_req);

		return v;
	}

	// Request

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null)
			mRequestQueue = Volley.newRequestQueue(getActivity());
		return mRequestQueue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(tag);
		getRequestQueue().add(req);
	}
}