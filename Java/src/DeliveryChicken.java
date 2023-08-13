import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * BJ 15686 치킨 배달
 * @author youngeun
 *
 * 300,660 KB
 * 2,444 ms
 *
 * <완전탐색>
 * 1 <= M <= 치킨집 수 <= 13
 * 완전탐색 가능하다!
 * 괜히 복잡하게 풀어보려다 시간만 오래 걸렸다.
 * 앞으로 간단하게 풀 수 있는 문제는 간단하게 풀어봐야지
 */
public class DeliveryChicken {
    static int N, M, originDist = 0, answer = Integer.MAX_VALUE;
    static int[][] town;
    static boolean[][] visited;
    static Chicken[][] chickenMap;
    static List<House> houses;
    static List<Chicken> chickens, deleted;
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        town = new int[N][N];
        houses = new ArrayList<>();
        chickens = new ArrayList<>();
        chickenMap = new Chicken[N][N];
        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < N; j++) {
                town[i][j] = Integer.parseInt(st.nextToken());
                if(town[i][j] == 1) {
                    houses.add(new House(i, j));
                }else if(town[i][j] == 2) {
                    chickenMap[i][j] = new Chicken(i, j);
                    chickens.add(chickenMap[i][j]);
                }
            }
        }
        // 초기 치킨 거리 구하기
        // 가장 가까운 거리이므로 BFS
        for(House house : houses) {
            getShortestDist(house, true);
            originDist += house.dist;
        }

        // 치킨 (C-M)개 골라서 폐업 -> 조합
        // 고른 치킨집과 인접한 집들의 최소 치킨거리 다시 구하기
        // 해당 치킨집들의 좌표값 town 2 -> 0으로 바꾸기
        // 치킨거리 계산 이후 치킨 좌표 다시 원복
        int C = chickens.size();
        Chicken chicken;
        int cnt = 0;
        int[] idx = new int[C];
        while(++cnt <= C-M) { // 뒤쪽부터 고를 숫자 갯수만큼 채우기
            idx[C-cnt] = 1;
        }
        do {
            int newAnswer = originDist;
            deleted = new ArrayList<>();
            // 치킨집 없애기
            for(int i = 0; i < C; i++) {
                if(idx[i] == 0) {
                    continue;
                }
                chicken = chickens.get(i);
                deleted.add(chicken);
                town[chicken.x][chicken.y] = 0;
            }
            // 치킨거리 다시 구하기
            // 가까운 치킨집 바뀐 경우만
            int prev;
            for(Chicken del : deleted) {
                for(House house : del.houses) {
                    prev = house.dist;
                    getShortestDist(house, false);
                    newAnswer += (house.dist - prev);
                    house.dist = prev;
                }
            }
            // 배열 원복
            for(Chicken del : deleted) {
                town[del.x][del.y] = 2;
            }
            answer = Math.min(answer, newAnswer);
        }while(nextPermutation(idx)); // chickenIdx 배열로 np 돌릴거임

        System.out.println(answer);
    }

    static void getShortestDist(House house, boolean flag) {
        Queue<Coordinate> queue;
        Coordinate c;
        int newX, newY;

        visited = new boolean[N][N];
        queue = new ArrayDeque<>();
        queue.add(new Coordinate(house.x, house.y));
        visited[house.x][house.y] = true;

        while (!queue.isEmpty()) {
            c = queue.poll();
            if(town[c.x][c.y] == 2) { // 치킨집!
                house.dist = house.getDistance(c);
                if(flag) {
                    chickenMap[c.x][c.y].houses.add(house);
                }
                break;
            }
            for(int[] dir : dirs) {
                newX = c.x + dir[0];
                newY = c.y + dir[1];
                if(isInRange(newX, newY) && !visited[newX][newY]) {
                    queue.add(new Coordinate(newX, newY));
                    visited[newX][newY] = true;
                }
            }
        }
    }

    static boolean isInRange(int x, int y) {
        if(0 <= x && x < N && 0 <= y && y < N) {
            return true;
        }
        return false;
    }

    static class Coordinate{
        int x;
        int y;
        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }
        int getDistance(Coordinate c) {
            return Math.abs(this.x-c.x) + Math.abs(this.y-c.y);
        }
    }

    static class Chicken extends Coordinate{
        List<House> houses; // 인접한 집 목록
        public Chicken(int x, int y) {
            super(x, y);
            this.houses = new ArrayList<>();
        }
    }

    static class House extends Coordinate{
        int dist;
        public House(int x, int y) {
            super(x, y);
        }
    }

    static boolean nextPermutation(int[] p) { // p: 다음 순열을 원하는 기존 순열의 배열
        // 1. 맨 뒤에서부터 탐색하며 꼭대기 찾기
        int N = p.length;
        int i = N-1;
        while (i > 0 && p[i-1] >= p[i]) {
            --i;
        }
        if(i==0) { // 다음 순열 없음(가장 큰 순열의 형태)
            return false;
        }

        // 2. 꼭대기 직전(i-1) 위치에 교환할 한단계 큰 수 뒤쪽부터 찾기
        int j = N-1;
        while (p[i-1] >= p[j]) {
            --j;
        }

        // 3. 꼭대기 직전(i-1) 위치의 수와 한단계 큰 수(j)를 교환
        swap(p, i-1, j);

        // 4. 꼭대기 자리부터 맨 뒤까지의 수를 오름차순의 형태로 바꿈
        int k = N-1;
        while(i<k) {
            swap(p, i++, k--);
        }
        return true; // 순열 완성
    }

    static void swap(int[] p, int a, int b) {
        int tmp = p[a];
        p[a] = p[b];
        p[b] = tmp;
    }
}
