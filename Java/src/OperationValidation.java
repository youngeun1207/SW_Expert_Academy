import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * SWEA 1233 [S/W 문제해결 기본] 9일차 - 사칙연산 유효성 검사
 * @author youngeun
 *
 * 20,544 kb
 * 114 ms
 *
 * 1. 리프노드 -> 반드시 숫자여야 함
 * 2. 그 외 노드
 *  a. 연산자여야 함
 *  b. 오른쪽, 왼쪽 서브트리 모두 존재하며, 유효해야 함
 *
 * 조건 충족 여부 트리 순회를 위해 DFS 사용
 * 재귀 대신 stack으로 구현하였다.
 */
public class OperationValidation {
    static Stack<Integer> stack = new Stack<>();
    static char[] tree;
    static int N;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        for(int tc = 1; tc <= 10; tc++){
            N = Integer.parseInt(br.readLine());
            tree = new char[N+1]; // index 0 제외

            int idx;
            char oper;
            for (int i = 1; i <= N; i++) {
                st = new StringTokenizer(br.readLine());
                idx = Integer.parseInt(st.nextToken());
                oper = st.nextToken().charAt(0);
                tree[idx] = oper;// 연산자와 숫자 모두 담아야하므로 char로 변환
            }

            // DFS 돌면서 유효성 검증
            // 인덱스로 접근할거임 -> Integer형
            stack.clear();
            stack.push(1); // root node 넣기
            int node;
            int isValidate = 1;
            while (!stack.empty()){
                node = stack.pop();
                if(node > N/2){ // 리프노드
                    if(tree[node] < '0' || tree[node] > '9') { // 리프 노드는 숫자여야 함
                        isValidate = 0;
                        break;
                    }
                }else {
                    if(tree[node] >= '0' && tree[node] <= '9'){ // 리프가 아닌 노드는 연산자여야 함
                        isValidate = 0;
                        break;
                    } else if (node*2+1 > N) { // 오른쪽 자식이 없는 경우
                        isValidate = 0;
                        break;
                    }
                    stack.push(node*2);
                    stack.push(node*2 + 1);
                }
            }
            System.out.println("#" + tc + " " + isValidate);
        }
        
    }
}
