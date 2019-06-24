package com.example.play;

import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.example.opengles.WlGlSurfaceView;

/**
 * 提供java进行播放  停止 等函数
 */
public class Player {
    static {
        System.loadLibrary("native-lib");
    }

    private String dataSource;
    private SurfaceHolder holder;
    private OnPrepareListener  listener;
    private WlGlSurfaceView wlGlSurfaceView;
    /**
     * 渲染surface
     */
    private Surface surface;

    public OnPrepareListener getOnPrepareListener() {
        return listener;
    }

    public void setPrepareListener(OnPrepareListener listener) {
        this.listener = listener;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

//    public void setSurfaceView(SurfaceView surfaceView) {
//        holder = surfaceView.getHolder();
//        holder.addCallback(this);
//    }

    /**
     * 开始播放
     */
    public void start() {
        native_start();
    }

    /**
     * 停止播放
     */
    public void stop() {
        native_stop();
    }

    /**
     * 准备好要播放的视频
     */
    public void prepare() {
        native_prepare(dataSource);
    }

//    /**
//     * 画布创建好了
//     *
//     * @param holder
//     */
//    @Override
//    public void surfaceCreated(SurfaceHolder holder) {
//
//    }
//
//    /**
//     * 画布的改变的时候
//     *
//     * @param holder
//     * @param format
//     * @param width
//     * @param height
//     */
//    @Override
//    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//        native_setSurface(holder.getSurface());
//    }
//
//    /**
//     * 画布的销毁
//     *
//     * @param holder
//     */
//    @Override
//    public void surfaceDestroyed(SurfaceHolder holder) {
//
//    }

    public void release() {
//        if (holder != null) {
//            holder.removeCallback(this);
//        }
        native_release();
    }

    public void onError(int errorCode) {
        Log.e("DNPlayer",errorCode+"");
    }


    //设置一个画图框架
    public void setWlGlSurfaceView(WlGlSurfaceView wlGlSurfaceView) {
        this.wlGlSurfaceView = wlGlSurfaceView;
//        wlGlSurfaceView.setOnGlSurfaceViewOncreateListener(new WlOnGlSurfaceViewOncreateListener() {
//            @Override
//            public void onGlSurfaceViewOncreate(Surface s) {
//                if(surface == null)
//                {
//                    setSurface(s);
//                }
//                if(parpared && !TextUtils.isDigitsOnly(dataSource))
//                {
//                    wlPrepared(dataSource, isOnlyMusic);
//                }
//            }
//
//            @Override
//            public void onCutVideoImg(Bitmap bitmap) {
//                if(wlOnCutVideoImgListener != null)
//                {
//                    wlOnCutVideoImgListener.onCutVideoImg(bitmap);
//                }
//            }
//        });
    }

    private void setSurface(Surface surface) {
        this.surface = surface;
    }

    public void  setFrameData(int  w,int h, byte[] y, byte[] u, byte[] v){
        Log.e("ybb","调用了java setFrameData");

        if(wlGlSurfaceView != null)
        {
            wlGlSurfaceView.setCodecType(0);
            //调用软解码渲染界面
            wlGlSurfaceView.setFrameData(w, h, y, u, v);
        }else{
            Log.e("ybb","wlGlSurfaceView为空");
        }

    }

    public interface OnPrepareListener {
        void onPrepare();
    }

    public void onPrepare(){
        if (null != listener){
            listener.onPrepare();
        }
    }

    native void native_prepare(String dataSource);
    native void native_start();
//    native void native_setSurface(Surface surface);
    native void native_stop();
    native void native_release();


}
