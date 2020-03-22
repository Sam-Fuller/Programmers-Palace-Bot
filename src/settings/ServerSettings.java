package settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import resources.ServerResources;

/**
 * Handles all server settings, please use if you need to save a setting for a specific server.
 * 
 * For generic settings use {@link GeneralSettings}.
 * For server resource files use {@link ServerResources}.
 * 
 * @author Sam
 *
 */
public class ServerSettings {
	//default prefix is !
	private static final String defaultPrefix = "!";
	
	/**
	 * creates a new file with the default settings
	 * 
	 * @param serverID
	 */
	private static void setDefaults(String serverID) {
		//create new directory if needed
		File fileDir = new File(serverID);
		if(!fileDir.exists()) fileDir.mkdirs();
		
		//create list of default settings
		List<Setting> defaultSettings = new ArrayList<Setting>();
		defaultSettings.add(new Setting("prefix", defaultPrefix));
		
		//write list to file
		try {
			Files.write(getPath(serverID), defaultSettings.stream().map(x -> x.toString()).collect(Collectors.joining("\n")).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the path of a server settings file
	 * 
	 * @param serverID - the ID of the server, can be found using event.getGuild().getID()
	 * @return the path of the file
	 */
	private static Path getPath(String serverID) {
		return Paths.get(serverID + "/ServerSettings.settings");
	}
		
	/**
	 * Returns a specific server setting
	 * 
	 * @param serverID - the ID of the server, can be found using event.getGuild().getId()
	 * @param settingName - the string name of the setting
	 * @return the value of the setting
	 */
	public static String getSetting(String serverID, String settingName) {
		try {
			//read all lines from file
			List<Setting> prefix = Files.lines(getPath(serverID))
					//convert to settings
					.map(x -> Setting.convertToSetting(x))
					//filter only the ones with the setting name
					.filter(x -> x.getName().equals(settingName))
					.collect(Collectors.toList());
			
			if(prefix.isEmpty()) {
				//if no prefix is found, reinitialise file and use default prefix
				setDefaults(serverID);
				return defaultPrefix;
			}
			
			return prefix.get(0).getValue();
			
		} catch (IOException e) {
			//if file not found create file and use default prefix
			setDefaults(serverID);
			return defaultPrefix;
		}
	}
	
	/**
	 * Sets a specific server setting
	 * 
	 * @param serverID - the ID of the server, can be found using event.getGuild().getId()
	 * @param setting - the new value of the setting
	 */
	public static void setSetting(String serverID, Setting setting) {
		List<Setting> settingsList;
		
		try {
			//read file to settings
			settingsList = Files.lines(getPath(serverID))
					.map(x -> Setting.convertToSetting(x))
					.collect(Collectors.toList());

		} catch (IOException e) {
			//if file not detected: reinitialise file and try again
			setDefaults(serverID);
			try {
				settingsList = Files.lines(getPath(serverID))
						.map(x -> Setting.convertToSetting(x))
						.collect(Collectors.toList());
			} catch (IOException f) {
				//if this still throws error print error stack
				f.printStackTrace();
				return;
			}
		}
		
		
		boolean found = false;
		//find and replace the correct setting
		for (int i = 0; i < settingsList.size() && !found; i++) {
			if(settingsList.get(i).getName().equals(setting.getName())) {
				//change setting
				found = true;
				settingsList.set(i, setting);
			}
		}
		
		
		//no setting with the same name found, add to the end of the file
		if (!found) {
			settingsList.add(setting);
		}
		
		
		//write settings to file
		try {
			Files.write(getPath(serverID), settingsList.stream().map(x -> x.toString()).collect(Collectors.joining("\n")).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
