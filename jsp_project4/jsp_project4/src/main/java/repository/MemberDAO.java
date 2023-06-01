package repository;

import java.util.List;

import domain.MemberVO;

public interface MemberDAO {

	int insert(MemberVO mvo);

	MemberVO selectOne(MemberVO mvo2);

	int lastLogin(String id2);

	int edit(MemberVO mvo3);

	int delete(String id2);

	List<MemberVO> selectList();



}
