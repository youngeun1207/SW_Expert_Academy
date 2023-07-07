# 파리퇴치3
T = int(input())

for test_case in range(1, T + 1):
    N, M = map(int, input().split())
    fly = []
    for _ in range(N):
        fly.append(list(map(int, input().split())))
        
    def get_fly_cnt(now_col, now_row, dirs):
        cnt = fly[now_col][now_row]
        for m in range(1, M):
            for way_col, way_row in dirs:
                way_col = now_col+(way_col*m)
                way_row = now_row+(way_row*m)
                if 0 <= way_row < N  and 0 <= way_col < N:
                    cnt+=fly[way_col][way_row]
        return cnt 

    res = 0
    cross = [(-1,-1), (1, 1), (-1, 1), (1, -1)]
    right = [(1, 0), (-1, 0), (0, -1), (0, 1)]
    
    for col in range(N):
    	for row in range(N):
            res = max(res, get_fly_cnt(col, row, cross), get_fly_cnt(col, row, right))
    print('#{} {}'.format(test_case, res))