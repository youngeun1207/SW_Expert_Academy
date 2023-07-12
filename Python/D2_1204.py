# SWEA
# [S/W 문제해결 기본] 1일차 - 최빈수 구하기
from collections import Counter

T = int(input())
for test_case in range(1, T + 1):
    test_case = int(input())
    nums = list(map(int, input().split()))
    print('#{} {}'.format(test_case, Counter(nums).most_common(1)[0][0]))

