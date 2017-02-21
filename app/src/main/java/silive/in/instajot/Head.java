package silive.in.instajot;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by akriti on 8/4/16.
 */
public class Head extends Service{
    WindowManager.LayoutParams params;
    private WindowManager windowManager;
    private ImageView chatHead;
    View mView;


    @Override
    public void onCreate() {
        super.onCreate();
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        chatHead = new ImageView(this);
        chatHead.setImageResource(R.drawable.ic_launcher);





        params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displaymetrics);
        final int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;


        chatHead.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        Log.d("Chat","up");
                        mView = View.inflate(getApplicationContext(), R.layout.overview,null);
                        mView.setTag("TAG");
                        Log.d("Chat", "up");
                        int top = getApplicationContext().getResources().getDisplayMetrics().heightPixels / 2;
                        //final EditText etMassage = (EditText) mView.findViewById(R.id.text);
                        Button ButtonSend = (Button) mView.findViewById(R.id.button);
                        ButtonSend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Utils.printLog("clicked");
                /*mView.setVisibility(View.GONE);
                                if(!etMassage.getText().toString().equals(""))
                                {
                                    //Utils.printLog("sent");
                                    etMassage.setText("");
                                }*/
                            }
                        });
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX
                                + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY
                                + (int) (event.getRawY() - initialTouchY);
                        /*if (params.y <= (height/5)) {


                            Log.d("Chat", "gone");

                        } else*/
                            windowManager.updateViewLayout(chatHead, params);
                        return true;
                }
                return false;
            }
        });
        windowManager.addView(chatHead, params);

    }
    private void hideDialog(){
        if(mView != null && windowManager != null){//private void hideDialog();{
        if(mView != null && windowManager != null){
            windowManager.removeView(mView);
            mView = null;
        }
    }
            windowManager.removeView(mView);
            mView = null;
        }
    

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (chatHead != null)
            windowManager.removeView(chatHead);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /*Intent intent = new Intent(Intent.ACTION_VIEW);

    intent.setDataAndType(uri, "video/mp4");*/
    /*public void onClick(View arg0) {
        // TODO Auto-generated method stub
        String uriString = inputUri.getText().toString();
        Uri intentUri = Uri.parse(uriString);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(intentUri);

        startActivity(intent);

    }});

        }*/
}


