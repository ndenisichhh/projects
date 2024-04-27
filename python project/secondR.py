from random import randint as rd
import os
import time

a = [[rd(0,3) for x in range(7)] for i in range(11)]
for i in a:
    print(i)
k=1

def top(i, j):
    global k, a
    while  (i != 0) and (a[i][j] == a[i-1][j]) and (a[i][j] != ''):
        k+=1
        i-=1
        if a[i][j+1] == a[i][j]:
            right(i, j+1)
        if a[i][j-1] == a[i][j]:
            left(i, j-1)
  
def bottom(i, j):
    global k, a
    while  (i+1 != len(a)) and (a[i][j] == a[i+1][j]) and (a[i][j] != ''):
        k+=1
        i+=1
        if a[i][j+1] == a[i][j]:
            right(i, j+1)
        if a[i][j-1] == a[i][j]:
            left(i, j-1)
 
def right(i, j):
    global k, a
    while (j+1 != len(a[0])) and (a[i][j] == a[i][j+1]) and (a[i][j] != ''):
        k+=1
        j+=1
        if a[i+1][j] == a[i][j]:
            bottom(i+1, j)
        if a[i-1][j] == a[i][j]:
            top(i-1, j)
 
def left(i, j):
    global k, a
    while (j!= 0) and (a[i][j] == a[i][j-1]) and (a[i][j] != ''):
        k+=1
        j-=1
        if a[i+1][j] == a[i][j]:
            bottom(i+1, j)
        if a[i-1][j] == a[i][j]:
            top(i-1, j)



def changeTop(i, j):
    global a, k
    while  (i != 0) and (a[i][j] == a[i-1][j]) and (a[i][j] != ''):
        if j+1 != len(a[0]) and a[i-1][j+1] == a[i-1][j]:
            changeRight(i-1, j+1)
        if j != 0 and a[i-1][j-1] == a[i-1][j]:
            changeLeft(i-1, j-1)
        a[i][j] = ''
        try:
            if i-1 != 0:
                if (a[i-1][j+1] != a[i-1][j]) and (a[i-2][j] != a[i-1][j]) and (a[i-1][j-1] != a[i-1][j]):
                    a[i-1][j] = ''
                    break
            else:
                if (a[i-1][j+1] != a[i-1][j]) and (a[i-1][j-1] != a[i-1][j]):
                    a[i-1][j] = ''
                    break
        except:
            a[i-1][j] = ''
            break
        i-=1
            
def changeBottom(i, j):
    global a, k
    while  (i+1 != len(a)) and (a[i][j] == a[i+1][j]) and (a[i][j] != ''):
        if j+1 != len(a[0]) and a[i+1][j+1] == a[i+1][j]:
            changeRight(i+1, j+1)
        if (j != 0) and a[i+1][j-1] == a[i+1][j]:
            changeLeft(i+1, j-1)
        a[i][j] = ''
        try:
            if i+2 != len(a):
                if (a[i+1][j+1] != a[i+1][j]) and (a[i+2][j] != a[i+1][j]) and (a[i+1][j-1] != a[i+1][j]):
                    a[i+1][j] = ''
                    break
            else:
                if (a[i+1][j+1] != a[i+1][j]) and (a[i+1][j-1] != a[i+1][j]):
                    a[i+1][j] = ''
                    break
        except:
            a[i+1][j] = ''
            break
        i+=1
    
def changeRight(i, j):
    global a, k
    while (j+1 != len(a[0])) and (a[i][j] == a[i][j+1]) and (a[i][j] != ''):
        if (i+1 != len(a)) and a[i+1][j+1] == a[i][j+1]:
            changeBottom(i+1, j+1)
        if (i != 0) and a[i-1][j+1] == a[i][j+1]:
            changeTop(i-1, j+1)
        a[i][j] = ''
        try:
            if j+2 != len(a[0]):
                if a[i+1][j+1] != a[i][j+1] and a[i-1][j+1] != a[i][j+1] and a[i][j+2] != a[i][j+1]:
                    a[i][j+1] = ''
                    break
            else:
                if a[i+1][j+1] != a[i][j+1] and a[i-1][j+1] != a[i][j+1]:
                    a[i][j+1] = ''
                    break
        except:
            a[i][j+1] = ''
            break
        j+=1

def changeLeft(i, j):
    global a, k
    while (j != 0) and (a[i][j] == a[i][j-1]) and (a[i][j] != ''):
        if (i+1 != len(a)) and a[i+1][j-1] == a[i][j-1]:
            changeBottom(i+1, j-1)
        if (i != 0) and a[i-1][j-1] == a[i][j-1]:
            changeTop(i-1, j-1)
        a[i][j] = ''
        try:
            if j-1 != 0:
                if a[i+1][j-1] != a[i][j-1] and a[i-1][j-1] != a[i][j-1] and a[i][j-2] != a[i][j-1]:
                    a[i][j-1] = ''
                    break
            else:
                if a[i+1][j-1] != a[i][j-1] and a[i-1][j-1] != a[i][j-1]:
                    a[i][j-1] = ''
                    break
        except:
            a[i][j-1] = ''
            break
        j-=1

for i in range(len(a)):
    for j in range(len(a[0])):
        k = 1
        changeTop(i, j)
        changeBottom(i, j)
        changeRight(i, j)
        changeLeft(i, j)
        print(f'i: {i}, j: {j}, k: {k}')
# for i in a:
#     print(i)
# def check():
#  if k>=4:
  

# for i in range(len(a)):
# for j in range(len(a[0])):