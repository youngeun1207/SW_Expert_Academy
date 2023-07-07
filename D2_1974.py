# 스도쿠 검증

T = int(input())
for test_case in range(1, T + 1):
    sdoku = []
    for _ in range(9):
        sdoku.append(list(map(int, input().split())))
    r_sdoku = list(zip( *sdoku ))
    
    def test():
        # 가로 검증
        for row in sdoku:
            if(len(set(row))!= 9):
                return 0

        # 세로 검증
        for col in r_sdoku:
            if(len(set(col))!= 9):
                return 0

        # 작은 칸 검증
        # 기준점 -> *3 해준 값이 작은 칸의 가장 왼쪽 윗 칸 인덱스
            # 00 01 02
            # 10 11 12
            # 20 21 22
        # 기준점으로부터 세로+2, 가로+2 까지 검증
        
        origins = [(0,0), (0,1), (0,2), (1,0), (1,1), (1,2), (2,0), (2,1), (2,2)]
        for origin in origins:
            nums = []
            origin_y, origin_x = origin[0]*3, origin[1]*3
            for way_x, way_y in origins:
                nums.append(sdoku[origin_y+way_y][origin_x+way_x])
            if(len(set(nums)) != 9):
                return 0
        return 1
            
    print('#{} {}'.format(test_case, test()))
            
    
