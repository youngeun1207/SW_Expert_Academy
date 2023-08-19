import java.io.*;
import java.util.StringTokenizer;

/**
 * 코드트리 산타의 선물 공장 2
 * 삼성 SW 역량테스트 2022 하반기 오후 2번 문제
 * @author youngeun
 *
 * 1594ms
 * 59MB
 *
 * <양방향 Linkded List>
 * 삽입과 삭제가 빈번히 일어나는데, 뒤가 아닌 맨 앞 요소를 변경한다는 점에서 Linked List를 사용하는 문제임을 확인!
 * 특히 400번 명령인 "물건 나누기"는 최대 100번까지만 주어진다는 점에서 순회를 하는 경우가 매우 적으므로 연결리스트가 적합하다고 보았다.
 *
 */
public class Santa2 {
    static int Q, N, M, op, src, dst;
    static Node[][] factory; // 시작 노드와 끝 노드
    static Node[] presents;
    static Node tmpStart, tmpEnd, tmp;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Q = Integer.parseInt(br.readLine());

        for(int q = 0; q < Q; q++){
            String str = br.readLine();
            StringTokenizer st = new StringTokenizer(str);
            op = Integer.parseInt(st.nextToken());
//            System.out.println(str);
            switch (op){
                case 100: // 공장 설립
                    N = Integer.parseInt(st.nextToken());
                    M = Integer.parseInt(st.nextToken());
                    factory = new Node[N+1][2]; // 공장 라인 별로 Linked List 저장할거임
                    presents = new Node[M+1]; // 500번 수행을 위해(특정 idx의 선물에 바로 접근해야함) 별도의 선물 배열 저장
                    for (int n = 1; n <= N; n++) { // Linked-List 초기화 -> 양방향 끝에 null 가리키는 빈 노드 생성
                        Node f = new Node(-1, null, null);
                        Node b = new Node(-1, null, null);
                        b.front = f;
                        f.back = b;

                        factory[n][0] = f;
                        factory[n][1] = b;
                    }
                    for(int m = 1; m <= M; m++){
                        int idx = Integer.parseInt(st.nextToken());
                        Node node = new Node(m, factory[idx][1].front, factory[idx][1]);
                        presents[m] = node;
                        factory[idx][1].front.back = node;
                        factory[idx][1].front = node;
                        factory[idx][0].length++;
                    }
                    break;

                case 200: // 선물 전체 옮기기
                    src = Integer.parseInt(st.nextToken());
                    dst = Integer.parseInt(st.nextToken());
                    if(factory[src][0].length == 0){
                        System.out.println(factory[dst][0].length);
                        break;
                    }

                    factory[src][0].back.front = factory[dst][0]; // src의 1번 노드를 dst와 연결
                    factory[src][1].front.back = factory[dst][0].back; // src의 마지막 노드를 dst와 연결
                    factory[dst][0].back.front = factory[src][1].front; // dst의 1번 노드를 src의 마지막 노드 뒤에 연결
                    factory[dst][0].back = factory[src][0].back; // dst의 1번 노드 위치에 src의 1번 노드 끼워넣기
                    factory[src][0].back = factory[src][1]; // src 내부 노드 비우기
                    factory[src][1].front = factory[src][0];

                    // 길이 변경
                    factory[dst][0].length += factory[src][0].length;
                    factory[src][0].length = 0;

                    System.out.println(factory[dst][0].length);
                    break;

                case 300: // 맨 앞 선물 교체
                    src = Integer.parseInt(st.nextToken());
                    dst = Integer.parseInt(st.nextToken());

                    if(factory[src][0].length == 0 && factory[dst][0].length == 0){
                        System.out.println(0);
                        break;
                    }
                    Node f1 = factory[src][0].back; // src의 1번 노드
                    Node f2 = factory[dst][0].back; // dst의 1번 노드

                    // src에 선물이 없는 경우
                    if(f1.idx == -1){
                        // dst에서 f2 제거
                        factory[dst][0].back = f2.back;
                        f2.back.front = factory[dst][0];

                        // src에 f2 추가
                        f2.front = factory[src][0]; // f1의 앞 뒤로 src 연결하기
                        f2.back = factory[src][1];
                        factory[src][0].back = f2; // src에 f2 연결
                        factory[src][1].front = f2;

                        // 길이 변경
                        factory[src][0].length = 1;
                        factory[dst][0].length--;

                        System.out.println(factory[dst][0].length);
                        break;
                    }
                    // dst에 선물이 없는 경우
                    else if(f2.idx == -1){
                        // src에서 f1 제거
                        factory[src][0].back = f1.back;
                        f1.back.front = factory[src][0];

                        // dst에 f1 추가
                        f1.front = factory[dst][0]; // f1의 앞 뒤로 dst 연결하기
                        f1.back = factory[dst][1];
                        factory[dst][0].back = f1; // dst에 f1 연결
                        factory[dst][1].front = f1;

                        // 길이 변경
                        factory[dst][0].length = 1;
                        factory[src][0].length--;

                        System.out.println(factory[dst][0].length);
                        break;
                    }
                    // 둘 다 있는 경우
                    else {
                        // 1번 노드 연결
                        factory[dst][0].back = f1;
                        factory[src][0].back = f2;
                        f2.front = factory[src][0];
                        f1.front = factory[dst][0];
                        // 2번째 노드와 1번째 노드 연결
                        f1.back.front = f2;
                        f2.back.front = f1;
                        tmp = f1.back;
                        f1.back = f2.back;
                        f2.back = tmp;

                        System.out.println(factory[dst][0].length);
                    }
                    break;

                case 400: // 물건 나누기
                    src = Integer.parseInt(st.nextToken());
                    dst = Integer.parseInt(st.nextToken());

                    tmpStart = factory[src][0].back; // 나눠줄 노드 첫번째 노드
                    tmpEnd = tmpStart; // 나눠줄 선물 마지막 노드

                    if(factory[src][0].length < 2){
                        System.out.println(factory[dst][0].length);
                        break;
                    }

                    int cnt = factory[src][0].length/2; // 나눌 선물 갯수 = floor(length/2)
                    // 갯수만큼 연결 리스트 이동 탐색
                    for(int c = 1; c < cnt; c++){
                        tmpEnd = tmpEnd.back;
                    }

                    tmpEnd.back.front = factory[src][0]; // 나눔할 선물 뒤 노드를 1번 노드로 변경
                    factory[src][0].back = tmpEnd.back;

                    tmpEnd.back = factory[dst][0].back; // 나눔할 선물 마지막 노드와 dst의 1번 노드 연결
                    factory[dst][0].back = tmpStart; // 나눔할 선물 1번 노드를 dst의 1번 노드로 변경
                    tmpEnd.back.front = tmpEnd; // dst의 기존 1번 노드 앞에 나눔할 선물 마지막 노드 위치
                    tmpStart.front = factory[dst][0]; // 나눔할 선물 1번 노드를 dst의 1번 노드로 변경

                    // 길이 변경
                    factory[dst][0].length+=cnt;
                    factory[src][0].length-=cnt;
                    System.out.println(factory[dst][0].length);
                    break;

                case 500: // 특정 선물 앞, 뒤
                    src = Integer.parseInt(st.nextToken());
                    System.out.println(presents[src].front.idx + (2 * presents[src].back.idx));
                    break;

                case 600: // 공장 라인 맨 앞, 맨 뒤, 선물 갯수
                    src = Integer.parseInt(st.nextToken());
                    System.out.println(factory[src][0].back.idx + (2*factory[src][1].front.idx) + (3*factory[src][0].length));
                    break;

            }
        }
    }


    static class Node { // Linked List 내 Node
        int idx; // 선물 번호
        Node front; // 내 앞 선물
        Node back; // 내 뒤 선물
        int length; // 해당 리스트 길이

        Node(int idx, Node front, Node back) {
            this.idx = idx;
            this.front = front;
            this.back = back;
            this.length = 0;
        }
    }
}
