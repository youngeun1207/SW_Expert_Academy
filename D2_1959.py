# 두 개의 숫자열

T = int(input())

for test_case in range(1, T + 1):
    N, M = map(int, input().split())
    n_list = []
    A = list(map(int, input().split()))
    B = list(map(int, input().split()))

    if N > M:
        padding = N - M
        longger = A
        shorter = B
    else:
        padding = M - N
        longger = B
        shorter = A
    res = 0
    for i in range(padding+1):
        tmp = 0
        for j in range(len(shorter)):
            tmp += (shorter[j]*longger[i+j])
        res = max(res, tmp)
    print('#{} {}'.format(test_case, res))
