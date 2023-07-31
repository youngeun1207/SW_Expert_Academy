import java.util.Scanner;

/**
 * BJ 17478
 * 재귀함수가 뭔가요?
 * @author youngeun
 *
 * 18408 KB
 * 344 ms
 *
 * 재귀함수 문제
 *
 * 지정된 횟수 1씩 줄여가면서 재귀
 * 만약 0이 되면 대답 멘트("재귀함수는 자기 자신을 호출하는 함수라네")
 * 재귀함수 뒷부분에 "라고 답변하였지." 배치해야 재귀 빠져나오면서 순차적으로 클로징 멘트 출력
 */
public class WhatIsRecursiveFunction {
    static int n;
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        System.out.println("어느 한 컴퓨터공학과 학생이 유명한 교수님을 찾아가 물었다.");
        recur(n);

    }
    static void recur(int cnt) {
        if(cnt == 0) {
            printIndent(cnt);
            System.out.println("\"재귀함수가 뭔가요?\"");
            printIndent(cnt);
            System.out.println("\"재귀함수는 자기 자신을 호출하는 함수라네\"");
        }else {
            printIndent(cnt);
            System.out.println("\"재귀함수가 뭔가요?\"");
            printIndent(cnt);
            System.out.println("\"잘 들어보게. 옛날옛날 한 산 꼭대기에 이세상 모든 지식을 통달한 선인이 있었어.");
            printIndent(cnt);
            System.out.println("마을 사람들은 모두 그 선인에게 수많은 질문을 했고, 모두 지혜롭게 대답해 주었지.");
            printIndent(cnt);
            System.out.println("그의 답은 대부분 옳았다고 하네. 그런데 어느 날, 그 선인에게 한 선비가 찾아와서 물었어.\"");
            recur(cnt-1);
        }
        printIndent(cnt);
        System.out.println("라고 답변하였지.");
        return;
    }
    static void printIndent(int cnt) {
        for(int i = 0; i < n-cnt ; i++) {
            System.out.print("____");
        }
    }
}