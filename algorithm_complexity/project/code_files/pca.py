# The File Contains Algorithm For PCA

import numpy as np
    
def pca(number_compontents, data):
    if not 0 <= number_compontents <= data.shape[1]:
        raise ValueError('The number of features are less than the number of components')

    # calculate the covariance matrix
    cov_matrix = np.cov(data.T)
    
    # calculate the eigen things
    eig_vals , eig_vecs = np.linalg.eigh(cov_matrix)
    
    # codes are the same
    eig_pairs = [(np.abs(eig_vals[i]) , eig_vecs[:,i]) for i in range(len(eig_vals))]

    # Sorting All of Them
    eig_pairs.sort(key = lambda x : x[0] , reverse= True)
    
    # Getting the selected vector in a form of matrix
    final = [eig_pairs[i][1].reshape(data.shape[1],1) for i in range(number_compontents)]
    
    # Creating the Projection Matrix, multiplying by -identity is an addons
    projection_matrix = np.hstack((final))

    # transforming the data
    Y = data.dot(projection_matrix)
    
    return Y
    
        
        

