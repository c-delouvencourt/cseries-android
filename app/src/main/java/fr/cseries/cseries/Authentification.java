package fr.cseries.cseries;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import fr.cseries.cseries.auth.SQLiteHandler;
import fr.cseries.cseries.auth.SessionManager;
import fr.cseries.cseries.home.HomeActivity;

public class Authentification extends AppCompatActivity {

	private static Authentification INSTANCE;
	private static String TAG = Authentification.class.getSimpleName();

	private TextView       emailField;
	private TextView       passwordField;
	private ProgressDialog pDialog;
	private Button         button;

	private SessionManager session;
	private SQLiteHandler  db;
	private RequestQueue   queue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authentification);

		this.emailField = (TextView) findViewById(R.id.email);
		this.passwordField = (TextView) findViewById(R.id.password);
		this.button = (Button) findViewById(R.id.loginButton);
		this.button.setOnClickListener(connect);
		this.pDialog = new ProgressDialog(this);
		this.pDialog.setCancelable(false);

		this.db = new SQLiteHandler(getApplicationContext());
		this.session = new SessionManager(getApplicationContext());
		this.queue = Volley.newRequestQueue(this);

		if (session.isLoggedIn()) {
			Intent intent = new Intent(Authentification.this, HomeActivity.class);
			overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
			startActivity(intent);
			finish();
		}

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	View.OnClickListener connect = new View.OnClickListener() {
		public void onClick(View v) {
			checkLogin(emailField.getText().toString(), passwordField.getText().toString());
		}
	};

	private void checkLogin(final String username, final String password) {
		String tag_string_req = "req_login";

		pDialog.setMessage("Connexion...");
		showDialog();

		StringRequest strReq = new StringRequest(Request.Method.POST, CSeriesAPI.API_URL_LOGIN, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				hideDialog();

				try {
					JSONObject jObj = new JSONObject(response);
					boolean error = jObj.getBoolean("error");

					if (!error) {
						session.setLogin(true);

						JSONObject user = jObj.getJSONObject("user");
						String username = user.getString("username");

						db.addUser(username);

						Intent intent = new Intent(Authentification.this, HomeActivity.class);
						overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
						startActivity(intent);
						finish();
					} else {
						String errorMsg = jObj.getString("error_msg");
						Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Erreur JSON: " + e.getMessage(), Toast.LENGTH_LONG).show();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "Erreur de connexion: " + error.getMessage());
				Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
				hideDialog();
			}
		}) {
			@Override
			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("username", username);
				params.put("password", password);

				return params;
			}

		};

		this.addToRequestQueue(strReq, tag_string_req);
	}

	public RequestQueue getRequestQueue() {
		if (queue == null) queue = Volley.newRequestQueue(getApplicationContext());
		return queue;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (queue != null) queue.cancelAll(tag);
	}

	// DIALOG

	private void showDialog() {
		if (!pDialog.isShowing()) pDialog.show();
	}

	private void hideDialog() {
		if (pDialog.isShowing()) pDialog.dismiss();
	}

}

