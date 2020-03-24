package commands.basic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import bot.MessageEvent;
import commands.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * A help command, displays the help of any given command
 * implements {@link Command}
 * 
 * @author Sam
 *
 */
public class Help implements Command{
	/**
	 * returns a list of all aliases
	 */
	@Override
	public List<String> aliases() {
		return new ArrayList<String>(Arrays.asList("help", "h"));
	}

	/**
	 * Sends the help message to the chat the command was sent from
	 */
	@Override
	public void run(MessageReceivedEvent event, String[] args) {
		//EmbedBuilder for the message
		EmbedBuilder helpMessage = new EmbedBuilder();
		helpMessage.setColor(Color.WHITE);

		//If specific command is given
		if (args.length != 1) {
			//array of sub arguments (everything after !help)
			String[] subargs = Arrays.copyOfRange(args, 1, args.length);

			//retrieve valid command
			List<Command> validCommands = MessageEvent.commands().stream()
					.map(x -> x.valid(event, subargs))
					.filter(x -> x.isPresent())
					.map(x -> x.get())
					.collect(Collectors.toList());

			//if no valid command
			if (validCommands.isEmpty()) {
				//send error message
				helpMessage.addField(new Field("ERROR","Command not found",false));
				return;
				
			} else {
				//else send the help of the specified command
				validCommands.get(0).help(event, subargs);
			}

			
		}else {
			//if no specific command is given
			//send all help blocks
			MessageEvent.commands().stream()
			.forEach(x -> {
				x.help(event, args).stream()
				.forEach(helpMessage::addField);});
		}

		//send help message
		event.getChannel().sendMessage(helpMessage.setColor(Color.RED).build()).complete();
	}

	/**
	 * Returns a help field
	 */
	@Override
	public List<Field> help(MessageReceivedEvent event, String[] args) {
		List<Field> help = new ArrayList<Field>();
		help.add(new Field("!help","Use: !help\nAliases: " + aliases().toString() + "\nDescription: show all commands",false));
		return help;
	}

}
