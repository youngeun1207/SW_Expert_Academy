import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 17130 토끼가 정보섬에 올라온 이유
 * @author youngeun
 *
 * 26924 kb
 * 404 ms
 *
 * [2차원 DP]
 * 1. ↗, →, ↘ 방향으로만 이동할 수 있기 때문에 ←, ↙, ↖ 방향이 현재 노드에 도달하기 위해 들릴 수 있는 직전노드
 * 2. 직전 노드 중 최대 당근 + 현재 칸 당근(0 or 1)이 현재 칸까지 모을 수 있는 최대 당근 수
 * 3. 토끼는 오른->왼쪽으로만 이동할 수 있기 때문에 오른쪽으로 쭉쭉 이동하면서 직전 최대값 구함
 * 4. 이때, 토끼가 과거에 들린 적 없는 칸이면 합산에서 제외(visited 배열로 관리함)
 */
public class RabbitOnJungboIsland {
    static int N;
    static int M;
    static int[][] dirs = {{-1, 1}, {0, 1}, {1, 1}}; // ↗, →, ↘
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        char[][] island = new char[N][M];
        String str;
        int startX = 0, startY = 0;
        // 격자 값 받아오기
        for(int n = 0; n < N; n++){
            str = br.readLine();
            for (int m = 0; m < M; m++){
                island[n][m] = str.charAt(m);
                if(island[n][m] == 'R'){ // 시작점
                    startX = n;
                    startY = m;
                }
            }
        }
        int[][] dp = new int[N][M];
        boolean[][] visited = new boolean[N][M];
        int prevX, prevY;

        visited[startX][startY] = true;
        for(int y = startY+1; y < M; y++){ // 어차피 토끼 시작점부터 오른쪽으로만 진행하기 때문에 startY 이전 값은 볼 필요 없다.
            for(int x = 0; x < N; x++){
                if(island[x][y] == '#'){ // 벽이면 이동 X
                    continue;
                }
                for(int dir = 0; dir < 3; dir++){
                    // 현재 노드까지 오는데 가능한 직전 노드
                    prevX = x - dirs[dir][0]; // - 해주므로 ←, ↙, ↖ 방향 가리킴
                    prevY = y - dirs[dir][1];

                    if(!isOutOfBound(prevX, prevY) && visited[prevX][prevY]){ // 직전 노드가 범위 넘어간 노드 + 이전에 방문하지 않은 제외
                        visited[x][y] = true; // 방문 체크

                        if(island[x][y] == 'C'){ // 당근 get
                            dp[x][y] = Math.max(dp[x][y] , dp[prevX][prevY] + 1); // 이전 dp + 1과, 현재 저장된 값 중 고르기
                        }else { // 빈 공간 or 탈출구
                            dp[x][y] = Math.max(dp[x][y] , dp[prevX][prevY]); // 직전 값 복붙
                        }
                    }
                }
            }
        }

        int answer = -1;
        for(int n = 0; n < N; n++){
            for (int m = 0; m < M; m++){
                if(island[n][m] == 'O' && visited[n][m]){ // 출입구에 저장된 dp 값 중 최대값이 정답
                    answer = Math.max(answer, dp[n][m]);
                }
            }
        }

        System.out.println(answer);


    }
    static boolean isOutOfBound(int x, int y){
        return x < 0 || y < 0 || x >= N || y >= M;
    }
}
