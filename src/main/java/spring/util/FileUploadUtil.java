package spring.util;

import java.io.File;

public class FileUploadUtil {
	
	public static String checkSameFileName(String filename, String path) {
		//인자로 받은 filename에서 확장자를 뺀 파일명을 가져내자!
		//이를 위해서 filename에 있는 "."의 위치를 알아야 내야 한다. 
		int period = filename.lastIndexOf(".");
		
		//파일명과 확장자를 분리한다. 
		String f_name = filename.substring(0,  period); //파일명
		String suffix = filename.substring(period);
		
		//전체경로(Controller측에서 만들어서 줄 것임)
		//현재 java파일은 @component처리하지 않을 것이므로 
		//@autowired도 할 수 없기 때문에 application을 통해 절대경로를 받을 수 없다.
		//받은 path의 경로에는 파일명이 빠져있을 것. 따라서...
		String saveFile = path+System.getProperty("file.separator")+filename;
			//System.getProperty("file.sperator")는 /냐 \냐의 문제를 한방에 해결해준다.
		
		//위의 경로(path+filename)를 가지고 존재여부를 확인하자!
		//이때 필요한 것이 java.io.File임
		File f = new File(saveFile);
		
		//존재할 경우를 생각해서 이름 변경시킬 때 사용하는 숫자를 하나 만들자.
		int idx=1;
		while(f.exists()) {//같은 이름의 파일이 존재하는 경우
			//파일명 뒤에 숫자를 붙여 파일명을 변경해야 한다.
			StringBuffer sb = new StringBuffer();
			sb.append(f_name);
			sb.append(idx++); //파일명에 숫자를 붙인 후 1 증가한다.
			sb.append(suffix);
			
			filename=sb.toString(); //변경한 파일이름
			
			saveFile = path+System.getProperty("file.separator")+filename;
			
			f= new File(saveFile); //변경한 파일이름으로 파일을 만듦.
		} //while의 끝 
		
		return filename; //중복되지 않는 파일명 반환
		
		
	}
}
