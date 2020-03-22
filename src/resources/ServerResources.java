package resources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import settings.GeneralSettings;
import settings.ServerSettings;

/**
 * Handles server resources, please use for server specific resources e.g. currency
 * 
 * For server specific settings use {@link ServerSettings}.
 * For generic settings use {@link GeneralSettings}.
 * 
 * @author Sam
 *
 */
public class ServerResources {
	/**
	 * Returns the path of the resource file for a given server and resource
	 * 
	 * @param serverID
	 * @param resourceName
	 * @return
	 */
	public Path getPath(String serverID, String resourceName) {
		return Paths.get(serverID+"/"+resourceName+".resource");
	}
	
	/**
	 * Returns the list of resources in a resource file, each element represents one line in the file
	 * 
	 * @param serverID
	 * @param resourceName
	 * @return
	 */
	public List<String> getResourceList(String serverID, String resourceName) {
		try {
			return Files.lines(getPath(serverID, resourceName))
					.collect(Collectors.toList());
		} catch (IOException e) {
			return new ArrayList<String>();
		}
	}
	
	/**
	 * Returns the stream of resources in a resource file, each element represents one line in the file
	 * 
	 * @param serverID
	 * @param resourceName
	 * @return
	 */
	public Stream<String> getResourceStream(String serverID, String resourceName) {
		try {
			return Files.lines(getPath(serverID, resourceName));
		} catch (IOException e) {
			return new ArrayList<String>().stream();
		}
	}
	
	/**
	 * Sets the list of resources in a resource file, each element of resourceList represents one line in the file
	 * 
	 * @param serverID
	 * @param resourceName
	 * @param resourceList
	 */
	public void setResourceList(String serverID, String resourceName, List<String> resourceList) {
		try {
			Files.write(getPath(serverID, resourceName), resourceList.stream().collect(Collectors.joining("\n")).getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}