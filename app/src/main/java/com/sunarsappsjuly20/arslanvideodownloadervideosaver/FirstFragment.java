package com.sunarsappsjuly20.arslanvideodownloadervideosaver;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.widget.LinearLayout;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.first_frag, container, false);
        TextView tv = v.findViewById(R.id.tvFragFirst);

        LinearLayout likeButtonsLayout = v.findViewById(R.id.like_buttons);
        likeButtonsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for like_buttons here
                // For example, you can open a new window or perform any action
                BrowserManager browserManager = new BrowserManager(getActivity());
                browserManager.newWindow("https://www.imdb.com"); // Example URL
            }
        });


       LinearLayout fb_buttons = v.findViewById(R.id.fb_buttons);
        fb_buttons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event for like_buttons here
                // For example, you can open a new window or perform any action
                BrowserManager browserManager = new BrowserManager(getActivity());
                browserManager.newWindow("https://www.facebook.com/watch"); // Example URL
            }
        });

        return v;
    }
}
