package bot;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 * Extends {@link net.dv8tion.jda.api.hooks.ListenerAdapter}
 * When a message is received, a new {@link MessageEvent} is started on a new thread
 * 
 * @author Sam
 *
 */
public class MessageListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		new MessageEvent(event).start();
	}
}
