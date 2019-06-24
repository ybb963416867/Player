package com.example.opengles;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;


/**
 * Created by hlwky001 on 2017/12/15.
 */

public class GlSurfaceView extends GLSurfaceView{

    private static final String TAG = "GlSurfaceView";
    private GlRender glRender;
    private OnGlSurfaceViewOncreateListener onGlSurfaceViewOncreateListener;

    public GlSurfaceView(Context context) {
        this(context, null);
    }

    public GlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        glRender = new GlRender(context);
        //设置egl版本为2.0
        setEGLContextClientVersion(2);
        //设置render
        setRenderer(glRender);
        //设置为手动刷新模式
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        glRender.setOnRenderRefreshListener(new OnRenderRefreshListener() {
            @Override
            public void onRefresh() {
                requestRender();
            }
        });
    }


    public void setOnGlSurfaceViewOncreateListener(OnGlSurfaceViewOncreateListener onGlSurfaceViewOncreateListener) {
        if(glRender != null)
        {
            glRender.setOnGlSurfaceViewOncreateListener(onGlSurfaceViewOncreateListener);
        }
    }

    public void setCodecType(int type)
    {
        if(glRender != null)
        {
            glRender.setCodecType(type);
        }
    }


    public void setFrameData(int w, int h, byte[] y, byte[] u, byte[] v)
    {
        Log.e(TAG,"GSufaceView中的setFrameData");
        if(glRender != null)
        {
            glRender.setFrameData(w, h, y, u, v);
            requestRender();
        }else{
            Log.e(TAG,"GlRender为空");
        }
    }

    public void cutVideoImg()
    {
        if(glRender != null)
        {
            glRender.cutVideoImg();
            requestRender();
        }
    }
}
