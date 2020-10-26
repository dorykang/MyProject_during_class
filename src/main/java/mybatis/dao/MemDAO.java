package mybatis.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.MemVO;

@Component
public class MemDAO {
	@Autowired
	private SqlSessionTemplate sst;
	//이렇게 하면 따로 injection해주지 않아도(setter 만들지 않아도)
	//sst에 값이 자동으로 들어가게 된다. 
	//단 이렇게 하기 위해서는 SqlSessionTemplate이 먼저 생성되어 있어야 한다.
		//그렇기 위해서는 root-context.xml에서 SqlSessionTemplate을 만들어야 한다. 
	
	public MemDAO() {
		System.out.println("MemDAO가 탄생했드아앙아아아아아아!!!!");
	}
	
	//회원목록
	public MemVO[] getAll() {
		MemVO[] ar = null;
		
		List<MemVO> list = sst.selectList("mem.all");
		if(list != null && list.size() > 0) {
			ar = new MemVO[list.size()]; 
			//현재 배열은 MemVO가 생성된 것이 아니라
			//MemVO를 저장할 수 있는 공간이 마련된 것이다. 그 크기가 list의 크기와 같다.
			
			//list에 있는 각 요소들을 배열인 ar에 복사한다. 
			list.toArray(ar);
		}
		//현재 sst는 지역변수가 아니라 멤버변수이기 때문에 여기서 close()해버리면
		//다른 곳에서 DAO를 접근해서 getAll() 메서드를 활용할 수 없게 된다.
		//이 DAO는 딱 하나만 만들어서 여러 곳에서 활용한다. 마치 Singleton처럼.
		//Controller에서 DAO를 멤버변수로 받아서 활용(using @Autowired)
		//그렇게 하기 위해서는 DAO가 먼저 만들어져 있어야 한다.
		//즉 이 DAO는 root-context.xml에 있어야 한다.
		return ar;
	}
	
	//로그인을 수행하는 기능 <- MemberController에서 호출
	public MemVO login(String id,String pw) {
		Map<String, String> map = new Hashtable<String, String>();
		map.put("m_id", id);
		map.put("m_pw", pw);
		
		//MemVO mvo=sst.selectOne("mem.login", map);
		//return mvo;
		return sst.selectOne("mem.login", map);
	}
}
