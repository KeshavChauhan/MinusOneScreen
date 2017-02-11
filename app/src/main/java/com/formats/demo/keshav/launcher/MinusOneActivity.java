package com.formats.demo.keshav.launcher;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.InMobiNativeStrand;
import com.inmobi.sdk.InMobiSdk;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;

/**
 * Created by keshav.p on 1/15/17.
 */

public class MinusOneActivity extends AppCompatActivity implements
        InMobiNative.NativeAdListener,
        InMobiBanner.BannerAdListener,
        InMobiNativeStrand.NativeStrandAdListener,
        InMobiInterstitial.InterstitialAdListener2 {

    public static final String TAG = MinusOneActivity.class.getSimpleName();

    InMobiNative n;
    InMobiBanner b;
    InMobiNativeStrand v;
    InMobiInterstitial i;

    private View mAdView;
    private ViewGroup mContainer;
    Button intAd;

    ImageView imageView1, imageView2, imageView3, imageView4;
    TextView textView1, textView2, textView3, textView4;

    View.OnTouchListener gestureListener;
    Intent mainActivity;
    private GestureDetector gestureDetector;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        InMobiSdk.init(this, "1a19654b05694a2385448499f03f48df");

        setContentView(R.layout.activity_minusone);

        InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);

        Toast.makeText(this, "MinusOneActivity", Toast.LENGTH_SHORT).show();

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        textView1 = (TextView) findViewById(R.id.textView1);

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        textView2 = (TextView) findViewById(R.id.textView2);

        imageView3 = (ImageView) findViewById(R.id.imageView3);
        textView3 = (TextView) findViewById(R.id.textView3);

        imageView4 = (ImageView) findViewById(R.id.imageView4);
        textView4 = (TextView) findViewById(R.id.textView4);

        intAd = (Button) findViewById(R.id.buttonInterst);

        gestureDetector = new GestureDetector(new SwipeGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        };

        mContainer = (ViewGroup) findViewById(R.id.container);

        n = new InMobiNative(this, 1460958725647L, this);
        n.load();
        Log.d(TAG, "Native Load ad");

        b = (InMobiBanner) findViewById(R.id.banner);
        b = new InMobiBanner(this, 1473189489298L);
        RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.parent_banner);
        RelativeLayout.LayoutParams bannerLp = new RelativeLayout.LayoutParams(640, 100);
        bannerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bannerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        adContainer.addView(b, bannerLp);
        b.load();
        b.setRefreshInterval(60);
        Log.d(TAG, "Banner Load ad");

        v = new InMobiNativeStrand(this, 1478955038899L, this);
        v.load();
        Log.d(TAG, "Video Load ad");

        i = new InMobiInterstitial(this, 1475973082314L, this);
        i.load();
        Log.d(TAG, "Interstitial Load ad");

        intAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.show();
            }
        });

    }


    @Override
    public void onBackPressed() {
        this.startActivity(new Intent(MinusOneActivity.this, MainActivity.class));

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private void onLeftSwipe() {
        // Do something
        Toast.makeText(this, "left Swipe method", Toast.LENGTH_SHORT).show();
        mainActivity = new Intent(this, MainActivity.class);
        startActivity(mainActivity);
    }

    private void onRightSwipe() {
        // Do something
        Toast.makeText(this, "Swipe Left for Home screen", Toast.LENGTH_SHORT).show();
    }

    // Private class for gestures
    private class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        // Swipe properties, you can change it to make the swipe
        // longer or shorter and speed
        private static final int SWIPE_MIN_DISTANCE = 50;
        private static final int SWIPE_MAX_OFF_PATH = 190;
        private static final int SWIPE_THRESHOLD_VELOCITY = 190;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2,
                               float velocityX, float velocityY) {
            try {
                float diffAbs = Math.abs(e1.getY() - e2.getY());
                float diff = e1.getX() - e2.getX();

                if (diffAbs > SWIPE_MAX_OFF_PATH)
                    return false;

                // Left swipe
                if (diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    MinusOneActivity.this.onLeftSwipe();

                    // Right swipe
                } else if (-diff > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    MinusOneActivity.this.onRightSwipe();
                }
            } catch (Exception e) {
                Log.e(TAG, "Error on gestures");
            }
            return false;
        }
    }

    @Override
    public void onAdLoadSucceeded(InMobiNative inMobiNative) {
        Log.d(TAG, "Ad load succeed");
        try {
            JSONObject content = new JSONObject((String) inMobiNative.getAdContent());
            Log.d(TAG, "Content is" + content);

            String title = content.getString("title");
            Log.d(TAG, "Title is: " + title);
            String icon = content.getJSONObject("icon_xhdpi").getString("url");
            Log.d(TAG, "Icon Url is: " + icon);
            final String category = content.getString("category");
            Log.d(TAG, "Category is: " + category);
            final String click_url = content.getString("click_url");
            Log.d(TAG, "Click_url is: " + click_url);
            String cta = content.getString("cta_install");
            Log.d(TAG, "cta is: " + cta);


            textView1.setText("Google");
            Picasso.with(this).load(R.drawable.google).resize(190, 190).into(imageView1);
            InMobiNative.bind(imageView1, n);
            imageView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in/"));
                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MinusOneActivity.this.startActivity(browserIntent);

                    } catch (Exception e) {
                        Log.d(TAG, "Exception top is: " + e);
                        Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in"));
                        internetIntent.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
                        internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MinusOneActivity.this.startActivity(internetIntent);
                        n.reportAdClickAndOpenLandingPage(null);
                        Log.d(TAG, "Exception is: " + e);

                    }

                }
            });


            textView2.setText("Quora");
            Picasso.with(this).load(R.drawable.quora).resize(190, 190).into(imageView2);
            InMobiNative.bind(imageView2, n);
            imageView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.quora.com/"));
                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MinusOneActivity.this.startActivity(browserIntent);

                    } catch (Exception e) {
                        Log.d(TAG, "Exception top is: " + e);
                        Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.quora.com/"));
                        internetIntent.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
                        internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MinusOneActivity.this.startActivity(internetIntent);
                        n.reportAdClickAndOpenLandingPage(null);
                        Log.d(TAG, "Exception is: " + e);

                    }

                }
            });


            textView3.setText("Maps");
            Picasso.with(this).load(R.drawable.mapsicon).resize(190, 190).into(imageView3);
            InMobiNative.bind(imageView3, n);
            imageView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in/maps"));
                        browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MinusOneActivity.this.startActivity(browserIntent);

                    } catch (Exception e) {
                        Log.d(TAG, "Exception top is: " + e);
                        Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in/maps"));
                        internetIntent.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
                        internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MinusOneActivity.this.startActivity(internetIntent);
                        n.reportAdClickAndOpenLandingPage(null);
                        Log.d(TAG, "Exception is: " + e);

                    }

                }
            });


            textView4.setText(title);
            Picasso.with(this).load(icon).resize(190, 190).into(imageView4);
            InMobiNative.bind(imageView4, n);
//

            imageView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (click_url != null) {
                        try {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(click_url));
                            browserIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MinusOneActivity.this.startActivity(browserIntent);

                        } catch (Exception e) {
                            Log.d(TAG, "Exception top is: " + e);
                            Intent internetIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(click_url));
                            internetIntent.setComponent(new ComponentName("com.android.browser", "com.android.browser.BrowserActivity"));
                            internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MinusOneActivity.this.startActivity(internetIntent);
                            n.reportAdClickAndOpenLandingPage(null);
                            Log.d(TAG, "Exception is: " + e);

                        }

                        n.reportAdClickAndOpenLandingPage(null);

                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus inMobiAdRequestStatus) {
        Log.d(TAG, "Failed to load Native ad. " + inMobiAdRequestStatus.getMessage());
    }

    @Override
    public void onAdDismissed(InMobiNative inMobiNative) {

    }

    @Override
    public void onAdDisplayed(InMobiNative inMobiNative) {

    }

    @Override
    public void onUserLeftApplication(InMobiNative inMobiNative) {

    }

    @Override
    public void onAdLoadSucceeded(InMobiBanner inMobiBanner) {


    }

    @Override
    public void onAdLoadFailed(InMobiBanner inMobiBanner, InMobiAdRequestStatus inMobiAdRequestStatus) {
        Log.d(TAG, "Failed to load ad. " + inMobiAdRequestStatus.getMessage());

    }

    @Override
    public void onAdDisplayed(InMobiBanner inMobiBanner) {

    }

    @Override
    public void onAdDismissed(InMobiBanner inMobiBanner) {

    }

    @Override
    public void onAdInteraction(InMobiBanner inMobiBanner, Map<Object, Object> map) {

    }

    @Override
    public void onUserLeftApplication(InMobiBanner inMobiBanner) {

    }

    @Override
    public void onAdRewardActionCompleted(InMobiBanner inMobiBanner, Map<Object, Object> map) {

    }

    @Override
    public void onAdLoadSucceeded(@NonNull InMobiNativeStrand inMobiNativeStrand) {
        mAdView = inMobiNativeStrand.getStrandView(mAdView, mContainer);
        if (mAdView == null) {
            Log.d(TAG, "Could not render Strand!");
        } else {
            mContainer.addView(mAdView);
            Log.d(TAG, " rendered Strand!");
        }
    }

    @Override
    public void onAdLoadFailed(@NonNull InMobiNativeStrand inMobiNativeStrand, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
        Log.d(TAG, "Failed to load Video ad. " + inMobiAdRequestStatus.getMessage());
    }

    @Override
    public void onAdImpressed(@NonNull InMobiNativeStrand inMobiNativeStrand) {

    }

    @Override
    public void onAdClicked(@NonNull InMobiNativeStrand inMobiNativeStrand) {

    }

    @Override
    public void onAdLoadFailed(InMobiInterstitial inMobiInterstitial, InMobiAdRequestStatus inMobiAdRequestStatus) {
        Log.d(TAG, "Unable to load interstitial ad (error message: " +
                inMobiAdRequestStatus.getMessage());
    }

    @Override
    public void onAdReceived(InMobiInterstitial inMobiInterstitial) {
        Log.d(TAG, "onAdReceived");
    }

    @Override
    public void onAdLoadSucceeded(InMobiInterstitial inMobiInterstitial) {
        Log.d(TAG, "onAdLoadSuccessful");
    }

    @Override
    public void onAdRewardActionCompleted(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
        Log.d(TAG, "onAdRewardActionCompleted " + map.size());
    }

    @Override
    public void onAdDisplayFailed(InMobiInterstitial inMobiInterstitial) {
        Log.d(TAG, "onAdDisplayFailed " + "FAILED");

    }

    @Override
    public void onAdWillDisplay(InMobiInterstitial inMobiInterstitial) {
        Log.d(TAG, "onAdWillDisplay " + inMobiInterstitial);

    }

    @Override
    public void onAdDisplayed(InMobiInterstitial inMobiInterstitial) {
        Log.d(TAG, "onAdDisplayed " + inMobiInterstitial);

    }

    @Override
    public void onAdInteraction(InMobiInterstitial inMobiInterstitial, Map<Object, Object> map) {
        Log.d(TAG, "onAdInteraction " + inMobiInterstitial);

    }

    @Override
    public void onAdDismissed(InMobiInterstitial inMobiInterstitial) {
        Log.d(TAG, "onAdDismissed " + inMobiInterstitial);

    }

    @Override
    public void onUserLeftApplication(InMobiInterstitial inMobiInterstitial) {
        Log.d(TAG, "onUserWillLeaveApplication " + inMobiInterstitial);

    }

}
