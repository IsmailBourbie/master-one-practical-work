# This File Containt testing of Diffrent inout to PCA Algorithm

# getting the libraries
import numpy as np
import matplotlib.pyplot as plt
from pca import pca
import time as t
from sklearn.decomposition import PCA

def x_cube(x):
    return (x**3)/700

result_our = []
result_sklearn = []

s = 40

for i in range(s):
    data = np.random.rand(i*100 + 3,i*100 + 3)
    start = t.time()
    pcc = PCA(2)
    new = pcc.fit_transform(data)
    end = t.time()
    result_sklearn.append(end-start)
    
    start = t.time()
    xx = pca(2,data)
    end = t.time()
    result_our.append(end-start)
    
x = np.linspace(0,s,num= s)
plt.plot(x , result_our , c = 'b' , label = 'Our PCA')
plt.plot(x , result_sklearn , c = 'r', label = 'Sklearn PCA')
plt.plot(x_cube(x) , c = 'g' , label = 'Cube Function')
plt.legend()
plt.xlabel('Size Of Matrix ')
plt.ylabel('Time in Second (S)')
plt.title('Time Taken By Algorithms')
plt.show()

