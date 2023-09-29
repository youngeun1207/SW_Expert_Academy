import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백준 21610 마법사 상어와 비바라기
 * @author youngeun
 *
 * 17776 KB
 * 192 ms
 *
 * <단순구현 문제>
 * 문제의 요건을 잘 따라서 풀면 되는 단순구현 문제이다..
 */
public class BJ_21610_마법사상어와비바라기_신영은 {
    static int N, M, dir, dist;
    static int[][] A;
    static List<int[]> clouds;
    static boolean[][] prevClouds;
    static int[][] dirs = new int[][]{{0, -1}, {-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}}; // ←, ↖, ↑, ↗, →, ↘, ↓, ↙
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        A = new int[N][N]; // 바구니
        clouds = new ArrayList<>(); // 구름 위치 -> 구름은 비 내린 이후 사라지기 때문에 queue 사용함..

        // 구름 초기값
        clouds.add(new int[]{N-1, 0});
        clouds.add(new int[]{N-1, 1});
        clouds.add(new int[]{N-2, 0});
        clouds.add(new int[]{N-2, 1});

        // 바구니 초기값
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++){
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 이동하기
        for(int m = 0; m < M; m++){
            st = new StringTokenizer(br.readLine());
            dir = Integer.parseInt(st.nextToken()) - 1; // 이동 좌표 배열 0부터 시작함..
            dist = Integer.parseInt(st.nextToken()) % N;

            // 구름 이동 및 비내리기
            moveClouds();

            // 물복사 버그
            copyWater();

            // 새로 구름 생성하기
            makeClouds();
        }

        // 정답 구하기
        System.out.println(getResult());
    }

    /**
     * 이동 방향과 거리에 따라 구름 이동
     * 칸을 넘어가면 반대편 시작점으로 이동함
     * 이동 이후 비 내리기까지 시전
     */
    static void moveClouds(){
        for (int[] cloud : clouds){
            // 음수 교정을 위해 N 더해줌
            cloud[0] = (cloud[0] + dirs[dir][0] * dist + N) % N;
            cloud[1] = (cloud[1] + dirs[dir][1] * dist + N) % N;

            // 구름이 있는 곳의 물 1씩 증가시키기
            rain(cloud);
        }
    }

    /**
     * 구름이 있는 칸의 물 1씩 증가시키기
     * @param cloud
     */
    static void rain(int[] cloud){
        A[cloud[0]][cloud[1]]++;
    }

    /**
     * 물복사 버그
     * 대각선 4방향 위치에 물이 있는 경우 물 1씩 증가시키기
     */
    static void copyWater(){
        prevClouds = new boolean[N][N];
        for (int[] cloud : clouds) {
            // 복사버그
            // dirs 배열 재사용 -> 1, 3, 5, 7번째
            for (int dir = 1; dir < 8; dir+=2){
                A[cloud[0]][cloud[1]] += isWaterExist(cloud[0] + dirs[dir][0], cloud[1] + dirs[dir][1]);
            }
            // 이전 구름이 있던 위치 표시 -> 새 구름 생성 시 참고
            prevClouds[cloud[0]][cloud[1]] = true;
        }
    }

    /**
     * 대각선 인접한 칸에 물이 존재하는지 여부 확인
     * @param x
     * @param y
     * @return 존재하면 1, 없거나 칸을 넘어가면 0
     */
    static int isWaterExist(int x, int y){
        return (0 <= x && x < N && 0 <= y && y < N && A[x][y] > 0) ? 1 : 0;
    }

    /**
     * 물의 양이 2 이상이면서, 이전에 구름이 없던 위치에 구름을 새로 생성해주기
     * 비가 내린 후에 구름은 사라지기 때문에 새로운 구름 리스트 생성해줌.
     */
    static void makeClouds(){
        clouds = new ArrayList<>(); // 구름 리스트 초기화
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                if(A[i][j] >= 2 && !prevClouds[i][j]){ // 물의 양이 2 이상이면서, 이전에 구름이 있던 위치가 아닐 때
                    clouds.add(new int[]{i, j}); // 새 구름 리스트에 추가
                    A[i][j] -= 2; // 물 양 2씩 줄이기
                }
            }
        }
    }

    /**
     * 전체 칸의 물 양 합하기
     * @return 전체 칸의 물 양 합산
     */
    static int getResult(){
        int answer = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                answer+=A[i][j];
            }
        }
        return answer;
    }
}
