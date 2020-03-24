package commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface CommandCollection extends Command{
	/**
	 * returns a list of all sub commands
	 * @return list of sub commands
	 */
	public List<Command> subCommands();
	
	/**
	 * returns a possible sub command if it matches the input arguments
	 */
	@Override
	public default Optional<Command> valid(MessageReceivedEvent event, String[] args) {
		if (!this.aliases().contains(args[0].toLowerCase())) return Optional.empty();
		
		if (args.length == 0) return Optional.of(this);
		
		String[] subargs = Arrays.copyOfRange(args, 1, args.length);
		
		List<Command> validCommands = subCommands().stream()
		.map(x -> x.valid(event, subargs))
		.filter(x -> x.isPresent())
		.map(x -> x.get())
		.collect(Collectors.toList());
		
		if (validCommands.isEmpty()) return Optional.empty();
		
		return Optional.of(validCommands.get(0));
	}

	/**
	 * returns a list of all help fields of sub commands
	 */
	@Override
	public default List<Field> help(MessageReceivedEvent event, String[] args) {
		List<Field> fieldList = new ArrayList<Field>();
		
		if (args.length != 0) {
			String[] subargs = Arrays.copyOfRange(args, 1, args.length);
			
			List<Command> validCommands = subCommands().stream()
				.map(x -> x.valid(event, subargs))
				.filter(x -> x.isPresent())
				.map(x -> x.get())
				.collect(Collectors.toList());
			
			if (validCommands.isEmpty()) {
				fieldList.add(new Field("ERROR","Command not found",false));
			} else {
				validCommands.get(0).help(event, subargs);
			}
			
		}else {
			subCommands().stream().forEach(x -> fieldList.addAll(x.help(event, args)));
		}
		
		return fieldList;
	}
	
	/**
	 * default run command, command collections typically perform no action
	 */
	@Override
	public default void run(MessageReceivedEvent event, String[] args) {}
}
