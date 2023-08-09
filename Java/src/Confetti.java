import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 2563 색종이
 * @author youngeun
 *
 * 11564 KB
 * 88 ms
 *
 * 단순구현 문제
 * 색종이가 붙었는지 여부(boolean) 저장하는 paper 배열을 만듦
 * 색종이가 붙은 영역은 true로
 * 마지막에 true인 칸 갯수 셈
 */
public class Confetti {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        boolean[][] paper = new boolean[100][100];
        int N = Integer.parseInt(br.readLine());
        int left, bottom;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            left = Integer.parseInt(st.nextToken());
            bottom = Integer.parseInt(st.nextToken());
            for(int b = 90-bottom; b < 100-bottom; b++){
                for(int l = left; l < left+10; l++){
                    paper[b][l] = true;
                }
            }
        }
        int answer = 0;
        for(int i = 0; i < 100; i++){
            for (int j = 0; j < 100; j++){
                if(paper[i][j]){
                    answer++;
                }
            }
        }
        System.out.println(answer);
    }
}
