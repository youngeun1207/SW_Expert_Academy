import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 17135 캐슬 디펜스
 * @author youngeun
 *
 * 38,740 KB
 * 200 ms
 *
 * BFS
 * 궁수의 위치 조합으로 생성
 * 가장 가까운 병사 -> 왼쪽 병사 순이므로 왼쪽부터 시작하는 BFS 수행함
 */
public class CastleDefence {
    static int N, M, D, cnt=0, origin, p1, p2, p3;
    static int answer = 0;
    static int[][] originMap;
    static int[][] map;
    static boolean[][] visited;
    static List<int[]> killed;
    static int[][] dirs = new int[][]{{0, -1},{-1, 0}, {0, 1}}; // 좌, 상, 우

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        originMap = new int[N][M];
        map = new int[N+1][M];

        for(int n = 0; n < N; n++) {
            st = new StringTokenizer(br.readLine());
            for(int m = 0; m < M; m++) {
                originMap[n][m] = Integer.parseInt(st.nextToken()); // 원본 지도
                map[n][m] = originMap[n][m]; // 테케 수행용 지도
                origin+=map[n][m]; // 병사 수
            }
        }
        // 궁수 조합 구하기 3CM
        // 어차피 궁수 3명이니까.. 반복문 돌릴래
        for(p1 = 0; p1 < M-2; p1++) {
            for(p2 = p1+1; p2 < M-1; p2++) {
                for(p3 = p2+1; p3 < M; p3++) {
                    int tmp = 0;
                    cnt = origin; // 모든 병사가 다 없으면 종료이므로 병사 수 저장함
                    while(cnt > 0) {
                        killed = new ArrayList<>(); // 우선순위 병사
                        // 각 병사 별로 BFS 수행
                        BFS(p1);
                        BFS(p2);
                        BFS(p3);
                        // 동시에 화살 쏘므로 같은 적 맞출 가능성 있음
                        // 그러므로 매 턴이 종료된 후 병사 없애줘야함
                        for(int i = 0; i < killed.size(); i++) {
                            int node[] = killed.get(i);
                            if(map[node[0]][node[1]] == 1) { // 중복 병사가 아닌 경우
                                cnt--;
                                map[node[0]][node[1]] = 0;
                                tmp++;
                            }
                        }
                        moveDown(); // 한 칸 이동
                    }
                    answer = Math.max(tmp, answer);
                    copyMap(); // 지도 원복
                }
            }
        }

        System.out.printf("%d",answer);
    }
    /**
     * 최고 우선순위: 거리 가까움 -> 왼쪽 이므로 왼쪽 이동이 우선인 BFS 돌림
     * @param p 병사
     */
    static void BFS(int p) {
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {N, p, 0});
        visited = new boolean[N+1][M];
        visited[N][p] = true;

        while(!queue.isEmpty()) {
            int[] node = queue.poll();
            if(node[2] > D) {
                break;
            }
            if(map[node[0]][node[1]] == 1) {
                killed.add(new int[] {node[0], node[1]}); // 최고 우선순위 병사 집어넣기
                return;
            }
            for(int dir = 0; dir < 3; dir++) {
                int newR = node[0] + dirs[dir][0];
                int newC = node[1] + dirs[dir][1];

                if(isInRange(newR, newC)) {
                    queue.add(new int[] {newR, newC, node[2]+1});
                    visited[newR][newC] = true;
                }
            }
        }
    }
    /**
     * 맵 내부에 있는지
     * @param r
     * @param c
     * @return
     */
    static boolean isInRange(int r, int c) {
        return 0 <= r && r < N && 0 <= c && c < M && !visited[r][c];
    }
    /**
     * 지도 한 칸씩 밑으로 이동
     * 만약 병사가 성에 도달했므면 병사 수 줄이기
     */
    static void moveDown() {
        for(int c = 0; c < M; c++) {
            cnt -= map[N][c];
        }
        for(int r = N-1; r >= 0; r--) {
            map[r+1] = map[r];
        }
        map[0] = new int[M];
    }

    static void copyMap() {
        for(int i = 0; i < N; i++) {
            map[i] = Arrays.copyOf(originMap[i], M);
        }
    }
}

