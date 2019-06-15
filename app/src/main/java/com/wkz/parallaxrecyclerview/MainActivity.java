package com.wkz.parallaxrecyclerview;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wkz.parallaxrecycler.ParallaxRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> mDatas = Arrays.asList(
            "http://img3.imgtn.bdimg.com/it/u=2144088491,2830150766&fm=15&gp=0.jpg",
            "http://img2.imgtn.bdimg.com/it/u=12227583,7322417&fm=15&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=633346867,2875846929&fm=15&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2384021022,3542285156&fm=15&gp=0.jpg",
            "http://img1.imgtn.bdimg.com/it/u=3548422650,2724790703&fm=15&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1419275690,566648140&fm=15&gp=0.jpg",
            "http://img5.imgtn.bdimg.com/it/u=1832098726,3750255665&fm=15&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2350314257,4252738694&fm=15&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=2648895514,2698678120&fm=15&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=3945620789,3035408210&fm=15&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=30120854,607908110&fm=15&gp=0.jpg",
            "https://f12.baidu.com/it/u=3001915256,970479921&fm=72",
            "https://f11.baidu.com/it/u=3087422712,1174175413&fm=72",
            "https://f10.baidu.com/it/u=642753244,1588215304&fm=72"
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
                            addAll(mDatas);
                            addAll(mDatas);
                            addAll(mDatas);
                            addAll(mDatas);
                            addAll(mDatas);
                        }})
                        .setOnItemClickListener((holder, position) -> {
                            Toast.makeText(MainActivity.this, "Clicked " + position, Toast.LENGTH_SHORT).show();
                        }));
    }
}
