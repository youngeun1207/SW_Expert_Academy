package ye;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 백준 17143 낚시왕
 * @author youngeun
 *
 * 23472 KB
 * 288 ms
 *
 * <단순구현>
 * - 상어가 거꾸로 가는 경우(상, 좌) 인덱스를 일일히 교정하면 비효율적임
 * - 모듈러 연산만으로 상어의 현재 위치 쉽게 구할 수 있도록 인덱스 사이클 배열을 생성
 *     - 예) R=4 -> [0, 1, 2, 3, 2, 1]
 *     - 상어 위치 최초 1회 교정 후에는 상행은 하행처럼, 좌행은 우행처럼 똑같이 쓸 수 있음
 *     - 단, 지도에 상어 위치 저장 시에는 사이클 배열에 저장된 인덱스로 변경하기
 */
public class KingOfFishing {
    static int R, C, M, answer = 0;
    static Shark[] sharks;
    static Shark[][] map;
    static int[] rows, cols;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        // 사이클 만들기
        rows = new int[R*2-2];
        cols = new int[C*2-2];
        for(int i = 0; i < R; i++) {
            rows[i] = i;
        }
        int cnt = R-2;
        for(int i = R; i < rows.length; i++) {
            rows[i] = cnt--;
        }
        for(int i = 0; i < C; i++) {
            cols[i] = i;
        }
        cnt = C-2;
        for(int i = C; i < cols.length; i++) {
            cols[i] = cnt--;
        }

        // 상어 정보 받기
        sharks = new Shark[M];
        map = new Shark[R][C];
        for(int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            Shark s = new Shark(r, c, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
            sharks[m] = s;
            map[r-1][c-1] = s;
        }

        // 열 수 만큼 반복
        for(int c = 0; c < C; c++) {
            get(c); // 낚시
            move(); // 상어 이동
            eat(); // 상어끼리 잡아먹기
        }

        System.out.println(answer);
    }

    /**
     * 상어 이동
     */
    static void move() {
        for(Shark s : sharks) {
            if(s.dead) { // 죽은 상어
                continue;
            }

            if(s.d <= 2) { // 세로 이동
                s.r = (s.r+s.s) % rows.length;
            }else { // 가로 이동
                s.c = (s.c+s.s) % cols.length;
            }
        }
    }

    /**
     * 상어 잡아먹기
     */
    static void eat() {
        map = new Shark[R][C];
        for(Shark s : sharks) {
            if(s.dead) { // 죽은 상어
                continue;
            }
            int row = rows[s.r]; // 실제 인덱스로 변경
            int col = cols[s.c];

            if (map[row][col] == null) { // 빈칸
                map[row][col] = s;
            }
            else if(map[row][col].z < s.z) { // 내가 더 큼 -> 기존 상어 잡아먹기
                map[row][col].dead = true;
                map[row][col] = s; // 바꾸기
            }
            else { // 내가 더 작음 -> 잡아먹힘
                s.dead = true;
            }
        }
    }

    /**
     * 낚시왕이 상어 잡기
     */
    static void get(int col) {
        for(int row = 0; row < R; row++) {
            if(map[row][col] != null) {
                map[row][col].dead = true;
                answer+=map[row][col].z;
                break; // 가장 가까운거 잡은 후에는 다음 열로 이동
            }
        }
    }

    static class Shark {
        int r, c, s, d, z;
        boolean dead = false;

        public Shark(int r, int c, int s, int d, int z) {
            this.r = r-1; // 행
            this.c = c-1; // 열
            this.s = s; // 속도
            this.d = d; // 이동방향
            this.z = z; // 크기

            changeCoor();
        }
        /**
         * 사이클에 해당하는 인덱스로 좌표 교정하기
         * 거꾸로 가는 경우(상, 좌)에 해당
         */
        void changeCoor() {
            if(this.d == 1) { // 상행 -> r 교정
                this.r = rows.length - this.r;
            }else if(this.d == 4) { // 좌행 -> c 교정
                this.c = cols.length - this.c;
            }
        }
    }
}