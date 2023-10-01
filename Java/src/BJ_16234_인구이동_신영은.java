import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 백준 16234 인구이동
 * @author youngeun
 *
 * 294124 KB
 * 540 ms
 *
 * <BFS>
 * 1. 상하좌우로 인접한 국가끼리 연합이 가능하면 BFS로 쭉쭉 뻗어나가기
 * 2. 이 때, visited 배열에 해당 국가가 속한 연합 번호를 마킹해두기
 * 3. 또한 연합의 개수를 저장해두어 만약 연합의 개수가 전체 국가의 개수와 동일하면 인구이동이 더이상 발생하지 않은 것이므로 종료
 * 4. 연합에 속한 인구 총합과, 국가 개수를 저장해두어 이후 이동 연산을 용이하게 함.
 */
public class BJ_16234_인구이동_신영은 {
    static int N, L, R, cnt, answer = 0;
    static int[][] A, visited, dirs = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static Queue<int[]> queue;
    static int[] sum, cities;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        A = new int[N][N];
        for (int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // BFS
       while (true){
            cnt = 0; // 연합 번호
            visited = new int[N][N]; // 해당 국가가 속한 연합 번호 저장(A와 1:1 매핑됨)
            sum = new int[N*N+1]; // 연합 내 인구 수 합
            cities = new int[N*N+1]; // 연합한 국가의 개수

            // 1. 국경선 열기
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (visited[i][j] == 0) { // 아직 어느 연합에도 속해있지 않은 국가임
                        visited[i][j] = ++cnt; // 속해있는 연합 번호 마킹 + 연합 개수 갱신
                        cities[cnt] = 1;
                        sum[cnt] = A[i][j];

                        // BFS로 국가 연합하기
                        queue = new ArrayDeque<>();
                        queue.add(new int[] {i, j});
                        BFS();
                    }
                }
            }

            // 2. 인구 이동
            for(int i = 0; i < N; i++){
                for(int j = 0; j < N; j++){
                    A[i][j] = sum[visited[i][j]] / cities[visited[i][j]];
                }
            }

            // 3. 인구 이동이 발생한 날짜 갱신
            if(cnt < N*N) answer++;
            else break; // 국경선 공유하는 국가가 하나도 없을 때(연합의 개수가 전체 국가의 개수임) 종료
        }

        System.out.println(answer);
    }

    /**
     * 연합 가능한 국가들끼리 BFS로 계속 뻗어나가면서 union
     */
    static void BFS(){
        while(!queue.isEmpty()){
            int[] city = queue.poll();
            for(int[] dir : dirs){ // 상하좌우로 인접한 국가 검증
                int newX = city[0] + dir[0];
                int newY = city[1] + dir[1];

                if(canCombine(newX, newY, city)){
                    queue.add(new int[] {newX, newY});
                    visited[newX][newY] = cnt; // 속해있는 연합 번호 마킹해주기
                    cities[cnt]++; // 연합에 속한 국가 수++
                    sum[cnt] += A[newX][newY]; // 연합에 속한 인구 수 합치기
                }
            }
        }
    }

    /**
     * 연합 가능한 국가인지 확인
     * @param x : 연합할 국가의 row
     * @param y : 연합할 국가의 col
     * @param city : 기존 국가의 좌표
     * @return : 연합 가능 여부
     */
    static boolean canCombine(int x, int y, int[] city){
        return 0 <= x && x < N && 0 <= y && y < N && visited[x][y] == 0
                && Math.abs(A[city[0]][city[1]] - A[x][y]) >= L
                && Math.abs(A[city[0]][city[1]] - A[x][y]) <= R;
    }
}
