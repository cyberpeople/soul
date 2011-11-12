package devxs.cyberpeople.soul;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
 
/**
 * A very simple application to handle Voice Recognition intents
 * and display the results
 */
public class SoulActivity extends Activity
{
    private static final int VOICE_REQUEST_CODE = 1911;
    private TextView tv;
    
    /**
     * Called with the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recog);
 
        // Button speakButtonWeb = (Button) findViewById(R.id.speakButtonWeb);
        // Button speakButtonFree = (Button) findViewById(R.id.speakButtonFree);
        tv = (TextView) findViewById(R.id.result);
        
        // Disable button if no recognition service is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0)
        {
        	Toast.makeText(this, "Voice support it not availible.", Toast.LENGTH_LONG).show();
        }
        else
        {
        	Toast.makeText(this, "Voice support is (probably) working.", Toast.LENGTH_LONG).show();
        }
    }
    
    // GUI
 
    /**
     * Handle the action of the button being clicked
     */
    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }
    
    // Voice recognition
    
    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {    	
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH); 
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Give a command to the robot!");
        startActivityForResult(intent, VOICE_REQUEST_CODE);
    }
 
    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == VOICE_REQUEST_CODE && resultCode == RESULT_OK)
        {
            // Populate the wordsList with the String values the recognition engine thought it heard
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
        	// wordsList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, matches));
        	
        	String bestMatch = matches.get(0);
            //Toast.makeText(this, bestMatch, Toast.LENGTH_LONG).show();
            tv.setText(bestMatch);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    private Direction handleResult(String s)
    {
    	return Direction.LEFT;
    }
    
    // Send commands
    
    /*
     * Send http requests and POST data (?) to a glue thing
     */
}