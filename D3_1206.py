# [S/W 문제해결 기본] 1일차 - View
T = 10
for test_case in range(1, T+1):
    n = int(input())
    building = list(map(int, input().split()))
    count = 0
    dir = [-1, -2, 1, 2]
    for i in range(2, n-2):
        # 양 옆 빌딩들의 높이
        side_building = []
        for j in dir:
            side_building.append(building[i+j])
        # 양 옆중 한곳이라도 큰 경우
        if max(side_building) > building[i]:
            continue
        # 그렇지 않은 경우
        count += building[i] - max(side_building)
    print(f"#{test_case} {count}")