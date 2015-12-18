package fr.xs.jtk.format.json.parser;

import java.util.List;
import java.util.Map;

/**
 * Container factory for creating containers for JSON object and JSON array.
 * 
 * @see fr.xs.jtk.format.json.parser.JSONParser#parse(java.io.Reader, ContainerFactory)
 * 
 * @author FangYidong
 */
public interface ContainerFactory {
	/**
	 * @return A Map instance to store JSON object, or null if you want to use fr.xs.jtk.format.json.JSONObject.
	 */
	Map<?,?> createObjectContainer();
	
	/**
	 * @return A List instance to store JSON array, or null if you want to use fr.xs.jtk.format.json.JSONArray. 
	 */
	List<?> creatArrayContainer();
}
