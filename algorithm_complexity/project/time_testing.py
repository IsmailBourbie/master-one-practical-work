# This File Containt testing of Diffrent inout to PCA Algorithm

# getting the libraries
import numpy as np
import matplotlib.pyplot as plt
from pca import pca
import time as t

s = 20
result = []
for i in range(s):
    data = np.random.rand(i*100,i*100)
    start = t.time()
    new = pca(i,data)
    end = t.time()
    result.append(end-start)
    
x = np.linspace(0,s,num=s)
plt.plot(x , result , c = 'b')
plt.show()