package kr.or.connect.reservation.dao;

import static kr.or.connect.reservation.dao.MemberDaoSqls.SELECT_MEMBER_BY_USERID;
import static kr.or.connect.reservation.dao.MemberDaoSqls.SELECT_MEMBER_BY_USERNO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.reservation.dto.Member;
import kr.or.connect.reservation.dto.MemberAuth;
import kr.or.connect.reservation.security.encoder.CustomNoOpPasswordEncoder;

@Repository
public class MemberDao {
	
final static Integer CANCLE_FLAG = 1; //취소상태를 표현하기위한 전역변수
	
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAuthoritiesAction;
	private SimpleJdbcInsert insertMemberAction;
	Calendar calendar = Calendar.getInstance();
	
	private RowMapper<Member> member_rowMapper = BeanPropertyRowMapper.newInstance(Member.class);
	
	public void setDataSource(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public MemberDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAuthoritiesAction = new SimpleJdbcInsert(dataSource)
				.withTableName("authorities");
        this.insertMemberAction = new SimpleJdbcInsert(dataSource)
				.withTableName("member")
				.usingGeneratedKeyColumns("user_no");
    }
	
	public void createAuth(MemberAuth memberAuth) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("user_no", memberAuth.getUserNo());
		parameters.put("user_id", memberAuth.getUserId());
		parameters.put("authority", memberAuth.getAuthority());
		insertAuthoritiesAction.execute(parameters);
	}
	
	public void create(Member member) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("user_no", member.getUserId());
		parameters.put("user_id", member.getUserId());
		parameters.put("user_pw", member.getUserPw());
		parameters.put("user_name", member.getUserName());
		parameters.put("job", member.getJob());
		
		parameters.put("coin", 1);
		java.util.Date currentTime = calendar.getTime();
		parameters.put("reg_date", currentTime);
		parameters.put("upd_date", currentTime);
		parameters.put("enabled", 1);
		
		parameters.put("email", member.getEmail());
		parameters.put("zipCode", member.getZipcode());
		parameters.put("address01", member.getAddress01());
		parameters.put("address02", member.getAddress02());
		insertMemberAction.execute(parameters);
	}
	
	//사용자 아이디로 회원정보 조회
	public Member readByUserId(String userId) {
		try {
			Map<String,String> params = Collections.singletonMap("userId", userId);
			List<Member> list = new ArrayList<>();
			Member member = new Member();
			list = jdbc.query(SELECT_MEMBER_BY_USERID, params, member_rowMapper);
			if(list.size()>1) {
				member=list.get(0);
				List<MemberAuth> authList = new ArrayList<>();
				for(int i=0;i<list.size();i++) {
					MemberAuth oneAuth = new MemberAuth(list.get(i).getUserNo(),list.get(i).getUserId(),list.get(i).getAuthority());
					System.out.println("readbyid size>1"+oneAuth.toString());
					authList.add(oneAuth);
				}
				member.setAuthList(authList);
				return member;
			}else {
				member=list.get(0);
				MemberAuth oneAuth = new MemberAuth(member.getUserNo(),member.getUserId(),member.getAuthority());
				System.out.println("readbyid sizeelse"+oneAuth.toString());
				List<MemberAuth> authList = new ArrayList<>();
				authList.add(oneAuth);
				member.setAuthList(authList);
				return member;
			}
			
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	};
	
	public Member read(int userNo) {
		try {
			Map<String,Integer> params = Collections.singletonMap("userNo", userNo);
			List<Member> list = new ArrayList<>();
			Member member = new Member();
			list = jdbc.query(SELECT_MEMBER_BY_USERNO, params, member_rowMapper);
			if(list.size()>1) {
				member=list.get(0);
				List<MemberAuth> authList = new ArrayList<>();
				for(int i=0;i<list.size();i++) {
					MemberAuth oneAuth = new MemberAuth(list.get(i).getUserNo(),list.get(i).getUserId(),list.get(i).getAuthority());
					System.out.println("size>1"+oneAuth.toString());
					authList.add(oneAuth);
				}
				member.setAuthList(authList);
				System.out.println("size>1"+member.toString());
				return member;
			}else {
				member=list.get(0);
				MemberAuth oneAuth = new MemberAuth(member.getUserNo(),member.getUserId(),member.getAuthority());
				System.out.println("size else"+oneAuth.toString());
				List<MemberAuth> authList = new ArrayList<>();
				authList.add(oneAuth);
				member.setAuthList(authList);
				System.out.println("size else"+member.toString());
				return member;
			}
		}catch (EmptyResultDataAccessException e){
			e.printStackTrace();
			return null;
		}
	};
	
	// 아이디 검사
	public boolean isMemberId(String id) throws SQLException {
		try {
			Map<String, String> params = Collections.singletonMap("id", id);
			List<Member> memberList = jdbc.query("SELECT * FROM member WHERE user_id = :id", params, member_rowMapper);//params 에는 sql문의 where절에서 =:"displayInfoId" 해당 구문이 받아먹을 displayInfoId가 밸류값으로 존재함
			
			if(memberList.size()<=0) {
				return false;
			}else {
				return true;
			}
		}catch (EmptyResultDataAccessException e){ //예외처리
			e.printStackTrace();
			return false;
		}
	}

	public boolean isMemberEmail(String email) {
		try {
			Map<String, String> params = Collections.singletonMap("email", email);
			List<Member> memberList = jdbc.query("SELECT * FROM member WHERE email = :email", params, member_rowMapper);//params 에는 sql문의 where절에서 =:"displayInfoId" 해당 구문이 받아먹을 displayInfoId가 밸류값으로 존재함
			
			if(memberList.size()<=0) {
				return false;
			}else {
				return true;
			}
		}catch (EmptyResultDataAccessException e){ //예외처리
			e.printStackTrace();
			return false;
		}
	}
	
}
