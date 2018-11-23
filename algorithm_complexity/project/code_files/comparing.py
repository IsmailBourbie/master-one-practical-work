
#getting The libraries
from pca import pca
import numpy as np
import matplotlib.pyplot as plt
import time as t
from sklearn.decomposition import PCA

example = [100,500,1000]
features = [20,30,50,100]
compontest = [2,4,8,12,16,32,64]
time_one = []
time_two = []



for i in example:
    data = np.random.rand(i,i)
    start = t.time()
    pcaa = PCA(5)
    new_one = pcaa.fit_transform(data)
    end = t.time()
    time_one.append(end-start)
            
    start = t.time()
    new_two = pca(5,data)
    end = t.time()
    time_two.append(end-start)
    if np.all(new_one) == np.all(new_two):
        print('True')
    

plt.plot(time_one,c ='b',label = 'Sklearn PCA')
plt.plot(time_two,c ='r', label = 'Our PCA')
plt.xlabel('Number Of Components')
plt.ylabel('Time in Second')
plt.title('Time Taken for (5000,100) dataset')
plt.legend()
plt.show()