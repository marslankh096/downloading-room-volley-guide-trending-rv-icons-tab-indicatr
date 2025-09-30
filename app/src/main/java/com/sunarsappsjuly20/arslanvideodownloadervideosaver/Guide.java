package com.sunarsappsjuly20.arslanvideodownloadervideosaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import com.sinaseyfi.advancedcardview.AdvancedCardView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import java.util.ArrayList;
import java.util.List;

public class Guide extends AppCompatActivity {

    private ViewPager2 runs_pgr1;
    private AdvancedCardView advancedCardView1;
    private Button btn_gotit_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.showns_guide);

        advancedCardView1 = findViewById(R.id.info_back);
        btn_gotit_next = findViewById(R.id.info_next);
        ImageView runs3 = findViewById(R.id.close);

        runs_pgr1 = findViewById(R.id.guide_pager);
        DotsIndicator runs6 = findViewById(R.id.dots_indicator);

        GuideAdapter adapter = new GuideAdapter(this, getList());
        runs_pgr1.setAdapter(adapter);

        runs6.setViewPager2(runs_pgr1);
        runs_pgr1.registerOnPageChangeCallback(callback);

        advancedCardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runs_pgr1.setCurrentItem(runs_pgr1.getCurrentItem() - 1);
            }
        });

        btn_gotit_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runs_pgr1.setCurrentItem(runs_pgr1.getCurrentItem() + 1);
            }
        });

        runs3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    ViewPager2.OnPageChangeCallback callback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            switch (position) {
                case 0:
                    advancedCardView1.setVisibility(View.GONE);
                    btn_gotit_next.setText("Next");
                    break;
                case 3:
                    btn_gotit_next.setText("Got it");
                    startActivity(new Intent(getApplicationContext(), Home.class));
                    finish();
                    break;
                default:
                    advancedCardView1.setVisibility(View.VISIBLE);
                    btn_gotit_next.setText("Next");
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        runs_pgr1.unregisterOnPageChangeCallback(callback);
    }

    private List<GuideModel> getList() {
        List<GuideModel> guideModel_for_newUsersallusers = new ArrayList<>();
        guideModel_for_newUsersallusers.add(new GuideModel("1", "Go to Website", R.drawable.info_one));
        guideModel_for_newUsersallusers.add(new GuideModel("2", "Play Video & hit Download Icon", R.drawable.info_two));
        guideModel_for_newUsersallusers.add(new GuideModel("3", "Click on Fast Download", R.drawable.info_three_));
        guideModel_for_newUsersallusers.add(new GuideModel("1", "Go to website", 0));
        return guideModel_for_newUsersallusers;
    }

}