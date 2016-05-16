/**
 * mini C 파일을 Token단위로 읽어내어 TokenType 클래스에 정보를 저장하고
 * 이를 ArrayList에 묶어 파일내의 모든 토큰을 순서대로 반환해주는 클래스
 * @author Jeongminhyeok
 */

package minicscanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class TokenScanner implements TSymbol {
	private BufferedReader bufferedReader;
	private ArrayList<TokenType> tokenList;

	/**
	 * 생성자. 매개변수로 온 파일에 대하여 Scan을 진행한다.
	 * @param file - Scan하고자 하는 파일
	 */
	public TokenScanner(File file) {
		this.tokenList = new ArrayList<TokenType>();
		try {
			bufferedReader = new BufferedReader(new FileReader(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * BufferedReader 스트림을 수동적으로 닫아주는 메소드
	 */
	public void close() {
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<TokenType> scanToken() throws IOException,
			LexicalException {
		int index, len, line = 0;
		String id;
		String s;

		// 파일의 끝까지 줄 단위로 읽는다.
		while ((s = bufferedReader.readLine()) != null) {
			++line;
			index = 0;
			s = s.trim();	// 맨 앞, 뒤 공백문자 제거
			
			// 탐색하고자하는 문자의 인덱스(index)가 문자열의 길이보다 길 경우
			// 반복문을 빠져나와 다음 줄을 읽는다.
			LoopPerToken: while (index < s.length()) {
				// 공백문자일 경우 다음 문자로 넘어간다
				while (Character.isSpaceChar(s.charAt(index)))
					++index;
				if (isSuperLetter(s.charAt(index))) {
					// 키워드에 해당 하는지 찾는다.
					for (int i = 0; i < KEYWORD.length; ++i)
						if (s.indexOf(KEYWORD[i]) == index) {
							s = s.replaceFirst(KEYWORD[i], "");
							tokenList.add(new IntegerToken(KEYNUM[i],
									KEYWORD[i], 0));
							continue LoopPerToken;
						}

					// 키워드가 아니라면, 식별자(Identifier)로 인식한다.
					id = new String();
					len = 0;
					// 영문, '_', 숫자가 아닐 때 까지 읽어온다. 
					do {
						id += s.charAt(index++);
						if (++len >= ID_LENGTH)
							throw new LexicalException(line,
									ERROR_IDENTIFIER_TOO_LONG);
					} while (isSuperLetter(s.charAt(index))
							|| Character.isDigit(s.charAt(index)));
					tokenList.add(new CharacterToken(TIDENT, id, id));
					continue LoopPerToken;
					// 숫자로 시작한다면 숫자로 인식한다.
				} else if (Character.isDigit(s.charAt(index))) {
					id = new String();
					do {
						id += s.charAt(index++);
					} while (Character.isDigit(s.charAt(index)));
					tokenList.add(new IntegerToken(TNUMBER, id, getIntNum(id)));
					continue LoopPerToken;
					// 숫자도 아닌 경우
				} else {
					if (s.charAt(index) == '/') {
						// '/*' 를 만났을 경우 '*/'가 나올 때 까지 읽는다. (여러줄 주석)
						if (s.charAt(index + 1) == '*') {
							index += 2;
							// '/*' 만 나오고 줄이 끝난경우 다음 줄을 읽는다.
							if (index >= s.length()) {
								s += bufferedReader.readLine();
							}
							do {
								while (s.charAt(index) != '*') {
									++index;
									// 한 줄을 다 읽었다면 다음 줄을 읽는다.
									if (index >= s.length()) {
										s += bufferedReader.readLine();
									}
								}
								++index;
							} while (s.charAt(index) != '/');
							++index;
							continue LoopPerToken;
							// 단일 주석
						} else if (s.charAt(index + 1) == '/') { 
							index = s.length();
							continue LoopPerToken;
						}
					}
					// 그 외의 경우 TSymbol 인터페이스에 있는 상수 정의에 따라
					// 아래의 Token들을 순차적으로 비교해 본다.
					// "/=", "/", "!=", "!", "%=", "%", "&&", "*=", "*", "++",
					// "+", "--", "-=", "-", "<=", "<", "==", "=", ">=", ">",
					// "||", "(", ")", ",", ";", "[", "]", "{", "}"
					for (int i = 0; i < TOKEN_WORD.length; ++i) {
						if (s.indexOf(TOKEN_WORD[i]) == index) {
							s = s.replaceFirst(Pattern.quote(TOKEN_WORD[i]), "");
							tokenList.add(new IntegerToken(TOKEN_NUM[i],
									TOKEN_WORD[i], 0));
							continue LoopPerToken;
						}
					}

					// 모든 Token을 조사하고도 일치하는게 없는 경우
					// 유효하지 않은(Invalid) Token이므로 Exception을 발생시킨다.
					// 아래 반복문의 경우 예측 가능한 Exception중 일치하는게 있는지 확인
					for (int i = 0; i < INVALID_TOKEN.length; ++i) {
						if (s.indexOf(INVALID_TOKEN[i]) == index) {
							throw new LexicalException(line,
									INVALID_EXCEPTION[i]);
						}
					}
					// 예측 가능한 Exception에 없으면, 기본 Exception을 발생
					throw new LexicalException(line, ERROR_INVALID_CHARACTER);
				}
			}
		}
		// 파일의 마지막까지 읽었으므로, TEOF를 추가해준다.
		// (Java엔 EOF가 없으므로)
		tokenList.add(new IntegerToken(TEOF, "", 0));
		return tokenList;
	}

	/**
	 * 매개변수가 Super Letter인지 확인 (identifier의 앞글자인지)
	 * @param ch - 확인하고자 하는 값
	 * @return - 매개변수로 온 값이 영문자 혹은 '_' 이면 true를 반환, 둘 다 아니면 false를 반환
	 */
	private boolean isSuperLetter(char ch) {
		if (Character.isLetter(ch) || ch == '_')
			return true;
		return false;
	}

	/**
	 * 문자열을 받아 적당한 정수로 변환해주는 메소드
	 * 16진수, 10진수, 8진수 변환이 가능하다.
	 * @param s - 변환하고자하는 문자열(16진법, 10진버, 8진법 표기법으로 작성되있어야한다)
	 * @return - 매개변수를 10진수 정수로 바꾼 값을 반환한다.
	 */
	private int getIntNum(String s) {
		if (s.charAt(0) != '0' || s.length() == 1) {
			return Integer.parseInt(s);
		} else {
			if ('0' <= s.charAt(1) && s.charAt(1) <= '7') {
				return Integer.parseInt(s, 8);
			} else if (s.charAt(1) == 'x' || s.charAt(1) == 'X') {
				return Integer.parseInt(s, 16);
			} else {
				return 0;
			}
		}
	}
}
