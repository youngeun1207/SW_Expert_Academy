import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 백준 1992 쿼드트리
 *
 * @author youngeun
 *
 * 13548 KB
 * 88 ms
 *
 * <분할정복>
 * 0 혹은 1로만 이루어진 정사각형의 영역인지 판별
 *  - 구간 내의 값 모두 더한 후 길이^2 or 0인 경우는 1 || 0 출력
 *  - 아니라면 4분면으로 나누어서 재귀적으로 탐색
 */
public class QuadTree {
    static int N;
    static int[][] image;
    static StringBuilder sb;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();
        N = Integer.parseInt(br.readLine());
        // 입력 받기
        image = new int[N][N];
        for(int i = 0; i < N; i++){
            String input = br.readLine();
            for(int j = 0; j < N; j++) {
                image[i][j] = input.charAt(j) - '0'; // 계산 편하도록 정수로 저장
            }
        }
        recur(0, 0, N); // 재귀적으로 4분면 탐색
        System.out.println(sb.toString());
    }

    static void recur(int row, int col, int size){
        int sum = 0;
        for(int r = row; r < row + size; r++){ // 구간 내부에 있는 숫자 더하기
            for(int c = col; c < col + size; c++){
                sum+=image[r][c];
            }
        }
        if(sum == size*size){ // 모두 1인 경우
            sb.append(1);
        } else if (sum == 0) { // 모두 0인 경우
            sb.append(0);
        } else { // 섞여있는 경우 -> 재귀 호출
            sb.append("(");
            // 4분면으로 나누기
            // 1 2
            // 3 4
            int half = size/2;
            recur(row, col, half); // 1
            recur(row, col+half, half); // 2
            recur(row+half, col, half); // 3
            recur(row+half, col+half, half); //4
            sb.append(")");
        }
    }

}
