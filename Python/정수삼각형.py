# 프로그래머스 Lv.3 정수삼각형
# DP 활용 문제
# 층 별로 더 큰 쪽과 합산하기
# 단, 양 끝은 무조건 오른쪽 위 or 왼쪽 위
def solution(triangle):
    answer = triangle[0][0]
    if len(triangle) == 2:
        answer += max(triangle[1])
    else:
        l = len(triangle)
        dp = []
        dp.append(triangle[0])
        for i in range(1, l):  # 층 수, level = i+1
            dp.append([0] * (i + 1))
            for j in range(i + 1):  # 층 별 숫자 (층 수 + 1)
                if j == 0:  # 맨 앞 -> 무조건 오른쪽 위(j)
                    dp[i][j] = dp[i - 1][j] + triangle[i][j]
                elif j == i:  # 맨 끝 -> 무조건 왼쪽 위(j-1)
                    dp[i][j] = dp[i - 1][j - 1] + triangle[i][j]
                else:  # 오른쪽 위 vs 왼쪽 위 중 더 큰 쪽이랑 합산
                    dp[i][j] = max(dp[i - 1][j - 1], dp[i - 1][j]) + triangle[i][j]

        answer = max(dp[-1])
    return answer