# 숫자 배열 회전

T = int(input())
for test_case in range(1, T + 1):
    N = int(input())
    m_0 = []
    for _ in range(N):
        m_0.append(input().split())
    def turn(matrix):
        m = []
        for i in range(N):
            row = []
            for j in range(N):
                row.append(matrix[N-j-1][i])
            m.append(row)
        return m

    # 90도
    m_90 = turn(m_0)
    # 180도
    m_180 = turn(m_90)
    # 270도
    m_270 = turn(m_180)

    # 출력
    print('#'+str(test_case))
    for i in range(N):
        print(''.join(m_90[i]), ''.join(m_180[i]), ''.join(m_270[i]))