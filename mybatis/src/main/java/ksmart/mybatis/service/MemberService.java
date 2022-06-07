package ksmart.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ksmart.mybatis.dto.Member;
import ksmart.mybatis.dto.MemberLevel;
import ksmart.mybatis.mapper.MemberMapper;

@Service
@Transactional
public class MemberService {
	//DI(의존성주입)
	//1. 필드주입방식
	/*
	 *	@Autowired
	 *	//private MemberMapper memberMapper;
	 * */
	//2. setter 메소드 주입방식
	/*
	 * @Autowired private MemberMapper memberMapper;
	 * 
	 * public void setMemberMapper(MemberMapper memberMapper) { this.memberMapper =
	 * memberMapper; }
	 */
	
	//3. 생성자 메소드 주입방식
	/*
	 * 생성자의 호출 시점에 1회 호출되는 것이 보장
	 * 주입받은 객체가 변하지 않거나, 반드시 객체의 주입이 필요한 경우 강제하기 위해 
	 * */
	
	private final MemberMapper memberMapper;
	
	public MemberService(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}
	
	
	public List<Member> getSearchMemberList(String searchKey, String searchValue){
		List<Member> searchMemberList = memberMapper.getSearchMemberList(searchKey, searchValue);
		return searchMemberList;
				
	}
	
	/*로그인 이력 조회*/
	public Map<String, Object> getLoginHistory(int currentPage){
		//몇개 보여 줄 건지
		int rowPerPage = 5;
		int startPageNum = 1;
		int endPageNum = 10;
		//총 행의 갯수
		double rowCount = memberMapper.getLoginHistoryCount();
		//마지막페이지
		int lastPage = (int) Math.ceil(rowCount/rowPerPage);
		//페이징 처리
		int startRow = (currentPage-1) * rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startRow", startRow);
		paramMap.put("rowPerPage",rowPerPage);
		
		List<Map<String, Object>> loginHistoryList = memberMapper.getLoginHistory(paramMap);
		
		//동적 페이지번호
		if(currentPage > 6) {
			startPageNum = currentPage - 5;
			endPageNum = currentPage + 4;
			
			if(endPageNum >= lastPage) {
				startPageNum = lastPage - 9;
				endPageNum = lastPage;
			}
		}
		
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("lastPage", lastPage);
		resultMap.put("loginHistoryList",loginHistoryList);
		resultMap.put("startPageNum", startPageNum);
		resultMap.put("endPageNum", endPageNum);
		
		return resultMap;
	}
	
	
	
	/* 판매자 상품 목록 조회*/
	public List<Member> getSellerInfoList(){
		List<Member> sellerInfoList = memberMapper.getSellerInfoList();
		return sellerInfoList;
		
	}
	
	/*회원 전체 정보 조회*/
	
	public List<Member> getMemberInfoList(){
		List<Member> memberInfoList = memberMapper.getMemberInfoList();
		return memberInfoList;
	}
	
	
	/*회원탈퇴*/
	
	public boolean removeMember(String memberId, String memberPw) {
		
		boolean memberCheck = false;
		
		Member member = memberMapper.getMemberInfoById(memberId);
		if(member != null) {
			String memberPwCheck = member.getMemberPw();
			String memberLevel = member.getMemberLevel();
			if(memberPw.equals(memberPwCheck)) {
				memberCheck = true;
				//삭제로직
				/*
				 * //memberLevel = null 허용 if(memberLevel != null) {
				 * }
				 */
				//등급별로 삭제
				//판매자의 경우
				if("2".equals(memberLevel)) {
					//1.tb_order(상품코드에 연관된 튜플 삭제)
					memberMapper.removeOrderByGCode(memberId);
					
					//2. tb_goods (판매자가 등록한 상품 목록 삭제)
					memberMapper.removeGoodsById(memberId);
				
				//구매자	
				}else if("3".equals(memberLevel)) {
					//1. tb_order(구매자가 구매한 주문내역 삭제)
					memberMapper.removeOrderById(memberId);
				//일반회원
				}
					//1. tb_login(회원이 로그인한 이력 삭제)
					memberMapper.removeLoginHistoryById(memberId);
					//2. tb_member (회원탈퇴)
					memberMapper.removeMemberById(memberId);
			}
			
		}
		return memberCheck;
	}
	
	
	/*회원 수정*/
	
	public int modifyMember(Member member) {
		int result = memberMapper.modifyMember(member);
		return result;
	}
	
	
	/*회원상세정보*/
	
	public Member getMemberInfoById(String memberId) {
		Member member = memberMapper.getMemberInfoById(memberId);
				
			return member;	
	}
	
	/*
	 * 회원가입
	 * */
	
	public int addMember(Member member) {
		int result = memberMapper.addMember(member);
		
		return result;
	}

	
	/*회원등급목록조회*/
	
	public List<MemberLevel> getMemberLevelList(){
		List<MemberLevel> memberLevelList = memberMapper.getMemberLevelList();
		
		return memberLevelList;
	}
	
	
	
	/* 
	 * 	회원목록조회
	 * @return 회원 목록(List<Member>)
	 * 
	 * */
	
	public List<Member> getMemberList(){
		List<Member> memberList = memberMapper.getMemberList();
		
		/*
		 * 관리자 : 1, 판매자 : 2, 구매자 : 3
		 * */
		if(memberList != null) {
			
			//향상된 for문
			for(Member member : memberList) {
				String memberLevel = member.getMemberLevel();
				if(memberLevel != null){
					if("1".equals(memberLevel)) {
						member.setMemberLevel("관리자");
					}else if("2".equals(memberLevel)) {
						member.setMemberLevel("판매자");
						
					}else if("3".equals(memberLevel)) {
						member.setMemberLevel("구매자");
						
					}else {
						member.setMemberLevel("일반회원");
					}
					
				}
			}
			
		}
		
		return memberList;
	}
	
}
