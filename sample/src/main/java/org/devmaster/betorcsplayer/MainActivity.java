package org.devmaster.betorcsplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import org.devmaster.player.PlayerView;
import org.devmaster.player.Source;

public class MainActivity extends AppCompatActivity {

    private PlayerView mPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPlayerView = (PlayerView) findViewById(R.id.player);
        mPlayerView.setFullscreenView((FrameLayout) findViewById(R.id.fullscreen));
    }

    public void onClickYouTube(View view) {
        mPlayerView.loadVideo("2Vf1D-rUMwE", Source.YOUTUBE);
    }

    public void onClickVimeo(View view) {
        mPlayerView.loadVideo("236370796", Source.VIMEO);
    }
}
