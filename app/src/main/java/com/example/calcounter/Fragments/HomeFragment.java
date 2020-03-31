package com.example.calcounter.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.calcounter.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        final ViewPager viewPager = view.findViewById(R.id.quoteViewPager);
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(true, new DepthPageTransformer());

        ImageButton rightBtn = view.findViewById(R.id.rightBtn);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int location = viewPager.getCurrentItem();
                location++;
                viewPager.setCurrentItem(location >= adapter.getCount() ? 0 : location);
            }
        });

        ImageButton leftBtn = view.findViewById(R.id.leftBtn);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int location = viewPager.getCurrentItem();
                location--;
                viewPager.setCurrentItem(location >= adapter.getCount() ? 0 : location);
            }
        });

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("EEE, MMM d");
        timeFormat = new SimpleDateFormat("h:mm a");

        String date = dateFormat.format(calendar.getTime());
        String time = timeFormat.format(calendar.getTime());

        TextView dateText = view.findViewById(R.id.date);
        TextView timeText = view.findViewById(R.id.time);

        dateText.setText(date);
        timeText.setText(time);



        return view;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        int size = 3;

        @NonNull
        @Override
        public Fragment getItem(int position) {
            position = position % size;

            switch (position) {
                case 0:
                    return ViewPagerFragment.newInstance("You Can Do it!");
                case 1:
                    return ViewPagerFragment.newInstance("Keep Pushing Forward!");
                case 2:
                    return ViewPagerFragment.newInstance("Believe in yourself!");

                default:
                    return ViewPagerFragment.newInstance("ERROR");

            }

        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }
    }

    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }
}