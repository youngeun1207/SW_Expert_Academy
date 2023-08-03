import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 백준 2252 줄세우기
 * Topological sort
 * @author youngeun
 *
 * 55624 KB
 * 612 ms
 *
 * 나보다 뒤에 서야하는 학생들 모아두기 -> Map<> back
 * 나보다 전에 서야하는 학생 수 저장 -> indgree 배열
 * indgree==0인 학생부터 줄 세우기
 * a 학생이 줄을 서면, b 학생보다 앞에 서야하는 학생 수 한명 줄게된다
 *  -> b의 indgree 줄이기 위해 Map<> back 활용
 * 만약 b의 indgree도 0이 되면 줄 세우고 똑같은 로직 반복
 */
public class LineUp {
    static int N;
    static  int M;
    public static void main(String[] args) throws IOException {
        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        Map<Integer, List<Integer>> back = new HashMap<>();
        int[] indegree = new int[N+1];
        int a, b;
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            a = Integer.parseInt(st.nextToken()); // b보다 앞에 서야 함
            b = Integer.parseInt(st.nextToken()); // a보다 뒤에 서야 함

            back.putIfAbsent(a, new ArrayList<Integer>());
            back.get(a).add(b); // 나보다 뒤에 서야 하는 친구들 저장할거임
            indegree[b]++; // 나보다 전에 서야 하는 애들 수
        }
        Queue<Integer> queue = new LinkedList<>();
        for(int i = 1; i <= N; i++){
            if(indegree[i] == 0){
                queue.offer(i); // 나보다 앞에 서야하는 학생 0명인 경우부터 시작
            }
        }
        // BFS
        int student;
        while (queue.size() > 0){
            student = queue.poll();
            sb.append(student + " "); // 줄 설 수 있어!
            if(back.containsKey(student)){ // NPE 방지
                for(int s: back.get(student)){ // 나보다 뒤에 서야하는 애들 목록
                    indegree[s]--; // 나는 이제 줄을 섰으니까 s 앞에 서야하는 목록에서 빠지므로 indgree--
                    if(indegree[s] == 0){ // s도 앞에 서야하는 애들 다 줄을 섰어
                        queue.offer(s); // BFS 돌려
                    }
                }
            }
        }
        System.out.println(sb.toString());
    }
}
