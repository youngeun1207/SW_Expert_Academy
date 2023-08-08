import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 백준 1991 트리 순회
 * @author youngeun
 *
 * 11624 KB
 * 80 ms
 *
 * DFS를 다양하게 변형하는 문제
 * 재귀 대신 stack을 이용한 반복문으로 구현해보았다.
 * 또한 balanced tree가 아니므로 배열 대신 map에 Node 클래스를 저장하여 관리함.
 */
public class TreeTraversal {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        Map<String, Node> tree = new HashMap<>(); // balanced tree가 아니므로 array 대신 Map 쓰기

        int N = Integer.parseInt(br.readLine());
        String root, left, right;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            root = st.nextToken();
            left = st.nextToken();
            right = st.nextToken();

            tree.put(root, new Node(root, left, right));
        }

        // 전위 순회
        StringBuilder sb = new StringBuilder();
        Stack<Node> stack = new Stack<>();
        stack.push(tree.get("A")); // 루트부터..

        while (stack.size() > 0){
            Node node = stack.pop();
            sb.append(node.root);
            if(!node.right.equals(".")){
                stack.push(tree.get(node.right));
            }
            if(!node.left.equals(".")){
                stack.push(tree.get(node.left));
            }
        }
        System.out.println(sb.toString());

        // 중위 순회
        sb = new StringBuilder();
        stack = new Stack<>();
        Node node = tree.get("A");
        // 부모 노드를 스택에 미리 넣으면 안되므로 node가 null이 아니어도 진입 가능하도록 함
        while (stack.size() > 0 || node != null) {
            if (node != null) {
                stack.push(node); // 일단 스택에 넣고
                node = tree.get(node.left); // 왼쪽 자식 있는지 확인
            } else {
                node = stack.pop(); // pop 하는 시점에 print 수행
                sb.append(node.root);
                node = tree.get(node.right); // 오른쪽 자식 다시 스택에 넣기
            }
        }
        System.out.println(sb.toString());

        // 후위 순회
        stack = new Stack<>();
        Stack<String> visited = new Stack<>(); // 방문 순서 저장할 스택
        stack.push(tree.get("A")); // 루트부터..
        while (stack.size() > 0) {
            node = stack.pop();
            visited.push(node.root); // 방문한 배열에 저장(맨 마지막에 출력할 것이므로 스택 사용)
            if (!node.left.equals(".")) {
                stack.push(tree.get(node.left)); // 왼쪽에 자식 있으면 스택에 넣기
            }
            if (!node.right.equals(".")) {
                stack.push(tree.get(node.right)); // 오른쪽에 자식 있으면 스택에 넣기
            }
        }
        sb = new StringBuilder();
        while (visited.size() > 0){ // 방문 순서를 역순으로 출력
            sb.append(visited.pop());
        }
        System.out.println(sb.toString());
    }
    static class Node{
        String root;
        String left;
        String right;

        public Node(String root, String left, String right) {
            this.root = root;
            this.left = left;
            this.right = right;
        }
    }
}
