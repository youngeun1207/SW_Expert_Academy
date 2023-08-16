import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
/**
 * 백준 3109 빵집
 *
 * @author youngeun
 * 36,812 KB
 * 328 ms
 *
 * <그리디>
 * 왼쪽 열부터 오위 -> 오 -> 오 아래 순으로 DFS탐색하여 만약 끝까지 도달하면 answer++
 * 순서를 위에서부터 하므로 아래 열에서 위에서 한 탐색은 가지 않아도 됨 -> 그리디 풀이
 */
public class Bakery {
    static int answer = 0, R, C;
    static int[][] dirs = {{-1, 1}, {0, 1}, {1, 1}}; // 오른 위, 오른, 오른 아래
    static char[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];
        String input;
        for(int r = 0; r < R; r++) {
            input =  br.readLine();
            for(int c = 0; c < C; c++) {
                map[r][c] = input.charAt(c);
            }
        }

        for(int row = 0; row < R; row++) { // 행 별로 내려가면서 DFS 돌리기
            if(DFS(row, 0)) { // 해당 행에서 끝까지 도달할 수 있는 길이 있으면 정답++
                answer++;
            }
        }
        System.out.println(answer);
    }

    /**
     * 오른쪽으로 더이상 이동할 수 없을 때 까지 가기
     * @param row
     * @param col
     * @return
     */
    static boolean DFS(int row, int col) {
        map[row][col] = 'O'; // 도달함 체크

        if(col == C-1) { // 빵집 도착
            return true;
        }
        for(int i = 0; i < 3; i++) { // 오른 위 -> 오른 -> 오른 아래 순으로 (위에서 아래로) 탐색
            if(isInRange(row+dirs[i][0], col+dirs[i][1])) {
                if(DFS(row+dirs[i][0], col+dirs[i][1])) { // 만약 빵집까지 도달했으면 true 반환 || 아니면 다음 방향 도전
                    return true;
                }
            }
        }
        return false; // 3 방향 모두 막혀있으면 해당 경로 더이상 탐색하지 않음
    }

    /**
     * 다음 좌표로 이동할 수 있는지 여부 판별
     * @param r
     * @param c
     * @return
     */
    static boolean isInRange(int r, int c) {
        if(0 <= r && r < R && 0 <= c && c < C && map[r][c] == '.') {
            return true;
        }
        return false;
    }
}
