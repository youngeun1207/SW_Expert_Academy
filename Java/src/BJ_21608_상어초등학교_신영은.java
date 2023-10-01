import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백준 21608 상어 초등학교
 * 삼성전자 2021년도 상반기 기출
 *
 * @author youngeun
 *
 * 17676 KB
 * 148 ms
 *
 * <단순구현 + 가중치>
 * 주위 4자리가 모두 비어있어도, 친구 1명이라도 있는 것이 더 높은 점수 받으므로 1*친구 > 4*빈자리인 가중치 부여
 * 1. 인접한 4자리에 친구 있으면: 친구 당 +5점
 * 2. 인접한 자리가 비어있으면: 자리 당 + 1점
 * 3. 점수 같으면: 왼쪽 위 -> 오른쪽 아래 순
 */
class Main {
    static class Shark { // 상어 상태 저장할 class
        int x; // 확정된 상어 자리 x
        int y; // 확정된 상어 자리 y
        List<Integer> friends; // 상어의 짱친들

        Shark(int x, int y, List<Integer> friends) {
            this.x = x;
            this.y = y;
            this.friends = friends;
        }
    }

    static int N;
    static Map<Integer, Shark> sharks = new HashMap<>(); // 상어 번호 : 상어 상태
    static int[][] fixedClassroom; // 자리 확정된 상어들
    static int[][] dirs = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}}; // 상하좌우 인접한 자리 인덱스

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        fixedClassroom = new int[N][N]; // 교실 내 자리의 점수 저장

        for (int x = 0; x < N * N; x++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int shark = Integer.parseInt(st.nextToken()); // 상어 번호
            List<Integer> friends = new ArrayList<>(); // 상어의 짱친들
            for (int i = 0; i < 4; i++) {
                friends.add(Integer.parseInt(st.nextToken()));
            }

            if (x == 0) { // 빈 교실에서 최고 우선순위는 (1, 1)
                fixedClassroom[1][1] = shark;
                sharks.put(shark, new Shark(1, 1, friends));
                continue;
            }

            int score = 0;
            int[][] classroom = new int[N][N];

            // 1. 인접한 4자리에 친구 있으면: 친구 당 +5점
            for (int friend : friends) {
                if (sharks.containsKey(friend)) { // 친구 확정된 자리 있음
                    Shark s = sharks.get(friend);
                    for (int[] dir : dirs) { // 친구 자리의 4방향 인접 자리에 가중치 +5
                        int nx = s.x + dir[0];
                        int ny = s.y + dir[1];
                        // 교실 범위 넘어가거나, 이미 누군가의 자리면 넘어감
                        if (nx >= 0 && nx < N && ny >= 0 && ny < N && fixedClassroom[nx][ny] == 0) {
                            classroom[nx][ny]+=5;
                            score = Math.max(score, classroom[nx][ny]);
                        }
                    }
                }
            }
            // 2. 인접한 자리가 비어있으면: 자리 당 + 1점
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (fixedClassroom[i][j] != 0) { // 이미 누군가의 자리면 넘어감
                        continue;
                    }
                    for (int[] dir : dirs) {
                        int nx = i + dir[0];
                        int ny = j + dir[1];
                        // 자신의 상하좌우 인접 자리가 비어있으면 가중치 +1
                        if (nx >= 0 && nx < N && ny >= 0 && ny < N && fixedClassroom[nx][ny] == 0) {
                            classroom[i][j]+=1;
                            score = Math.max(score, classroom[i][j]);
                        }
                    }
                }
            }
            // 3. 점수 같으면: 왼쪽 위 -> 오른쪽 아래 순
            Loop:
            for (int i = 0; i < N; i++) { // 왼쪽 위 -> 오른쪽 아래 순으로 검색하기 때문에 따로 로직 짤 필요 없음
                for (int j = 0; j < N; j++) {
                    if (fixedClassroom[i][j] != 0) {
                        continue;
                    }
                    if (classroom[i][j] == score) { // 최고점수에 해당하는 자리
                        fixedClassroom[i][j] = shark;
                        sharks.put(shark, new Shark(i, j, friends));
                        break Loop;
                    }
                }
            }
        }

        int answer = 0;
        for (Shark shark : sharks.values()) {
            answer += getScore(shark); // 상어 만족도 조사
        }
        System.out.println(answer);
    }

    /**
     * 만족도 조사 메소드
     * @param shark
     * @return
     */
    static int getScore(Shark shark) {
        // 친구 0명이면 -> 0점, 1명이면 1점, 2명이면 10점, 3명이면 100점 ...
        int score = -1; // 10**-1 = 0, 10**0 = 1, 10**1 = 10, 10 ** 2 = 100 ...
        int x = shark.x;
        int y = shark.y;
        for (int[] dir : dirs) { // 주변 4자리에 짱친 있는지 확인
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (nx >= 0 && nx < N && ny >= 0 && ny < N && shark.friends.contains(fixedClassroom[nx][ny])) {
                score++;
            }
        }
        return (int) Math.pow(10, score);
    }
}
