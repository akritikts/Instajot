package silive.in.instajot.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import silive.in.instajot.services.Head;
import silive.in.instajot.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Intent i;
    public static Button startService, stopService, msg;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {


            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }

    }
    private void initializeView(){
        i = new Intent(this, Head.class);

        startService = (Button) findViewById(R.id.start);
        startService.setOnClickListener(this);
        stopService = (Button) findViewById(R.id.stop);
        stopService.setOnClickListener(this);
        msg = (Button) findViewById(R.id.msg);
        msg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start)
            startService(i);
        if (v.getId() == R.id.stop)
            stopService(i);
        if (v.getId() == R.id.msg) {
            Intent m = new Intent(this, Overview.class);
            startActivity(m);
        }
    }

    /*public void fun_stop_service() {
        // Intent intent = new Intent(this,Head.class);
        stopService(i);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
