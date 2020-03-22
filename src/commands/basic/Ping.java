package commands.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import commands.Command;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * A simple ping command, replies with pong
 * implements {@link Command}
 * 
 * @author Sam
 *
 */
public class Ping implements Command{
	/**
	 * returns a list of all aliases
	 */
	@Override
	public List<String> aliases() {
		return new ArrayList<String>(Arrays.asList("ping"));
	}

	/**
	 * send pong message to the chat the command was sent from
	 */
	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		event.getChannel().sendMessage("pong").queue();
	}

	/**
	 * Returns a help field
	 */
	@Override
	public List<Field> help(MessageReceivedEvent event, String[] args) {
		List<Field> help = new ArrayList<Field>();
		help.add(new Field("!ping","Use: !ping\nAliases: " + aliases().toString() + "\nDescription: pong",false));
		return help;
	}
	
}
