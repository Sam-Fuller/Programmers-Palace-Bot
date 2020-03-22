package commands.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commands.Command;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * This command changes the discord servers prefix, usable only by admins
 * implements {@link Command}
 * 
 * @author Sam
 *
 */
public class SetPrefix implements Command{
	
	/**
	 * returns a list of aliases
	 */
	@Override
	public List<String> aliases() {
		return new ArrayList<String>(Arrays.asList("")); //insert aliases here
	}

	/**
	 * change the prefix to the given argument
	 */
	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		//set prefix to the next argument
		
	}

	/**
	 * returns a help field for the command
	 */
	@Override
	public List<Field> help(MessageReceivedEvent event, String[] args) {
		//return a useful help field
		return null;
	}

}
