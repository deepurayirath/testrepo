package DeepuSDET.Data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataReader {
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException
	{
		//read Json file to String
		String JsonContent = FileUtils.readFileToString(new File(filePath),
				StandardCharsets.UTF_8);
		
		//now String to HashMap using Jackson DataBIND
		
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(JsonContent, new TypeReference<List<HashMap<String ,String>>>(){});
		 /* Convert the String and create two Hashmaps(in this case) and the put that in one list*/
		return data;
		
		//{map, map1} data will be returned like this
	}

}
