import java.util.*;

/**
 * 프로그래머스 Lv.2 거리두기 확인하기
 * 2021 카카오 채용연계형 인턴십 기출
 * 1.31ms, 84.6MB
 *
 * @author youngeun
 * BFS 활용하여 BFS를 사용하여 응시자 간 거리 계산
 * distance 메소드로 맨해튼 거리(|r1 - r2| + |c1 - c2|) 계산
 */
class CheckDistance {
    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];
        int[][] move = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 오른쪽, 아래쪽, 왼쪽, 위쪽

        for (int idx = 0; idx < places.length; idx++) {
            String[] place = places[idx];
            // BFS로 인근 응시자 찾기 -> 가장 가까운 응시자가 2 이상이면 바로 넘어감
            Queue<int[]> queue = new LinkedList<>();

            // visited_origin: 기준점으로 선정된 전적 있는지
            boolean[][] visited_origin = new boolean[5][5];
            boolean flag = false;
            while (true) {
                int[] origin = findHuman(place, visited_origin); // 기준이 될 사람 지정
                if (origin == null) {
                    break;
                }
                queue.offer(origin);
                // 해당 사람 기준으로 BFS 돌려야 함 -> visited 별도로 생성
                boolean[][] visited = new boolean[5][5];
                Loop :
                while (!queue.isEmpty()) { // BFS
                    int[] dot = queue.poll();
                    int col = dot[0], row = dot[1];
                    visited[col][row] = true;
                    for (int[] moveDot : move) { // 상하좌우 4방향으로 이동
                        int newCol = col + moveDot[0], newRow = row + moveDot[1];
                        // map 넘어가지 않았으면서, 파티션(X)이 아니면서(파티션에 막히면 더 이상 이동 X), 방문하지 않았으면서, 맨해튼거리 초과하지 않은 노드 검증
                        if (newCol >= 0 && newCol < 5 && newRow >= 0 && newRow < 5 && place[newCol].charAt(newRow) != 'X' && !visited[newCol][newRow] && distance(origin, newCol, newRow)) {
                            if (place[newCol].charAt(newRow) == 'P') { // 거리두기 안지킴
                                answer[idx] = 0;
                                flag = true;
                                break Loop; // 한명이라도 조건 충족 못하면 바로 탈락 -> 더 검증할 필요 없이 바로 중첩 반복문 탈출
                            }
                            queue.offer(new int[]{newCol, newRow}); // 빈 자리인 경우 맨해튼 거리 내에서 BFS 순회
                        }
                    }
                }
            }
            if (!flag) {
                answer[idx] = 1; // 모든 사람 거리두기 통과
            }
        }
        return answer;
    }

    /**
     * 맨해튼 거리(|r1 - r2| + |c1 - c2|) 계산 하는 메소드
     * @param dot1
     * @param col
     * @param row
     * @return
     */
    private boolean distance(int[] dot1, int col, int row) {
        int dist = Math.abs(dot1[0] - col) + Math.abs(dot1[1] - row);
        return dist <= 2; // 맨해튼 거리 2 이하인지? -> 2 이상은 확인할 필요 없음
    }

    /**
     * 기준점이 될 사람 찾는 메소드
     * @param place : 시험장 하나
     * @param visited : 이미 검증한 사람인지?
     * @return
     */
    private int[] findHuman(String[] place, boolean[][] visited) {
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                if (place[col].charAt(row) == 'P' && !visited[col][row]) { // 검증한 전적 없는 사람 찾기
                    visited[col][row] = true;
                    return new int[]{col, row};
                }
            }
        }
        return null;
    }
}
