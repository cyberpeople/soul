package devxs.cyberpeople.soul;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CommandsActivity extends Activity {
	// Constants
    private static final int VOICE_REQUEST_CODE = 1911;
    private String ip;
    private String url;
    
    private TextView connectionText;
    private TextView messageText;
    
    public Actor actor;
    
    public void onCreate(Bundle savedInstanceState)
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.commands);
        
        connectionText = (TextView) findViewById(R.id.connection);
        messageText = (TextView) findViewById(R.id.message);
        
        ip = getIntent().getExtras().getString("ip").trim();
   		url = "http://" + ip + "/command.php?command=";
   		actor = new Actor(url, this);
        
        connectionText.setText( "Sending commands to '" + url + "'." );
    }
    
    public void setMsg (String s)
    {
    	messageText.setText( s );
    }
    
    // GUI
    public void speakButtonClicked(View v)
    {
        startVoiceRecognitionActivity();
    }
    
    public void forwardsButtonClicked(View v)	{ actor.move(Action.FORWARDS); }
    public void backwardsButtonClicked(View v)	{ actor.move(Action.BACKWARDS);}
	public void leftButtonClicked(View v)		{ actor.move(Action.TURNLEFT);}
	public void rightButtonClicked(View v)		{ actor.move(Action.TURNRIGHT);}
	public void stopButtonClicked(View v)		{ actor.move(Action.STOP);}    
    public void minorRightButtonClicked(View v) { actor.move(Action.TURNMINORRIGHT); }
    public void minorLeftButtonClicked(View v) 	{ actor.move(Action.TURNMINORLEFT); }
    
    public void toast (String message)
    {
    	Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity()
    {    	
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH); 
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Give a command to the robot!");
        startActivityForResult(intent, VOICE_REQUEST_CODE);
    }
 
    /**
     * Handle the results from the voice recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	// If the Activity is ours and has returned OK
        if (requestCode == VOICE_REQUEST_CODE && resultCode == RESULT_OK)
        {
        	// Get the matches
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);	
        	String input = matches.get(0);
            
        	// Try and match the first match against the commands
        	Similar s = new Similar();
            String bestMatch = s.closestMatch(input);
            onCommand(bestMatch, input);
        }
        else if (requestCode == RecognizerIntent.RESULT_NO_MATCH)
        {
        	toast( getString(R.string.NO_COMMAND) + "." );
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    // Send commands
    
    private void onCommand (String command, String input)
    {
    	if (command == "NONE")
    	{
    		toast( getString(R.string.NO_COMMAND) + " when you said \"" + input + "\"." );
    	}
    	else
    	{
	    	toast( "You said: \"" + input + "\" and it was interpreted as " + command + "." );
	    	actor.move(command);
    	}
    }
}
