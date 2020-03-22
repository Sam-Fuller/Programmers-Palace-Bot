package bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.RateLimitedException;
import settings.GeneralSettings;
import net.dv8tion.jda.api.entities.Activity;

public class Main {
	public static String DISCORD_TOKEN;
	public static String ACTIVITY;
	
    //JDA instance
    public static JDA discord;

    //Connects the bot to discord
    public static void main(String[] args) throws LoginException, InterruptedException, RateLimitedException {
    	//load the settings and assign settings variables
    	GeneralSettings.loadSettings();
    	DISCORD_TOKEN = GeneralSettings.getSetting("token");
    	ACTIVITY = GeneralSettings.getSetting("activity");
    	System.out.println("settings loaded");
    	
    	//construct JDA instance
        discord = new JDABuilder(AccountType.BOT)
                .setActivity(Activity.playing(ACTIVITY))
                .setToken(DISCORD_TOKEN)
                .addEventListeners(new MessageListener())
                .build();
                
        System.out.println("bot started succesfully");
    }
}
