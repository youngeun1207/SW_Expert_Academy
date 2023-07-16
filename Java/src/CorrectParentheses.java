import java.util.Stack;

/**
 * 프로그래머스 Lv.2 올바른 괄호
 * <pre>
 * @author youngeun
 * Stack 사용 문제
 * '('면 스택에 넣기
 * ')'면 스택에서 짝지을 '(' 찾기
 * 스택 안에 짝지을 괄호가 없으면 False
 * </pre>
 */

class CorrectParentheses {
    boolean solution(String s) {

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char bracket = s.charAt(i);
            if (bracket == '(') { // '('면 stack에 넣기
                stack.push(bracket);
            } else {
                if (stack.size() > 0) { // ')'면 stack에서 짝지을 '(' 찾기
                    stack.pop();
                } else { // 만약 stack이 비어있으면 짝지을 수 없음
                    return (false);
                }
            }
        }

        if (stack.size() > 0) { // 문자열 다 돌았는데 stack에 '('가 남아있는 경우
            return (false); // stack 안에 있는 '('와 짝지어질 ')'가 없음
        }
        return true;
    }
}