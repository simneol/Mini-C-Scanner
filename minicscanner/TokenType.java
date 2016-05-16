/**
 * token의 정보를 저장하는 기본 클래스
 * 유형에 따라 상속하여 이용한다.
 * @author Jeongminhyeok
 */
package minicscanner;

public class TokenType {
	private int number;
	private String id;
	
	/**
	 * 생성자
	 * @param num - Token number
	 * @param id - Token id
	 */
	public TokenType(int num, String id) {
		this.number = num;
		this.id = id;
	}

	public int getNumber(){
		return number;
	}
	
	public String getId(){
		return id;
	}
	
	/**
	 * override 해서 이용하는 메소드
	 * @return
	 */
	public String getValue(){
		return "";
	}
	
	
}
