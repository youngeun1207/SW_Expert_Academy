import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 백준 17143 낚시왕
 * 삼성 SW 역량 테스트 기출
 * @author youngeun
 *
 * 메모리 75884 KB
 * 시간 1060 ms
 *
 * 단순 구현 + 해싱 + 이동 거리 계산
 */

class Shark {
    int idx, r, c, s, d, z;
    boolean dead;

    public Shark(int idx, int r, int c, int s, int d, int z) {
        this.idx = idx; // 상어 번호
        this.r = r; // row
        this.c = c; // col
        this.s = s; // speed
        this.d = d; // 이동 방향
        this.z = z; // size
        this.dead = false; // 상어 잡아먹히거나, 낚시꾼이 잡았을 때 true
    }

    @Override
    public String toString() { // 디버깅용
        String[] dirs = {"↑", "↓", "→", "←"};
        return idx + "번 상어 r:" + r + " c:" + c + " dir:" + dirs[d] + " dead:" + dead;
    }
}

public class KingOfFishing {
    static int R, C, M;
    static Map<Integer, Shark> sharkState;
    static int[][] sharkMap;
    static int answer = 0;
    static int[] cycleR, cycleC;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        R = scanner.nextInt();
        C = scanner.nextInt();
        M = scanner.nextInt();

        sharkState = new HashMap<>(); // shark_map 순회 안하고도 상어 이동할 수 있도록 상어 위치와 상태 HashMap으로 저장(해싱)
        sharkMap = new int[R][C]; // 2차원 배열 상의 상어 위치 -> 낚시 대상 상어 찾는 용으로 쓰임

        // 왕복이므로 5칸이면 01234321 순으로 움직임
        // 상어가 움직이는 상행, 하행 사이클 저장
        cycleR = new int[2*R-2];
        cycleC = new int[2*C-2];

        for (int r = 0; r < R; r++) {
            cycleR[r] = r;
        }
        for (int r = 2; r < R; r++) {
            cycleR[R + r - 2] = R - r;
        }
        for (int c = 0; c < C; c++) {
            cycleC[c] = c;
        }
        for (int c = 2; c < C; c++) {
            cycleC[C + c - 2] = C - c;
        }

        for (int m = 1; m < M+1; m++) {
            int r = scanner.nextInt();
            int c = scanner.nextInt();
            int s = scanner.nextInt();
            int d = scanner.nextInt();
            int z = scanner.nextInt();

            sharkMap[r-1][c-1] = m;
            // 역방향(위, 왼쪽)일 경우 cycle에서의 index로 바꿔 저장해야함
            if (d == 1) {
                r = 2 * R - r;
            } else if (d == 4) {
                c = 2 * C - c;
            }
            sharkState.put(m, new Shark(m, r-1, c-1, s, d-1, z));
        }

        for (int c = 0; c < C; c++) {
            getShark(c);
            for (Shark shark : sharkState.values()) {
                if (!shark.dead) { // 죽은 상어는 움직일 필요 없다
                    moveShark(shark);
                }
            }
            eatShark();
        }

        System.out.println(answer);
    }

    static void moveShark(Shark shark) {
        int distance;
        if (shark.d < 2) { // 위, 아래 -> r 변경
            distance = shark.s + shark.r;
            shark.r = distance % cycleR.length;
        } else { // 오른, 왼 -> c 변경
            distance = shark.s + shark.c;
            shark.c = distance % cycleC.length;
        }
    }

    static void eatShark() {
        sharkMap = new int[R][C]; // 상어끼리 잡아 먹은 후의 shark_map -> 초기화해서 사용함
        for (Shark shark : sharkState.values()) {
            if (!shark.dead) {
                // shark 객체 내에는 cycle 내에서의 index 저장했기 때문에 실제 index로 변환
                int r = cycleR[shark.r];
                int c = cycleC[shark.c];
                if (sharkMap[r][c] == 0) { // 비어있는 경우
                    sharkMap[r][c] = shark.idx;
                } else if (shark.z > sharkState.get(sharkMap[r][c]).z) { // 존 상어보다 큰 경우 -> 잡아먹기
                    sharkState.get(sharkMap[r][c]).dead = true;
                    sharkMap[r][c] = shark.idx;
                } else { // 기존 상어보다 작은 경우 -> 잡아먹히기
                    shark.dead = true;
                }
            }
        }
    }

    static void getShark(int idx) {
        for (int r = 0; r < R; r++) {
            if (sharkMap[r][idx] != 0) {
                Shark shark = sharkState.get(sharkMap[r][idx]);
                answer += shark.z;
                shark.dead = true;
                sharkMap[r][idx] = 0;
                break; // 가장 가까운 상어 낚시하면 바로 반복문 탈출
            }
        }
    }
}
