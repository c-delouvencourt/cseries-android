package fr.cseries.cseries;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splashscreen extends Activity {

	private static int SPLASH_TIME_OUT = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash_screen);


		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent i = new Intent(Splashscreen.this, Authentification.class);
				overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
				startActivity(i);

				finish();
			}
		}, SPLASH_TIME_OUT);
	}
}