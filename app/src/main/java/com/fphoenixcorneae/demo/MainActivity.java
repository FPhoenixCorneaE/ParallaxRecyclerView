package com.fphoenixcorneae.demo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fphoenixcorneae.parallax.ParallaxRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mDatas = Arrays.asList(
            "http://img2.imgtn.bdimg.com/it/u=12227583,7322417&fm=15&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3548422650,2724790703&fm=15&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2350314257,4252738694&fm=15&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3945620789,3035408210&fm=15&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=30120854,607908110&fm=15&gp=0.jpg",
            "https://f12.baidu.com/it/u=3001915256,970479921&fm=72"
    );
    private ParallaxRecyclerView mRvParallax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRvParallax = (ParallaxRecyclerView) findViewById(R.id.rvParallax);

        mRvParallax.setLayoutManager(new LinearLayoutManager(this));
        mRvParallax.setHasFixedSize(true);
        mRvParallax.setAdapter(
                new ParallaxRecyclerAdapter(
                        this,
                        new ArrayList<String>() {{
                            for (int i = 0; i < 5; i++) {
                                addAll(mDatas);
                            }
                        }})
                        .setOnItemClickListener((holder, position) -> {
                            Toast.makeText(MainActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
                        }));
    }
}
