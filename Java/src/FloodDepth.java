public class FloodDepth {
    /**
     * https://app.codility.com/programmers/trainings/1/flood_depth/
     * DP 활용
     맨 앞을 0으로 두고(끝이라 물 안고임)
     다음 칸이 현재 칸보다 낮으면 낮은 깊이만큼 물 높이 추가
     이걸 앞 뒤로 반복해서 더 얕은 물 높이로 설정
     -> 시간복잡도 O(N)
     */

    class Solution {
        public int solution(int[] A) {
            int len = A.length;
            // 좌측에서 우측으로
            int[] left_dp = new int[len];
            // 우측에서 좌측으로
            int[] right_dp = new int[len];

            left_dp[0] = 0;
            right_dp[len-1] = 0;
            // 양쪽으로 물 높이 계산
            for(int i = 1; i < len; i++){
                left_dp[i] = Math.max(0, left_dp[i-1]+(A[i-1]-A[i]));
                right_dp[len-i-1] = Math.max(0, right_dp[len-i]+(A[len-i]-A[len-i-1]));
            }
            // 그 중 더 얕은 쪽으로 선택
            for(int i = 0; i < len; i++){
                left_dp[i] = Math.min(left_dp[i], right_dp[i]);
            }
            return getMax(left_dp);
        }
        public int getMax(int[] dp){
            int len = dp.length;
            int ans = 0;
            for(int i = 0; i < len; i++){
                ans = Math.max(ans, dp[i]);
            }
            return ans;
        }
    }
}
