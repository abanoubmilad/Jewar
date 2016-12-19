package abanoubm.jewar;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Home extends FragmentActivity {
    private ViewPager mPager;

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_PAGES = 3;

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new FragmentSearchBooks();
            else if (position == 1)
                return new FragmentCurrentBooks();
            else
                return new FragmentUserMap();

        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    private class SignOut extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            JewarApi.sign_out();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


        }
    }

    private int mCurrentTab = 0;
    private ImageView[] buttons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
        buttons = new ImageView[]{
                (ImageView) findViewById(R.id.img1),
                (ImageView) findViewById(R.id.img2),
                (ImageView) findViewById(R.id.img3),
                (ImageView) findViewById(R.id.img4)
        };
        buttons[0].setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        buttons[3].setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new SignOut().execute();
                finish();
                Intent intent = new Intent(getApplicationContext(), SignIn.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        buttons[0].setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCurrentTab != 0)
                    mPager.setCurrentItem(0);


            }
        });
        buttons[1].setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCurrentTab != 1)
                    mPager.setCurrentItem(1);


            }
        });
        buttons[2].setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mCurrentTab != 2)
                    mPager.setCurrentItem(2);

            }
        });

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fireTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void fireTab(int changedTagCursor) {

        buttons[mCurrentTab].setBackgroundColor(Color.WHITE);
        mCurrentTab = changedTagCursor;
        buttons[changedTagCursor].setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

}
