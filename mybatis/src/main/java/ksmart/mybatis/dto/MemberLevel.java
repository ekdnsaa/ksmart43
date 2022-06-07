package ksmart.mybatis.dto;

public class MemberLevel {
	private String levelNum;
	private String levelName;
	private String levelRegDate;
	public String getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(String levelNum) {
		this.levelNum = levelNum;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelRegDate() {
		return levelRegDate;
	}
	public void setLevelRegDate(String levelRegDate) {
		this.levelRegDate = levelRegDate;
	}
	@Override
	public String toString() {
		return "MemberLevel [levelNum=" + levelNum + ", levelName=" + levelName + ", levelRegDate=" + levelRegDate
				+ "]";
	}
	
	
	
}
