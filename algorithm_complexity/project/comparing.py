
#getting The libraries
from pca import pca
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import time as t
from sklearn.decomposition import PCA

example = [100,500,1000,1500,5000,7500,10000]
features = [20,30,50,100]
compontest = [2,4,8,12,16]
time_one = []
time_two = []

for i in example:
    
    for j in features:
        
        for k in compontest:
            # generating data with numpy
            data = np.random.rand(i,j)
            start = t.time()
            pcaa = PCA(k)
            new = pcaa.fit_transform(data)
            end = t.time()
            print('Time Taken By Sklearn PCA For {} Componets, {} feature and {} row is {}'.format(k,j,i,end-start))
            time_one.append(end-start)
        
            start = t.time()
            new = pca(k,data)
            end = t.time()
            print('Time Taken By Our PCA For {} Componets, {} feature and {} row is {}'.format(k,j,i,end-start))
            time_two.append(end-start)

            print('------------------------------------------------------------------------------------')


plt.plot(time_one,c ='b')
plt.plot(time_two,c ='r')
plt.show()