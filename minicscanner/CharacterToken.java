/**
 * TokenType 클래스를 상속받아, value로 문자열이 들어가는 token을 저장하는 클래스
 * @author Jeongminhyeok
 */

package minicscanner;

public class CharacterToken extends TokenType implements Common {
	private String value;

	/**
	 * 값을 설정하는 생성자. ID의 값이 Identifier 최대 길이보다 길면 LexcialException을 발생시킨다.
	 * @param num - token number
	 * @param id - token id
	 * @param val - token value
	 */
	public CharacterToken(int num, String id, String val) {
		super(num, id);
		if (val.length() > ID_LENGTH) {
			throw new IllegalArgumentException(
					"Token ID's length must be eqaul or shorter than "
							+ ID_LENGTH);
		}
		this.value = val;
	}

	/**
	 * token의 value값을 반환하는 메소드
	 * @return - token의 value값
	 */
	@Override
	public String getValue() {
		return value;
	}
}
