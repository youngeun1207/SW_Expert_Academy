import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;
/**
 * 백준 1987 알파벳
 *
 * @author youngeun
 * 12548 kb
 * 916 ms
 *
 * <백트래킹>
 * DFS 돌면서 만약 방문했던 노드면 빠져나오기
 */
public class Alphabet {
    static int R, C;
    static char[][] map;
    static boolean[] visitedAlphabet;
    static int[][] dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int answer = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R][C];
        visitedAlphabet = new boolean[26]; // 방문한 알파벳 여부

        for(int r = 0; r < R; r++) {
            String str = br.readLine();
            for(int c = 0; c < C; c++) {
                map[r][c] = str.charAt(c);
            }
        }

        DFS(0, 0, 1); // 시작위치, 거리

        System.out.println(answer);
    }
    /**
     * 방문 가능여부
     * @param r
     * @param c
     * @return
     */
    static boolean isInRange(int r, int c) {
        // 범위 안벗어났으면서, 방문하지 않은 알파벳인 경우
        if(0<=r && r<R && 0<=c && c<C && !visitedAlphabet[map[r][c]-'A']) {
            return true;
        }
        return false;
    }
    /**
     * 백트래킹
     * @param x 현재 x좌표
     * @param y 현재 y좌표
     * @param dist 현재 이동거리
     */
    static void DFS(int x, int y, int dist) {
        answer = Math.max(dist, answer);
        // 방문한 알파벳
        visitedAlphabet[map[x][y]-'A'] = true;
        for(int dir = 0; dir < 4; dir++) { // 4방 이동
            int newR = x+dirs[dir][0];
            int newC = y+dirs[dir][1];
            if(isInRange(newR, newC)) {
                DFS(newR, newC, dist+1);
            }
        }
        // DFS 나오면서 방문체크 해제
        visitedAlphabet[map[x][y]-'A'] = false;
    }
}
