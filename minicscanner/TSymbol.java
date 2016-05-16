/**
 * 기본 적인 Token의 유형과 관련된 상수들을 정의해놓은 인터페이스
 * @author Jeongminhyeok
 */
package minicscanner;


public interface TSymbol extends Common{
	public static final int TNULL = -1;
	public static final int TNOT = 0;
	public static final int TNOTEQU = 1;
	public static final int TMOD = 2;
	public static final int TMODASSIGN = 3;
	public static final int TIDENT = 4;
	public static final int TNUMBER = 5;
	public static final int TAND = 6;
	public static final int TLPAREN = 7;
	public static final int TRPAREN = 8;
	public static final int TMUL = 9;
	public static final int TMULASSIGN = 10;
	public static final int TPLUS = 11;
	public static final int TINC = 12;
	public static final int TADDASSIGN = 13;
	public static final int TCOMMA = 14;
	public static final int TMINUS = 15;
	public static final int TDEC = 16;
	public static final int TSUBASSIGN = 17;
	public static final int TDIV = 18;
	public static final int TDIVASSIGN = 19;
	public static final int TSEMICOLON = 20;
	public static final int TLESS = 21;
	public static final int TLESSE = 22;
	public static final int TASSIGN = 23;
	public static final int TEQUAL = 24;
	public static final int TGREAT = 25;
	public static final int TGREATE = 26;
	public static final int TLBRACKET = 27;
	public static final int TRBRACKET = 28;
	public static final int TEOF = 29;
	public static final int TCONST = 30;
	public static final int TELSE = 31;
	public static final int TIF = 32;
	public static final int TINT = 33;
	public static final int TRETURN = 34;
	public static final int TVOID = 35;
	public static final int TWHILE = 36;
	public static final int TLBRACE = 37;
	public static final int TOR = 38;
	public static final int TRBRACE=39;
	
	public static final String[] KEYWORD = {"const", "else", "if", "int", "return", "void", "while"};
	public static final int[] KEYNUM = {TCONST, TELSE, TIF, TINT, TRETURN, TVOID, TWHILE};
	
	public static final String[] TOKEN_WORD = {
		"/=", "/", "!=", "!", "%=", "%", "&&", "*=", "*", "++", "+=", "+", "--", "-=", "-",
		"<=", "<", "==", "=", ">=", ">", "||", "(", ")", ",", ";", "[", "]", "{", "}"};
	public static final int[] TOKEN_NUM = {
		TDIVASSIGN, TDIV, TNOTEQU, TNOT,TMODASSIGN, TMOD, TAND, TMULASSIGN, TMUL, TINC, TADDASSIGN, TPLUS, TDEC, TSUBASSIGN, TMINUS,
		TLESSE, TLESS, TEQUAL, TASSIGN, TGREATE, TGREAT, TOR, TLPAREN, TRPAREN, TCOMMA, TSEMICOLON, TLBRACKET, TRBRACKET, TLBRACE, TRBRACE};
	
	public static final String[] INVALID_TOKEN = { "&", "|" };
	public static final int[] INVALID_EXCEPTION = { ERROR_SINGLE_AND_CHARACTER, ERROR_SINGLE_OR_CHARACTER};
}
