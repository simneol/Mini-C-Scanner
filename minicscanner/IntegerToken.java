/**
 * TokenType 클래스를 상속받아, value로 정수가 들어가는 token을 저장하는 클래스
 * @author Jeongminhyeok
 */

package minicscanner;

public class IntegerToken extends TokenType {
	private int value;
	
	/**
	 * 값을 설정하는 생성자. 해당 token은 identifier를 저장하지 않으므로,
	 * id값에 길이 제한이 없다.
	 * @param num - token number
	 * @param id - token id
	 * @param val - token value
	 */
	public IntegerToken(int num, String id, int val) {
		super(num, id);
		this.value = val;
	}
	
	/**
	 * token의 value값을 반환하는 메소드
	 * @return - token의 value값
	 */
	@Override
	public String getValue() {
		return String.valueOf(value);
	}
}
