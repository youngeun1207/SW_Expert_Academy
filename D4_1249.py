# [S/W 문제해결 응용] 4일차 - 보급로

from collections import deque

C = int(input())
for test_case in range(1, 1+C):
    N = int(input())

    maps = []
    for _ in range(N):
        maps.append(list(input()))

    ans = 0
    dp = [[float('inf')] * N for _ in range(N)]
    dp[0][0] = 0

    q = deque()
    q.append((0, 0))
    
    print("#{} {}".format(test_case, ans))