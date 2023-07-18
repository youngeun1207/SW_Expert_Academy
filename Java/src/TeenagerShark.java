import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author youngeun
 * 백준 19236 청소년 상어
 * 삼성 SW 역량 테스트 기출
 * DFS + 단순구현
 */
class Fish {
    int r, c, dir;
    boolean dead;

    public Fish(int r, int c, int dir) {
        this.r = r;
        this.c = c;
        this.dir = dir;
        this.dead = false;
    }
    public Fish(int r, int c, int dir, boolean dead) {
        this.r = r;
        this.c = c;
        this.dir = dir;
        this.dead = dead;
    }

    @Override
    public String toString() {
        String[] dirs = {"↑", "↖", "←", "↙", "↓", "↘", "→", "↗"};
        return "r:" + r + " c:" + c + " dir:" + dirs[dir] + " dead:" + dead;
    }
}

public class TeenagerShark {
    static int[][] fishMap;
    static Map<Integer, Fish> fishState;
    static int[] dirsR = {-1, -1, 0, 1, 1, 1, 0, -1};
    static int[] dirsC = {0, -1, -1, -1, 0, 1, 1, 1};
    static int answer = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        fishMap = new int[4][4];
        fishState = new HashMap<>();

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 8; c += 2) {
                int fishNumber = scanner.nextInt();
                int fishDir = scanner.nextInt() - 1;
                fishState.put(fishNumber, new Fish(r, c / 2, fishDir));
                fishMap[r][c / 2] = fishNumber;
            }
        }

        DFS(0, 0, 0, fishMap, fishState);

        System.out.println(answer);
    }

    static boolean cannotMove(int newR, int newC, Fish shark) {
        if (newR >= 0 && newR < 4 && newC >= 0 && newC < 4 && !(newR == shark.r && newC == shark.c)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param shark : 상어 위치 저장용
     * @param fishMap : 물고기 위치 저장된 2차원 배열
     * @param fishState : 물고기 상태 저장된 Map
     */
    static void moveFish(Fish shark, int[][] fishMap, Map<Integer, Fish> fishState) {
        for (int i = 1; i <= 16; i++) { // 1~16번 물고기까지 순서대로 움직이기
            Fish fish = fishState.get(i);
            if (fish.dead) { // 잡아먹힌 물고기는 옮길 필요 없음
                continue;
            }

            int newR = fish.r + dirsR[fish.dir];
            int newC = fish.c + dirsC[fish.dir];

            while (cannotMove(newR, newC, shark)) { // 움직일 수 없으면 반시계 회전
                fish.dir = (fish.dir + 1) % 8;
                newR = fish.r + dirsR[fish.dir];
                newC = fish.c + dirsC[fish.dir];
            }
            // 물고기 바꾸기
            // 물고기 지도와 물고기 상태 모두 변경해야함
            fishMap[fish.r][fish.c] = fishMap[newR][newC];
            fishState.get(fishMap[newR][newC]).r = fish.r;
            fishState.get(fishMap[newR][newC]).c = fish.c;

            fishMap[newR][newC] = i;
            fish.r = newR;
            fish.c = newC;
        }
    }

    /**
     * @param eat : 먹은 물고기 점수
     * @param r : 상어 위치 r
     * @param c : 상어 위치 c
     * @param fishMap : 물고기 위치 저장된 2차원 배열
     * @param fishState : 물고기 상태 저장된 Map
     */
    static void DFS(int eat, int r, int c, int[][] fishMap, Map<Integer, Fish> fishState) {
        fishState.get(fishMap[r][c]).dead = true;
        eat += fishMap[r][c];

        answer = Math.max(eat, answer); // 현재 상태가 먹을 수 있는 최대 값이면 결과 갱신

        Fish shark = new Fish(r, c, fishState.get(fishMap[r][c]).dir); // 상어 객체이므로 순회하면서 내부 바뀌지 않도록 copy해서 넘김
        moveFish(shark, fishMap, fishState);

        for (int i = 1; i < 4; i++) { // 방향만 맞으면 여러 칸 한번에 이동할 수 있음
            int newR = r + dirsR[shark.dir] * i;
            int newC = c + dirsC[shark.dir] * i;

            if (newR >= 0 && newR < 4 && newC >= 0 && newC < 4 && !fishState.get(fishMap[newR][newC]).dead) {
                // 내부 바뀌지 않도록 주소값 전달이 아닌 내부 값 copy해서 넘김
                int[][] copyMap = new int[4][4];
                for (int j = 0; j < 4; j++) {
                    System.arraycopy(fishMap[j], 0, copyMap[j], 0, 4);
                }
                // 객체 참조값이 아닌 물고기 객체 내 값을 복사해서 넘겨줘야함
                Map<Integer, Fish> copyState = new HashMap<>();
                int fishIndex = 1;
                for(Fish fish : fishState.values()){
                    Fish copyFish = new Fish(fish.r, fish.c, fish.dir, fish.dead);
                    copyState.put(fishIndex, copyFish);
                    fishIndex++;
                }
                DFS(eat, newR, newC, copyMap, copyState);
            }
        }
    }
}
