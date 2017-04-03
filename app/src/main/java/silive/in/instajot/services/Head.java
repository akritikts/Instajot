package silive.in.instajot.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import silive.in.instajot.R;
import silive.in.instajot.activities.MainActivity;

/**
 * Created by akriti on 8/4/16.
 */
public class Head extends Service{
    WindowManager.LayoutParams params;
    private WindowManager mWindowManager;
    private ImageView chatHead;
    private View mfloatingHeadView;

    public Head() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mfloatingHeadView = LayoutInflater.from(this).inflate(R.layout.floating_head_view,null);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mWindowManager.addView(mfloatingHeadView, params);

        final View collapsedView = mfloatingHeadView.findViewById(R.id.collapse_view);
        final View expandedView = mfloatingHeadView.findViewById(R.id.expanded_container);
        ImageView closeButtonCollapsed = (ImageView) mfloatingHeadView.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopSelf();
            }
        });
        ImageView saveText = (ImageView) mfloatingHeadView.findViewById(R.id.text_btn);
        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ImageView recordAudio = (ImageView) mfloatingHeadView.findViewById(R.id.audio_btn);
        recordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        ImageView closeButton = (ImageView) mfloatingHeadView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collapsedView.setVisibility(View.VISIBLE);
                expandedView.setVisibility(View.GONE);
            }
        });
        ImageView openButton = (ImageView) mfloatingHeadView.findViewById(R.id.open_button);
        openButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open the application  click.
                Intent intent = new Intent(Head.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                //close the service and remove view from the view hierarchy
                stopSelf();
            }
        });

        mfloatingHeadView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        //the initial position.
                        initialX = params.x;
                        initialY = params.y;

                        //the touch location
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);



                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                //When user clicks on the image view of the collapsed layout,
                                //visibility of the collapsed layout will be changed to "View.GONE"
                                //and expanded view will become visible.
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        //Calculate the X and Y coordinates of the view.
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);


                        //Update the layout with new X & Y coordinate
                        mWindowManager.updateViewLayout(mfloatingHeadView, params);
                        return true;
                }
                return false;
            }
        });
    }


    private boolean isViewCollapsed() {
        return mfloatingHeadView == null || mfloatingHeadView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }

    

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mfloatingHeadView != null)
            mWindowManager.removeView(mfloatingHeadView);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}


