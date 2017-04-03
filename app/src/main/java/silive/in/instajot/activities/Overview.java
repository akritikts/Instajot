package silive.in.instajot.activities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import silive.in.instajot.R;

/**
 * Created by akriti on 9/4/16.
 */
public class Overview extends AppCompatActivity implements View.OnClickListener{
    public static Button button;
    public static Button record_audio,save_text;
    public static EditText msg_text;
    File audiofile = null;
    public static int flag = 1;
    MediaRecorder recorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overview);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
        msg_text = (EditText)findViewById(R.id.msg_text);
        save_text = (Button)findViewById(R.id.save_text);
        record_audio = (Button)findViewById(R.id.rec_audio);
        record_audio.setOnClickListener(this);
        button.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.rec_audio){
            flag = 2;
            record_audio.setEnabled(false);
            button.setEnabled(true);

            File sampleDir = Environment.getExternalStorageDirectory();
            try {
                audiofile = File.createTempFile("sound", ".3gp", sampleDir);
            } catch (IOException e) {
                Log.e("TAG", "sdcard access error");
                return;
            }
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile(audiofile.getAbsolutePath());
            try {
                recorder.prepare();
            } catch (IOException e) {
                Log.e("TAG", "record  error");
            }

            recorder.start();

        }
        if (v.getId()==R.id.button){
            if (flag==2){
                ContentValues values = new ContentValues(4);
                long current = System.currentTimeMillis();
                values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
                values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
                values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
                values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());
                ContentResolver contentResolver = getContentResolver();

                Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                Uri newUri = contentResolver.insert(base, values);

                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
                Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
            }
            else {
                msg_text.setVisibility(View.GONE);
                Toast.makeText(this, "Added File ", Toast.LENGTH_LONG).show();
            }



            }
            if (v.getId()==R.id.save_text){
                flag = 1;
                msg_text.setVisibility(View.VISIBLE);
            }

    }
}
