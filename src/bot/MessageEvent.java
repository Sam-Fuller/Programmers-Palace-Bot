package bot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import commands.Command;
import commands.basic.Help;
import commands.basic.Ping;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import settings.ServerSettings;
/**
 * Extends {@link java.lang.Thread}
 * Handles message received event bot commands
 * 
 * @author Sam
 *
 */
public class MessageEvent extends Thread {
	public static List<Command> commands(){
		//create a list of all commands
		List<Command> commands = new ArrayList<Command>();
		
		commands.add(new Ping());
		commands.add(new Help());
		//ADD NEW COMMANDS HERE <-----------------------------------------------------------------------------------
		
		return commands;
	}
	
	MessageReceivedEvent event;

	public MessageEvent(MessageReceivedEvent event) {
		this.event = event;
	}

	public void run() {
		//do not accept commands from bots
		if(event.getAuthor().isBot()) return;
		
		//get the message input
		String input = event.getMessage().getContentRaw();
		//get the server prefix
		String prefix = ServerSettings.getSetting(event.getGuild().getId(), "prefix");
		//check prefix is present
		if(!input.startsWith(prefix)) return;
		
		
		//remove prefix from string
		input = input.substring(prefix.length(), input.length());
		//split input into its arguments
		String[] args = input.split(" ");
		
		
		//find possible valid command
		List<Command> validCommand = commands().stream()
				.map(x -> x.valid(event, args))
				.filter(x -> x.isPresent())
				.map(x -> x.get())
				.collect(Collectors.toList());
		
		//if a valid command does not exist
		if (validCommand.isEmpty()) {
			//send error message
			event.getChannel().sendMessage("Command not recognised").queue();
			return;
		}
		
		//else run the command
		validCommand.get(0).run(event, args);
		
	}
}
