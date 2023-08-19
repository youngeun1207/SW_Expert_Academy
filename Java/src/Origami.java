import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 백준 20187 색종이 접기
 * @author youngeun
 *
 * 23304 KB
 * 152 ms
 *
 * 먼저 접었을 때 0 1 2 3 구멍 위치 바뀌지 않는 구간 저장
 * 	U -> 현재 위 ~ 높이 절반
 * 	D -> 높이 절반 ~ 현재 아래
 * 	R -> 너비 절반 ~ 현재 오
 *	L -> 현재 왼 ~ 너비 절반
 *
 * 이후 바뀌지 않는 위치부터 한줄씩 반전시켜가며 배열 완성
 */

public class Origami {
    static int[][] paper;
    static int K, H;
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        // 종이 접어서 구멍 뚫는 기준 사각형 찾기
        K = Integer.parseInt(br.readLine());
        int length = (int)Math.pow(2, K);
        paper = new int[length][length];
        int x_up = 0, y_left = 0;
        int x_down = length-1, y_right = length-1;

        int height = (length-1)/2, width = (length-1)/2;

        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < K*2; i++) {
            switch (st.nextToken()) {
                case "D": // 높이 절반 ~ 현재 아래
                    x_up = x_down - height;
                    height/=2;
                    break;
                case "U": // 현재 위 ~ 높이 절반
                    x_down = x_up + height;
                    height/=2;
                    break;
                case "R": // 너비 절반 ~ 현재 오
                    y_left = y_right - width;
                    width/=2;
                    break;
                case "L": // 현재 왼 ~ 너비 절반
                    y_right = y_left + width;
                    width/=2;
                    break;
            }
        }
        // 구멍 뚫기 -> 0123 구멍 위치 불변한 위치에 저장
        paper[x_up][y_left] = Integer.parseInt(br.readLine());

        // 재귀적으로 접은 것 풀기
        // 4칸만 먼저 만들기
        // 위로 반전하면서 올라가기
        for(int x = x_up-1; x >= 0; x--) {
            paper[x][y_left] = (paper[x+1][y_left]+2)%4;
        }
        // 아래로 반전하면서 내려가기
        for(int x = x_up+1; x < length; x++) {
            paper[x][y_left] = (paper[x-1][y_left]+2)%4;
        }
        // 왼쪽으로 반전하면서 이동
        int prev;
        for(int y = y_left-1; y >= 0; y--) {
            for(int col = 0; col < length; col++) {
                prev = paper[col][y+1];
                if(prev%2 == 0) {
                    paper[col][y] = prev+1;
                }else {
                    paper[col][y] = prev-1;
                }
            }
        }
        // 오른쪽으로 반전하면서 이동
        for(int y = y_left+1; y < length; y++) {
            for(int col = 0; col < length; col++) {
                prev = paper[col][y-1];
                if(prev%2 == 0) {
                    paper[col][y] = prev+1;
                }else {
                    paper[col][y] = prev-1;
                }
            }
        }
        // 출력
        for(int i = 0; i < length; i++) {
            for(int j = 0; j <length; j++) {
                sb.append(paper[i][j] + " ");
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
