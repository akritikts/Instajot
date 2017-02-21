package silive.in.instajot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public Intent i;
    Button startService, stopService,msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i = new Intent(this, Head.class);

        startService = (Button) findViewById(R.id.start);
        startService.setOnClickListener(this);
        stopService = (Button) findViewById(R.id.stop);
        stopService.setOnClickListener(this);
        msg =(Button)findViewById(R.id.msg);
        msg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.start)
            startService(i);
        if (v.getId() == R.id.stop)
            stopService(i);
        if (v.getId()==R.id.msg){
            Intent m = new Intent(this,Overview.class);
            startActivity(m);
        }
    }
    public  void fun(){
       // Intent intent = new Intent(this,Head.class);
        stopService(i);}
}
