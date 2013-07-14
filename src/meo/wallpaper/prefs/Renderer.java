package meo.wallpaper.prefs;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import rajawali.BaseObject3D;
import rajawali.lights.DirectionalLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.textures.ATexture.TextureException;
import rajawali.materials.textures.Texture;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class Renderer extends RajawaliRenderer {
	private static final String TAG_SETTINGS = "MEOTagSettings";

	private DirectionalLight mLight;
	private BaseObject3D mSphere;

	// Preferences
	private SharedPreferences mSharedPrefs;
	String meoprefLayout;

	public Renderer(Context context) {
		super(context);
		setFrameRate(60);

		mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		meoprefLayout = mSharedPrefs.getString(Settings.KEY_LAYOUT, "");

		Log.i(TAG_SETTINGS, "Renderer: " + meoprefLayout);
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(Settings.KEY_LAYOUT)) {
			meoprefLayout = mSharedPrefs.getString(Settings.KEY_LAYOUT, "");
		}
		Log.i(TAG_SETTINGS, "onSharedPrefChanged: " + meoprefLayout);
	}

	protected void initScene() {
		mLight = new DirectionalLight(1f, 0.2f, -1.0f); // set the direction
		mLight.setColor(1.0f, 1.0f, 1.0f);
		mLight.setPower(2);

		try {
			DiffuseMaterial material = new DiffuseMaterial();
			material.addTexture(new Texture(meo.wallpaper.xmaslights.R.drawable.halo_w128));
			mSphere = new Sphere(1, 24, 24);
			mSphere.setMaterial(material);
			mSphere.addLight(mLight);
			addChild(mSphere); // Queue an addition task for mSphere
		} catch (TextureException e) {
			e.printStackTrace();
		}

		getCurrentCamera().setZ(6);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mSphere.setRotY(mSphere.getRotY() + 1);
		
		mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
		meoprefLayout = mSharedPrefs.getString(Settings.KEY_LAYOUT, "");

		Log.i(TAG_SETTINGS, "onDrawFrame: " + meoprefLayout);
	}
}