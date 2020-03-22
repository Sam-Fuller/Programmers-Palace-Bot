package commands;

import java.util.List;
import java.util.Optional;

import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Command {

	List<String> aliases();

	void run(MessageReceivedEvent event, String[] args);

	List<Field> help(MessageReceivedEvent event, String[] args);

	default Optional<Command> valid(MessageReceivedEvent event, String[] args) {
		return null;
	};

}
