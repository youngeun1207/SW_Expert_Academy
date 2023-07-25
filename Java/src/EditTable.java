import java.util.*;

/**
 * 프로그래머스 Lv.3 표 편집
 * 2021 카카오 채용연계형 인턴십 기출
 * 정확도 테스트 : 7.06ms, 79.6MB
 * 효율성 테스트 : 322.23ms, 154MB
 *
 * @author youngeun
 * LinkedList와 Stack 활용한 문제
 * 리스트 내 특정 인덱스 삭제, 삽입 작업을 효율적으로 하기 위해 Linked List 구현해서 사용함
 * 직전에 지워진 항목 복구를 위해 Stack 사용
 */
public class EditTable {
    public String solution(int n, int k, String[] cmds) {
        class Node { // Linked List 내 Node
            int idx; // 초기 index
            int up; // 윗 행 index
            int down; // 아래 행 index
            String isExist; // 삭제 여부

            Node(int idx, int up, int down) {
                this.idx = idx;
                this.up = up;
                this.down = down;
                this.isExist = "O";
            }

            @Override
            public String toString() {
                return this.isExist;
            }
        }

        Deque<Node> stack = new ArrayDeque<>(); // 복구(Z) 작업을 위해 삭제 행 모아두는 stack

        Node[] table = new Node[n];
        for (int i = 0; i < n; i++) { // Linked-List이므로 현재 노드, 바로 위 노드, 바로 아래 노드 가리키도록 함
            table[i] = new Node(i, i - 1, i + 1);
        }
        // 맨 위, 맨 아래 노드는 -1 가리키도록
        table[0].up = -1;
        table[n - 1].down = -1;

        int current = k; // 현재 선택한 행 index

        for (String cmd : cmds) {
            String[] parsedCmd = cmd.split(" ");
            char command = parsedCmd[0].charAt(0);
            int x; // 이동할 거리 저장

            switch (command) {
                case 'U': // 위로 x칸
                    x = Integer.parseInt(parsedCmd[1]);
                    for (int i = 0; i < x; i++) {
                        current = table[current].up;
                    }
                    break;
                case 'D': // 아래로 x칸
                    x = Integer.parseInt(parsedCmd[1]);
                    for (int i = 0; i < x; i++) {
                        current = table[current].down;
                    }
                    break;
                case 'C': // 선택 행 삭제
                    Node node = table[current];
                    node.isExist = "X";
                    stack.push(node); // 삭제 행 모아두는 stack에 push
                    if (node.up != -1) { // 나의 윗 행을 나의 아래 행과 연결
                        table[node.up].down = node.down;
                    }
                    if (node.down != -1) { // 나의 아래 행을 나의 윗 행과 연결
                        table[node.down].up = node.up;
                    }
                    // 만약 아래 행이 없으면 바로 윗 행 선택
                    current = node.down != -1 ? node.down : node.up;
                    break;
                case 'Z': // 직전 삭제 행 복구
                    Node restoreNode = stack.pop(); // 지운 행 모아둔 stack에서 가장 최근 지운 행 pop
                    restoreNode.isExist = "O";
                    if (restoreNode.up != -1) { // 나와 기존 윗 행을 연결
                        table[restoreNode.up].down = restoreNode.idx;
                    }
                    if (restoreNode.down != -1) { // 나와 기존 아래 행 연결
                        table[restoreNode.down].up = restoreNode.idx;
                    }
                    break;
            }
        }

        StringBuilder result = new StringBuilder();
        for (Node node : table) {
            result.append(node);
        }
        return result.toString();
    }
}
