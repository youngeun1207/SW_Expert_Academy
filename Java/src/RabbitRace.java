import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 코드트리 토끼와 경주
 * 삼성 SW 역량테스트 2023 상반기 오전 2번 문제
 *
 * 5675ms
 * 46MB
 * <우선순위 큐 + 구현>
 * - 다양한 우선순위에 따라 우선순위 큐를 설계해 사용함
 * - 문제에서 row+col이 같고 row도 같으면 col로 비교하라는 조건이 있는데, 해당 조건은 불필요한(무조건 같은) 조건이므로 제거함
 * - 토끼의 이동 거리가 매우 길기 때문에 (1 ≤ d ≤ 1,000,000,000) 왕복 순환 배열을 활용해 나머지 연산으로 현재 인덱스 계산함
 *   -> 백준 낚시왕과 유사한 풀이
 */
public class RabbitRace {
    // 우선순위 큐 활용
    static PriorityQueue<Rabbit> rabbits, moved; // 경주 진행 중 우선순위 구하기, 경주 종료 후 최고 우선순위 토끼 구하기
    static PriorityQueue<int[]> coor; // 4방 중 우선순위 높은 좌표 구하기
    static Map<Long, Rabbit> rabbitMap; // 토끼 이동거리 변경을 위해 저장
    static int op, Q, N, M, P, K, S;
    static int[] cycleN, cycleM;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Q = Integer.parseInt(br.readLine());

        StringTokenizer st = new StringTokenizer(br.readLine());
        op = Integer.parseInt(st.nextToken());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 끝에 닿으면 반대로 움직이는 왕복이므로 5칸이면 01234321 순으로 움직임
        // 토끼가 움직이는 상행, 하행 사이클 저장
        cycleN = new int[2*N-2];
        cycleM = new int[2*M-2];
        for (int r = 0; r < N; r++) {
            cycleN[r] = r;
        }
        for (int r = 2; r < N; r++) {
            cycleN[N + r - 2] = N - r;
        }
        for (int c = 0; c < M; c++) {
            cycleM[c] = c;
        }
        for (int c = 2; c < M; c++) {
            cycleM[M + c - 2] = M - c;
        }

        P = Integer.parseInt(st.nextToken());
        rabbits = new PriorityQueue<>();
        rabbitMap = new HashMap<>();
        // 경주 시작 준비
        for(int p = 0; p < P; p++){
            Rabbit rabbit = new Rabbit(Long.parseLong(st.nextToken()), Integer.parseInt(st.nextToken())); // 고유번호, 이동거리
            rabbits.add(rabbit);
            rabbitMap.put(rabbit.id, rabbit);
        }

        for(int q = 0; q < Q-1; q++){
            st = new StringTokenizer(br.readLine());
            op = Integer.parseInt(st.nextToken());
            switch (op){
                case 200: // 경주 진행
                    K = Integer.parseInt(st.nextToken());
                    S = Integer.parseInt(st.nextToken());
                    Rabbit rabbit;
                    // 점수 S 부여 시 K번의 턴 동안 한번이라도 뽑혔던 적이 있던 토끼 중 가장 우선순위가 높은 토끼를 골라야만 하므로 뽑혔던 토끼 저장
                    // 우선순위: 현재 서있는 행 번호 + 열 번호가 큰 토끼 > 행 번호가 큰 토끼 > 열 번호가 큰 토끼 > 고유번호가 큰 토끼
                    // 단, 열은 무조건 같다
                    moved = new PriorityQueue<>((r2, r1)->{ // 내림차순이므로 r2, r1
                        // 행 + 열
                        long res = (r1.col + r1.row) - (r2.col + r2.row);
                        if(res == 0){
                            // 열
                            res = r1.row - r2.row;
                            if(res == 0){
                                // 고유번호
                                return (int)(r1.id - r2.id);
                            }return (int)res;
                        }return (int)res;
                    });

                    for(int k = 0; k < K; k++){
                        // 우선순위: 행 번호 + 열 번호가 큰 칸 > 행 번호가 큰 칸 > 열 번호가 큰 칸
                        // 단, 열은 무조건 같다
                        coor = new PriorityQueue<>((r2, r1)->{ // 내림차순이므로 r2, r1
                            // 행 + 열
                            int res = (r1[0]+r1[1]) - (r2[0]+r2[1]);
                            if(res == 0){
                                // 행
                                return r1[0] - r2[0];
                            }return res;
                        });
                        // 가장 우선순위 높은 토끼
                        rabbit = rabbits.poll();

                        // 상하좌우 이동한 좌표 구하기
                        move(rabbit);

                        // 우선순위 높은 칸 구하기
                        // 우선순위 높은 칸으로 이동
                        int[] max = coor.poll();
                        rabbit.row = max[0];
                        rabbit.col = max[1];

                        // 나머지 토끼들 점수 올리기
                        for(Rabbit r : rabbits){
                            r.score += (rabbit.row + rabbit.col + 2); // 문제는 좌표 1부터 시작이므로 +2 해줌
                        }
                        // 우선순위 높은 토끼 이동한 토끼 목록에 추가하고, 다시 우선순위 큐에 추가
                        rabbit.jump++;
                        moved.add(rabbit);
                        rabbits.offer(rabbit);
                    }
                    // 모든 라운드 끝나면, 이동했던 토끼 중 우선순위 가장 높은 토끼에게 점수 S 추가
                    // 큰 경우 우선
                    rabbit = moved.poll();
                    rabbit.score+=S;
                    break;
                case 300: // 이동거리 변경
                    rabbitMap.get(Long.parseLong(st.nextToken())).dist *= Long.parseLong(st.nextToken());
                    break;
                case 400: // 최고의 토끼 선정
                    long answer = -1;
                    for(Rabbit r : rabbits){
                        answer = Math.max(answer, r.score);
                    }
                    System.out.println(answer);
            }
        }
    }

    static void move(Rabbit rabbit){
        for (int dir = 0; dir < 4; dir++){ // 상 하 좌 우
            int r = rabbit.row;
            int c = rabbit.col;

            // 역방향(위, 왼쪽)일 경우 cycle에서의 index로 바꿔 저장해야함
            if (dir == 0) {
                r = 2 * N - r - 2;
            } else if (dir == 2) {
                c = 2 * M - c - 2;
            }

            long distance;
            int row = r, col = c;
            if (dir < 2) { // 위, 아래 -> row 변경
                distance = r + rabbit.dist; // 현재 위치(cycle 내 index) + 이동거리
                row = (int)distance % cycleN.length; // cycle로 저장했기 때문에 나머지 연산하면 위치 빠르게 구할 수 있음!
            } else { // 오른, 왼 -> col 변경
                distance = c + rabbit.dist;
                col = (int)distance % cycleM.length;
            }
            coor.offer(new int[] {cycleN[row], cycleM[col]}); // 실제 좌표는 cycle 배열의 해당 인덱스에 저장된 값임
        }
    }

    static class Rabbit implements Comparable<Rabbit>{
        long jump, id, dist, score; // 점프 횟수, 고유번호, 이동 거리, 점수
        int row, col; // 토끼가 서있는 좌표

        public Rabbit(long id, long dist){
            this.jump = 0;
            this.row = 0;
            this.col = 0;
            this.id = id;
            this.dist = dist;
            this.score = 0;
        }

        @Override
        public int compareTo(Rabbit r) {
            // 우선순위: 현재까지의 총 점프 횟수가 적은 토끼 > 행 + 열 작은 토끼, 행 작은 토끼, 열 작은 토끼, 고유번호가 작은 토끼
            // 단, 열은 비교할 필요 없이 행 같으면 무조건 같음
            // 점프 횟수
            long res = this.jump - r.jump;
            if(res == 0){
                // 행 + 열
                res = (this.col + this.row) - (r.col + r.row);
                if(res == 0){
                    // 행
                    res = this.row - r.row;
                    if(res == 0){
                        // 고유번호
                        return (int)(this.id - r.id);
                    }return (int)res;
                }return (int)res;
            }return (int)res;
        }
    }
}