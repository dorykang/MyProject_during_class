package spring.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownload
 우리가 이전에 servlet파일을 열면
 @WebServlet("/FileDownload")라고 원래 적혀있었는데 
 스프링 환경에서는 web.xml에 servlet으로 등록해줘야 한다...라고
 말씀하셨는데 web.xml에 가보니 이미 등록되어 있었다. ㅎㅎ
 */
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//파라미터 값들 받기(dir, filename) -- 스프링과 달리 직접 받아야한다. 
		String dir = request. getParameter("dir");
		String filename = request.getParameter("filename");
		
		//dir을 절대경로로 만들자.
			//내장객체인 ServletContext application이 필요하다. 
		String path = getServletContext().getRealPath(dir);
		
		//앞서 얻어낸 upload의 절대경로와 파일명을 연결하면 전체경로가 된다. 
		String fullPath = path+System.getProperty("file.separator")+filename;
		
		//전체경로를 가지고 File 객체를 생성한다. 
		File f = new File(fullPath);
		
		//바구니 역할
		byte[] buf = new byte[2048]; 
			//파일크기와 같은 크기로 만드는 경우 :: byte[] buf = new byte [(int)f.length()];
		
		//전송할 데이터가 Stream처리될 때 문자셋 지정(클라이언트에게 보내는 것=response)
		response.setContentType("application/octet-stream;charset=8859_1"); 
			//쌍따옴표 안의 내용은 이미 정해져 있음(Http protocol 통신시 header에 들어가는 내용)
		
		//다운로드 대화상자 처리
		response.setHeader("Content-Disposition", 
				"attachment;filename="+new String(filename.getBytes("utf-8"), "8859_1")); 
			// 1) setHeader()의 괄호 안의 내용은 대소문자 구분하니 주의!
			// 2) 파일명에 한글이 있는 경우를 대비해 처리해줘야 한다. 
				//자바 안에서는 유니코드를 사용하기 때문에 한글처리를 위해 utf-8을 써야 한다. 
		        //그런데 이를 클라이언트 화면에 보여주기 위해서는 8859_1의 형식이어야 한다. 
				//즉 filename을 utf-8로 잘게 다져서(byte) 8859_1로 바꾼다는 의미. 
		        //(이런 방식으로 명시하지 않으면 한글은 다 깨져서 온다.)
		
		//전송타입이 이진데이터(binary)가 될 수 있도록 설정해준다. 
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		//여기까지는 설정! 이제 진짜 파일 다운로드 가즈아아아아
		if(f.isFile()) {
			//서버 입장에서 파일을 input 받는 것
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			
			//요청한 곳으로 보내기 위해(서버 입장에서는 응답) 
			//스트림을 응답객체(response)로부터 얻어낸다.
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			
			int size = -1;
			
			//steam사용할 때는 try-catch 써야 한다. 
			try {
				//읽어서 보내기
				while((size = bis.read(buf)) != -1) {//더이상 읽을 것이 없을 때까지
					bos.write(buf, 0, size); //읽은 만큼 쓰도록 한다. 
					bos.flush();
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				if(bos != null) {
					bos.close();
				}
				if(bis != null) {
					bis.close();
				}
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
