# 프로그래머스 Lv.3 등굣길
# 갈 수 있는 길 계산하기
# 확통 활용
def solution(m, n, puddles):
    dp = [[0 for i in range(m)] for j in range(n)]

    for i in puddles:
        dp[i[1] - 1][i[0] - 1] = 'X'

    for i in range(m):
        if dp[0][i] != 'X':
            dp[0][i] = 1
        else:
            break

    for i in range(n):
        if dp[i][0] != 'X':
            dp[i][0] = 1
        else:
            break

    for i in range(1, n):
        for j in range(1, m):
            if dp[i][j] != "X":
                if dp[i - 1][j] == 'X':
                    dp[i - 1][j] = 0
                if dp[i][j - 1] == 'X':
                    dp[i][j - 1] = 0
                dp[i][j] = (dp[i - 1][j] + dp[i][j - 1]) % 1000000007

    return dp[n - 1][m - 1]
