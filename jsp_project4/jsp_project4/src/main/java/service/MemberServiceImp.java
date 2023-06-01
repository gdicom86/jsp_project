package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.MemberController;
import domain.MemberVO;
import repository.MemberDAO;
import repository.MemberDAOImpl;

public class MemberServiceImp implements MemberService {
	private static final Logger log = LoggerFactory.getLogger(MemberService.class);
	
	private MemberDAO mdao;
	
	public MemberServiceImp() {
		mdao = new MemberDAOImpl();
	}
		
		
	@Override
	public int register(MemberVO mvo) {
		log.info(">>> register service 진입");
		return mdao.insert(mvo);
	}


	@Override
	public MemberVO login(MemberVO mvo2) {
		log.info(">>> login service 진입");
		return mdao.selectOne(mvo2);
	}


	@Override
	public int lastLogin(String id2) {
		log.info(">>> logout service 진입");
		return mdao.lastLogin(id2);
	}


	@Override
	public int edit(MemberVO mvo3) {
		log.info(">>> edit service 진입");
		return mdao.edit(mvo3);
	}


	@Override
	public int delete(String id2) {
		log.info(">>> delete service 진입");
		return mdao.delete(id2);
	}


	@Override
	public List<MemberVO> list() {
		log.info(">>> list print service 진입");
		return mdao.selectList();
	}


	
	

}
