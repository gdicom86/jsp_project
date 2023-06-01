package repository;


import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.MemberVO;
import orm.DatabaseBuilder;
import service.MemberService;

public class MemberDAOImpl implements MemberDAO {

	private static final Logger log = LoggerFactory.getLogger(MemberDAOImpl.class);
	private SqlSession sql;
	
	//NameSpace = SQL이 mapper를 구분하기 위한 이름
	//NameSpace.SQL구문이름
	private String NS = "MemberMapper.";
	
	public MemberDAOImpl() {
		new DatabaseBuilder(); //class객체 생성
		sql = DatabaseBuilder.getFactory().openSession(); //객체에서 펙토리를 가져와 sql연결
	}
	
	
	
	@Override
	public int insert(MemberVO mvo) {
		// 추가
		
		//구문은 어디서 적어요? = memberMapper에서 적어요!
		
		//sql.insert(NS+이름, 객체);
		//transaction처리가 자동으로 이루어짐
		int isOk = sql.insert(NS+"reg", mvo); 
		
		if(isOk > 0 ) { //결과가 올바르게 나왔다면
			sql.commit(); //실행
		} //아니면 실행x
		
		return isOk;
	}



	@Override
	public MemberVO selectOne(MemberVO mvo2) {
		log.info(">>> login DAO 진입");
		return sql.selectOne(NS + "login", mvo2);
	}
	
	@Override
	public int lastLogin(String id2) {
		log.info(">>> logout DAO 진입");
		int isOk = sql.update(NS + "logout", id2);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}



	@Override
	public int edit(MemberVO mvo3) {
		log.info(">>> edit DAO 진입");
		int isOk = sql.update(NS + "edit", mvo3);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}



	@Override
	public int delete(String id2) {
		log.info(">>> delete DAO 진입");
		int isOk = sql.delete(NS + "delete", id2);
		if(isOk > 0) {
			sql.commit();
		}
		return isOk;
	}



	@Override
	public List<MemberVO> selectList() {
		log.info(">>> list DAO 진입");
		return sql.selectList(NS+"list"); 
	}



}
