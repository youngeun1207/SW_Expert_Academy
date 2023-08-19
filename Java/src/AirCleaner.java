import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 17144 미세먼지 안녕!
 * 삼성 SW 역량 테스트 기출
 * @author youngeun
 *
 * 36284 KB
 * 304 ms
 *
 * <단순구현>
 * 확산 -> 공기청정기 위치 기준으로 시계, 반시계 돌리는 것 유의
 */
public class AirCleaner {
    static int R, C, T;
    static int[][] field;
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int fresher;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st =  new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());
        field = new int[R][C];

        for (int r = 0; r < R; r++) {
            st =  new StringTokenizer(br.readLine());
            for (int c = 0; c < C; c++) {
                field[r][c] = Integer.parseInt(st.nextToken());
                if (field[r][c] == -1) { // 공기청정기 위치 파악
                    fresher = r;
                }
            }
        }
        for (int t = 0; t < T; t++) {
            diffusion();
            turn();
        }
        printSum();
    }

    /**
     * 먼지 확산하는 메소드
     */
    static void diffusion() {
        int[][] diffused = new int[R][C]; // 확산된 먼지 상태 저장할 2차원 배열

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                int cnt = 0;
                int prev = field[r][c]; // 현재 먼지 상태 저장 -> 확산 후 남은 먼지 계산 위함
                if (prev <= 0) {
                    continue;
                }

                int toDiffuse = prev / 5;

                for (int[] dir : dirs) {
                    // 확산 될 위치
                    int newR = r + dir[0];
                    int newC = c + dir[1];

                    if (newR >= 0 && newR < R && newC >= 0 && newC < C && field[newR][newC] >= 0) {
                        cnt++; // 확산된 칸 수
                        diffused[newR][newC] += toDiffuse;
                    }
                }
                diffused[r][c] += prev - (cnt * toDiffuse);
            }
        }

        field = diffused;
    }

    /**
     * 공기청정기 기준으로 시계, 반시계 방향 회전
     */
    static void turn() {
        turnClock();
        turnReverse();

        field[fresher-1][0] = -1;
        field[fresher][0] = -1;
    }

    /**
     * 맵 시계방향으로 돌리는 메소드
     */
    static void turnClock() { // 데이터 손실 없도록 왼 -> 위 -> 오 -> 아래 순으로 이동
        int[][] fieldUp = new int[fresher][C]; // 공기 순환된 상태 저장할 배열
        int height = fieldUp.length - 1;
        int width = C - 1;

        for (int i = 0; i < fresher; i++) { // 돌아가지 않는 부분(중앙부)도 없어지면 안되므로 복사해두기
            System.arraycopy(field[i], 0, fieldUp[i], 0, C);
        }
        // 맨 왼쪽 열
        int prev = fieldUp[0][0];
        for (int i = 1; i <= height; i++) {
            int tmp = fieldUp[i][0];
            fieldUp[i][0] = prev;
            prev = tmp;
        }
        // 맨 윗 줄
        prev = fieldUp[0][width];
        for (int i = 1; i <= width; i++) {
            int tmp = fieldUp[0][width - i];
            fieldUp[0][width - i] = prev;
            prev = tmp;
        }
        // 맨 오른쪽 열
        prev = fieldUp[height][width];
        for (int i = 1; i <= height; i++) {
            int tmp = fieldUp[height - i][width];
            fieldUp[height - i][width] = prev;
            prev = tmp;
        }
        // 맨 밑 줄
        prev = 0;
        for (int i = 1; i <= width; i++) {
            int tmp = fieldUp[height][i];
            fieldUp[height][i] = prev;
            prev = tmp;
        }

        for (int i = 0; i < fresher; i++) {
            System.arraycopy(fieldUp[i], 0, field[i], 0, C);
        }
    }

    /**
     * 맵 시계 반대방향으로 돌리는 메소드
     */
    static void turnReverse() { // 데이터 손실 없도록 왼 -> 아래 -> 오 -> 위 순으로 이동
        int[][] fieldDown = new int[R - fresher][C];
        int height = fieldDown.length - 1;
        int width = C - 1;

        for (int i = fresher; i < R; i++) {
            System.arraycopy(field[i], 0, fieldDown[i - fresher], 0, C);
        }

        // 맨 왼쪽 열
        int prev = fieldDown[height][0];
        for (int i = 1; i <= height; i++) {
            int tmp = fieldDown[height - i][0];
            fieldDown[height - i][0] = prev;
            prev = tmp;
        }
        // 맨 밑 줄
        prev = fieldDown[height][width];
        for (int i = 1; i <= width; i++) {
            int tmp = fieldDown[height][width - i];
            fieldDown[height][width - i] = prev;
            prev = tmp;
        }
        // 맨 오른쪽 열
        prev = fieldDown[0][width];
        for (int i = 1; i <= height; i++) {
            int tmp = fieldDown[i][width];
            fieldDown[i][width] = prev;
            prev = tmp;
        }
        // 맨 윗 줄
        prev = 0;
        for (int i = 1; i <= width; i++) {
            int tmp = fieldDown[0][i];
            fieldDown[0][i] = prev;
            prev = tmp;
        }
        for (int i = fresher; i < R; i++) {
            System.arraycopy(fieldDown[i - fresher], 0, field[i], 0, C);
        }
    }

    /**
     * 먼지 갯수 세는 메소드
     */
    static void printSum() {
        int sum = 0;
        for (int[] row : field) {
            for (int value : row) {
                sum += value;
            }
        }
        System.out.println(sum + 2); // 냉장고 위치 표시하는 -1 2개 고려
    }
}
