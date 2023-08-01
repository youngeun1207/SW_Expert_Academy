import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * SWEA D4 1210
 * [S/W 문제해결 기본] 2일차 - Ladder1
 * @author youngeun
 *
 * 23,048 kb
 * 137 ms
 *
 * 밑에서부터 올라오는 단순구현 문제
 */
public class Ladder {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for(int i = 0; i < 10; i++) {
            int tc = Integer.parseInt(br.readLine());
            String[] ladders = new String[100];
            int[][] dirs = {{-1, 0}, {0, -1}, {0, 1}}; // 위, 왼, 오른
            int dir = 0; // 초기값 -> 상행

            for(int j = 0; j < 100; j++){
                ladders[j] = br.readLine();
            }
            // 시작점(2) 찾기
            int col = 0;

            for(int c = 0; c < 100; c++){
                if(ladders[99].charAt(c*2) == '2'){
                    col = c;
                    break;
                }
            }
            int row = 99;
            // 아래에서부터 올라가기
            while (row != 0){
                // 현재 방향대로 1칸 이동
                row += dirs[dir][0];
                col += dirs[dir][1];

                // 위로 올라가는 경우
                if(dir == 0){
                    // 왼쪽 길 있는지
                    if(0 <= (col-1) && ladders[row].charAt((col-1)*2) == '1'){
                        dir = 1;
                    }
                    // 오른쪽 길 있는지
                    else if(col+1 < 100 && ladders[row].charAt((col+1)*2) == '1') {
                        dir = 2;
                    }
                }
                // 왼쪽이나 오른쪽
                else{
                    // 상행 있는지
                    if(ladders[row-1].charAt(col*2) == '1'){
                        dir = 1;
                    }
                }
            }
            System.out.printf("#%d %d\n", tc, col);
        }
    }
}
