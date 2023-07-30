import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Move {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // TC
        int testCase = Integer.parseInt(br.readLine());

        for (int t = 1; t <= testCase; t++){
            StringTokenizer st = new StringTokenizer(br.readLine());
            // 공간 크기
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            // 참가자 수
            int n = Integer.parseInt(st.nextToken());

            int[][] space = new int[x][y];
            for(int i = 0; i < x; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < y; j++){
                    space[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            int money = 0;
            for(int i = 0; i < n; i++){
                st = new StringTokenizer(br.readLine());
                Person player = new Person(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken()));
                player.setDirAndDist(space[player.row][player.col]);
                for(int j = 0; j < player.cnt; j++){
                    switch (player.dir){
                        case(1): // 위
                            player.row -= player.dist;
                            break;
                        case(2): // 오른쪽 위
                            player.col += player.dist;
                            player.row -= player.dist;
                            break;
                        case(3): // 오른쪽
                            player.col += player.dist;
                            break;
                        case(4): // 오른쪽 아래
                            player.col += player.dist;
                            player.row += player.dist;
                            break;
                        case(5): // 아래
                            player.row += player.dist;
                            break;
                        case(6): // 왼쪽 아래
                            player.col -= player.dist;
                            player.row += player.dist;
                            break;
                        case(7): // 왼쪽
                            player.col -= player.dist;
                            break;
                        case(8): // 왼쪽 위
                            player.col -= player.dist;
                            player.row -= player.dist;
                            break;
                    }
                    if(player.col < 0 || player.col >= y || player.row < 0 || player.row >= x){
                        player.col = -1;
                        player.row = -1;
                        break;
                    }
                    player.setDirAndDist(space[player.row][player.col]);
                }
                if(player.col == -1 && player.row == -1){
                    money -= 1000;
                }else{
                    money += space[player.row][player.col] * 100 - 1000;
                }
            }
            System.out.println("#" + t + " " + money);
        }
    }
}
class Person{
    int row;
    int col;
    int cnt;
    int dir;
    int dist;

    public Person(int row, int col, int cnt) {
        this.row = row;
        this.col = col;
        this.cnt = cnt;
    }
    void setDirAndDist(int coordinate){
        this.dir = coordinate / 10;
        this.dist = coordinate % 10;
    }
}
