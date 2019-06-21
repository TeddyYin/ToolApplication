import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class TranslateKeyValue {
	
	// Create a ArrayList
	public static ArrayList<String> keyList = new ArrayList<String>();
	public static ArrayList<String> valueList = new ArrayList<String>();
	
	public static void main(String [] argv) throws Exception {
		Scanner sc = new Scanner(System.in);
		
		println("In C folder example :C:/OriginFolderPath");
		print("Please enter origin folder path :");
		
		// get existing folder path
		String aimsDirFile = sc.nextLine();
		
		print("Please enter create folder path :");

		// get create folder path
		String writeDirFile = sc.nextLine();

		// maybe plus check point
		// ...
		
		readKeyValue(keyList,valueList);
		
		recursionFile(aimsDirFile, writeDirFile);
	}
	
	public static void recursionFile(String aimsDirFile, String writeDirFile) throws Exception {
		File file = new File(aimsDirFile);
		File createFile = new File(writeDirFile);

		if(file.isDirectory()){
			println("dir " + aimsDirFile);
			
			String[] aimsList = file.list();
			
			for (int i = 0; i < aimsList.length; i++) {
				String aimsStr = file + "/" + aimsList[i];
				String createStr = createFile + "/" + aimsList[i];
				
				createFile.mkdirs();
				recursionFile(aimsStr, createStr);
			}
			
		}else if(file.isFile()){
			// only change .html file
			// if(file.isFile() && file.toString().indexOf(".html") > -1)
				
			createFile.createNewFile();
			
			toCN(aimsDirFile, writeDirFile, keyList, valueList);
		}
	}
	
	public static void toCN(String aimsStr, String writeStr, ArrayList<String> keyList, ArrayList<String> valueList) throws IOException{
		// readValue
		InputStreamReader indexHtml = new InputStreamReader(new FileInputStream(aimsStr), "UTF-8");
		
		// make you rich
		// InputStreamReader indexHtml = new InputStreamReader(new FileInputStream(aimsStr), "Big5");
		
		BufferedReader brindex = new BufferedReader(indexHtml);
		
		// write Value
		BufferedWriter bwindex = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeStr, false),"UTF-8"));
		
		// make you rich
		// BufferedWriter bwindex = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeStr, false),"Big5"));
		
		int editCount = 0;
		
		while (brindex.ready()) {
			
			String str = brindex.readLine();
			
			for(int i = 0; i < keyList.size(); i++){
				
				boolean keyB = keyList.get(i) != null && !keyList.get(i).equals("");
				boolean valueB = valueList.get(i) != null && !valueList.get(i).equals("");
				
				if(keyB && valueB && str.indexOf(keyList.get(i)) > -1){
					
					str = str.replace(keyList.get(i), valueList.get(i));
					//println(str); // it's change value
					
					editCount++;
					//i = keyList.size();
				}
			}
			
			bwindex.write(str);
			bwindex.write("\n");
			bwindex.flush();
		}
		
		println(writeStr + " write success and edit count = " + editCount);
		
		bwindex.close();
	}
	
	public static void readKeyValue(ArrayList<String> keyList, ArrayList<String> valueList) throws IOException{
		// readKey
		InputStreamReader isrKey = new InputStreamReader(new FileInputStream("readKey.txt"), "Unicode");
		BufferedReader brKey = new BufferedReader(isrKey);
		
		// readValue
		InputStreamReader isrValue = new InputStreamReader(new FileInputStream("readValue.txt"), "Unicode");
		BufferedReader brValue = new BufferedReader(isrValue);
		
		while (brKey.ready() && brValue.ready()) {
			keyList.add(brKey.readLine());
			valueList.add(brValue.readLine());
		}
	}
	
	private static void println(String message){
		System.out.println(message);
	}
	
	private static void print(String message){
		System.out.print(message);
	}
}