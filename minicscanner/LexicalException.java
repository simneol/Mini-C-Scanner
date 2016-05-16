/**
 * LexicalException을 사용자 정의 예외로 생성해놓은 클래스
 * @author Jeongminhyeok
 */
package minicscanner;

public class LexicalException extends Exception implements Common {
	private int line;
	private int n;

	/**
	 * 생성자
	 * @param line - 소스파일의 몇 번째 줄에서 발생했는지에 대한 변수
	 * @param n - 어떤 유형의 Lexical Exception이 발생했는지에 대한 변수
	 */
	public LexicalException(int line, int n) {
		this.line = line;
		this.n = n;
	}

	/**
	 * getMessage() 함수를 오버라이딩 한것.
	 * 멤버 변수 n(타입)에 따라 다른 메시지를 반환한다.
	 */
	@Override
	public String getMessage() {
		String msg;
		msg = "Invalid Syntax in line " + line + " : ";
		switch (n) {
		case ERROR_IDENTIFIER_TOO_LONG:
			msg += "An identifier length must be less than 12\n";
			break;
		case ERROR_SINGLE_AND_CHARACTER:
			msg += "It must be && not &\n";
			break;
		case ERROR_SINGLE_OR_CHARACTER:
			msg += "It must be || not |\n";
			break;
		case ERROR_INVALID_CHARACTER:
			msg += "invalid character !\n";
			break;
		}
		return msg;
	}
}
