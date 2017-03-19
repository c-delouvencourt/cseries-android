package fr.cseries.cseries.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Clément on 12/07/2016.
 */
public class PictureDownloader extends AsyncTask<String, Void, Bitmap> {

	Context context;
	ImageView bmImage;
	ImageView blur;

	public PictureDownloader(Context context, ImageView bmImage, ImageView blur) {
		this.context = context;
		this.bmImage = bmImage;
		this.blur = blur;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Erreur", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
		blur.setImageBitmap(BlurPicture.blur(this.context, result));
		this.cancel(true);
	}
}
