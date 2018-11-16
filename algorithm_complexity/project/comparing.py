
#getting The libraries
from pca import pca
import numpy as np
import matplotlib.pyplot as plt
import time as t
from sklearn.decomposition import PCA

example = [100,500,1000,1500,5000,7500,10000]
features = [20,30,50,100]
compontest = [2,4,8,12,16,32,64]
time_one = []
time_two = []

for i in compontest:
    data = np.random.rand(5000,100)
    start = t.time()
    pcaa = PCA(i)
    new_one = pcaa.fit_transform(data)
    end = t.time()
    time_one.append(end-start)
            
    start = t.time()
    new_two = pca(i,data)
    end = t.time()
    time_two.append(end-start)
    if new_one.all() == new_two.all():
        print(True)

plt.plot(compontest,time_one,c ='b',label = 'Sklearn PCA')
plt.plot(compontest,time_two,c ='r', label = 'Our PCA')
plt.xlabel('Number Of Components')
plt.ylabel('Time in Second')
plt.title('Time Taken for (5000,100) dataset')
plt.legend()
plt.show()