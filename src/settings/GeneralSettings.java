package settings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import resources.ServerResources;

/**
 * Handles all general bot settings.
 * 
 * For server specific settings use {@link ServerSettings}.
 * For server resource files use {@link ServerResources}.
 * 
 * @author Sam
 *
 */
public class GeneralSettings {
	private static List<Setting> settingList = new ArrayList<Setting>();

	/**
	 * Initialise settings, only needs to be performed once in {@link bot.Main}
	 */
	public static void loadSettings() {
		Path filePath = Paths.get("GeneralSettings.conf");
		try {
			settingList = Files.lines(filePath)
					.map(x -> Setting.convertToSetting(x))
					.collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * returns a the setting value of a given setting name
	 * 
	 * @param settingName - the name of the setting
	 * @return - the value of the setting
	 */
	public static String getSetting(String settingName) {
		return settingList.stream()
				.filter(x -> x.getName().equals(settingName))
				.collect(Collectors.toList())
				.get(0)
				.getValue();
	}
}
