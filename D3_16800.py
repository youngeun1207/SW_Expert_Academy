# 구구단 걷기
import math
test_case = int(input())

for i in range(1, test_case+1):
    n = int(input())
    divisor=[]
    for j in range(1, int(math.sqrt(n))+1):
        if n % j == 0:
            divisor.append(j)
    ans = (max(divisor)-1)+(n // max(divisor)-1)
    print("#{} {}".format(i, ans))