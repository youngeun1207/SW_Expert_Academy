import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백준 14502 연구소
 * @author youngeun
 *
 * 78188 KB
 * 316 ms
 *
 * <Combination + BFS>
 * 1. 벽을 3개 세워야 함 -> N*M 중 3개 뽑기
 * 2. 뽑은 숫자를 x라 하면..
 *  - row = x/M
 *  - col = c%M
 *  - 세 지점 모두 map[row][col] = 0인 경우만 BFS 수행
 * 3. BFS로 바이러스 전염시키기
 *  - tmp에 바이러스 전염 전의 안전 공간 개수 저장
 *  - 만약 전염 가능한 칸을 만나면 tmp--
 */
public class Lab {
    static int N, M, origin, tmp, answer = Integer.MIN_VALUE;
    static int[][] map, dirs = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static boolean[][] visited;
    static ArrayList<int[]> virus;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        origin = 0;
        virus = new ArrayList<>(); // 바이러스 위치 좌표 저장
        for(int n = 0; n < N; n++){
            st = new StringTokenizer(br.readLine());
            for(int m = 0; m < M; m++){
                map[n][m] = Integer.parseInt(st.nextToken());
                if(map[n][m] == 2){
                    virus.add(new int[] {n, m});
                } else if (map[n][m] == 0) {
                    origin++; // 바이러스 번지기 전 안전구역 초기값
                }
            }
        }

        // 벽 3개 고정이니까 3중 for문 돌려서 combination 만들기
        for(int wall1 = 0; wall1 < N*M-2; wall1++){
            if(map[wall1/M][wall1%M] != 0) continue;
            map[wall1/M][wall1%M] = 1; // 벽1 세우기
            for(int wall2 = wall1; wall2 < N*M-1; wall2++){
                if(map[wall2/M][wall2%M] != 0) continue;
                map[wall2/M][wall2%M] = 1; // 벽2 세우기
                for(int wall3 = wall2; wall3 < N*M; wall3++){
                    if(map[wall3/M][wall3%M] != 0) continue;
                    map[wall3/M][wall3%M] = 1; // 벽3 세우기

                    tmp = origin-3; // 벽 3개 더 생겼으므로 안전구역 개수 3개 줄어듦

                    visited = new boolean[N][M];
                    BFS();

                    answer = Math.max(answer, tmp);

                    // 원상복구
                    map[wall3/M][wall3%M] = 0;
                }
                map[wall2/M][wall2%M] = 0;
            }
            map[wall1/M][wall1%M] = 0;
        }
        System.out.println(answer);
    }

    /**
     * 상하좌우 인접한 칸으로 퍼져나가게 하는 BFS
     * map 건들지 않고 따로 visited 배열에 전염(방문) 여부 체크함
     */
    static void BFS(){
        for(int[] v : virus){ // 각각의 바이러스마다 BFS 수행하기
            Queue<int[]> queue = new ArrayDeque<>(); // queue를 이용한 BFS
            queue.offer(v);

            while (!queue.isEmpty()){
                int[] coor = queue.poll();
                for(int[] dir : dirs){ // 4방향 탐색
                    int row = coor[0] + dir[0];
                    int col = coor[1] + dir[1];
                    if(isInRange(row, col)){ // 전염 가능한 칸!
                        queue.offer(new int[] {row, col}); // 큐에 넣기
                        visited[row][col] = true; // 방문체크
                        tmp--; // 안전구역 한 칸 줄어듦
                    }
                }
            }
        }
    }

    /**
     * 전염 가능한 칸인지 확인
     * @param row
     * @param col
     * @return
     */
    static boolean isInRange(int row, int col){
        return 0<=row && row<N && 0<=col && col<M && map[row][col]==0 && !visited[row][col];
    }
}
