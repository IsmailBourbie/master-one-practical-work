# This File Containt testing of Diffrent inout to PCA Algorithm

# getting the libraries
import numpy as np
import matplotlib.pyplot as plt
from pca import pca
import time as t

def pcav(number_compontents, data , is_scaled = True):  

    u,s,v = np.linalg.svd(data.T)

    eig_pairs = [(np.abs(u[i]) , s[:,i]) for i in range(len(u))]    

    eig_pairs.sort(key = lambda x : x[0] , reverse= True)  

    final=[]
    for i in range(number_compontents):  
	       final.append(eig_pairs[i][1].reshape(data.shape[1],1))  
	
	    	# Creating the Projection Matrix  
    projection_matrix = np.hstack((final))  
	
	   	 # transforming the data  
    Y = data.dot (projection_matrix)  
	
    return Y  


s = 40
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

def x_cube(x):
    return x**3

plt.plot(x , x_cube(x) , c = 'b')
plt.show()

dd = np.random.rand(40*100,40*100)
start = t.time()
xx = pcav(2,dd)
end = t.time()
end-start
from sklearn.decomposition import PCA
start = t.time()
pcc = PCA(2)
new = pcc.fit_transform(dd)
end = t.time()

end-start