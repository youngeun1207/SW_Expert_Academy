import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Robot {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());

        for(int t = 1; t <= testCase; t++){
            int n = Integer.parseInt(br.readLine());
            String[][] field = new String[n][n];

            for(int i = 0; i < n; i++) {
                String s = br.readLine();
                field[i] = s.split(" ");
            }
            int moves = 0;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++){
                    switch (field[i][j]){
                        case ("C"):
                            for(int c = i+1; c < n; c++){
                                if(field[c][j].equals("S")){
                                    moves++;
                                } else {
                                    break;
                                }
                            }
                            for(int c = i-1; c >= 0; c--){
                                if(field[c][j].equals("S")){
                                    moves++;
                                } else {
                                    break;
                                }
                            }
                        case("B"):
                            for(int b = j-1; b >= 0; b--){
                                if(field[i][b].equals("S")){
                                    moves++;
                                } else {
                                    break;
                                }
                            }
                        case("A"):
                            for(int a = j+1; a < n; a++){
                                if(field[i][a].equals("S")){
                                    moves++;
                                } else {
                                    break;
                                }
                            }
                            break;
                    }
                }
            }
            System.out.println("#" + t + " " + moves);
        }
    }
}
