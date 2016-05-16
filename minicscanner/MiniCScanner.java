/**
 * Mini C Scanner 메인함수가 포함되있는 클래스
 * @author Jeongminhyeok
 */

package minicscanner;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MiniCScanner {
	private ArrayList<TokenType> tokenList = null;
	private TokenScanner tokenScanner;

	/**
	 * 파일이름을 매개변수로 받아, 해당 파일을 스캐너에 넣어서 반환된 토큰들을 출력한다.
	 * @param filename - mini C 소스파일 이름
	 * @param os - Token들의 정보를 출력할 출력스트림
	 */
	public MiniCScanner(String filename, OutputStream os) {
		tokenScanner = new TokenScanner(new File(filename));
		try {
			tokenList = tokenScanner.scanToken();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LexicalException e) { // 소스파일 내의 Lexical Error
			System.out.println(e.getMessage());
		} finally {
			tokenScanner.close();
		}
		for (int i = 0; i < tokenList.size(); ++i) {
			TokenType token = tokenList.get(i);
			try {
				os.write(String.format("Token ---> %-12s: (%d, %s)\n",
						token.getId(), token.getNumber(), token.getValue())
						.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 메인 메소드. 표준 입력으로부터 파일이름을 입력받아, 생성자를 호출한다.
	 * @param args
	 */
	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);
		String filename;
		// 인자값으로 들어온 것이 없으면 표준출력으로부터 Mini C 파일 이름을 받는다.
		if (args.length == 0) {
			System.out.print("Enter the mini C file name >> ");
			filename = sc.nextLine();
		} else {
			filename = args[0];
		}
		// 콘솔에 출력하기 위해 출력 스트림 인자는 System.out으로 준다.
		MiniCScanner miniCScanner = new MiniCScanner(filename, System.out);
		sc.close();
	}
}
