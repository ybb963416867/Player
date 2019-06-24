package com.example.play;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.opengles.WlGlSurfaceView;

public class MainActivity extends AppCompatActivity {

    private Player mPlayer;
    private Button play;
    private WlGlSurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.btn_play);
        surfaceView = findViewById(R.id.surfaceView);
        mPlayer = new Player();
        mPlayer.setWlGlSurfaceView(surfaceView);
//        dnPlayer.setSurfaceView(surfaceView);
        Button  stop=findViewById(R.id.btn_stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dnPlayer.stop();
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPlayer.prepare();
            }
        });
        mPlayer.setPrepareListener(new Player.OnPrepareListener() {
            @Override
            public void onPrepare() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "准备播放了", Toast.LENGTH_SHORT).show();
                        mPlayer.start();
                    }
                });
            }
        });
        mPlayer.setDataSource("rtmp://39.105.24.94/myapp/123");
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPlayer.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.release();
    }
}
