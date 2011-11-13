package devxs.cyberpeople.soul;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.widget.Toast;

public class Actor {
	
	private String url;
	private CommandsActivity ca;
	
	Actor (String url, CommandsActivity ca)
	{
		this.url = url;
		this.ca = ca;
	}
	
	public void move (String s)
	{
		if (s == "forwards")
			move(Action.FORWARDS);
		else if (s == "backwards")
			move(Action.BACKWARDS);
		else if (s == "spin")
			move(Action.TURNLEFT);
		else if (s == "rotate")
			move(Action.TURNRIGHT);
		else if (s == "stop")
			move(Action.STOP);
	}
	
	public void move (Action a)
	{
		sendRequest( doMove(a) );
	}
	
	public String doMove(Action a)
	{
		switch (a)
		{
			case FORWARDS:
				return "forward";
			case BACKWARDS:
				return "back";
			case TURNLEFT:
				return "turnLeft";
			case TURNRIGHT:
				return "turnRight";
			case STOP:
			default:
				return "stop";
		}
	}
	
	public void toast (String m)
	{
		Toast.makeText(ca, m, Toast.LENGTH_SHORT).show();
	}
	
	public void sendRequest(String s)
	{
		url = url + "?" + s;
		InputStream is;
		try
		{
			is = new URL(url).openStream();
			is.close();
		}
		catch (MalformedURLException a)
		{
			toast("Could not request url ["+url+"].");
		}
		catch (IOException e) {
			toast("Could not request url ["+url+"]!");
		}
	}
	
	public Action getDirection(String s)
	{
		if (s.equals("forwards"))
			return Action.FORWARDS;
		else if (s.equals("backwards"))
			return Action.BACKWARDS;
		else if (s.equals("left"))
			return Action.TURNLEFT;
		else if (s.equals("right"))
			return Action.TURNRIGHT;
		else // if (s.equals("stop"))
			return Action.STOP;
	}
}
