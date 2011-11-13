package devxs.cyberpeople.soul;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ConnectActivity extends Activity {
	private String ip;
	
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect);
    }
    
    public void connectButtonClicked(View v)
    {
    	EditText ipText = (EditText) findViewById(R.id.connectText);
    	
    	ip = ipText.getText().toString();
    	
    	Intent moveToCommands = new Intent(v.getContext(), CommandsActivity.class);
    	moveToCommands.putExtra("ip", ip);
    	startActivity(moveToCommands);
    	
    }
}
