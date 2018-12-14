import time as t
import matplotlib.pyplot as plt


def plot_result(values=None, result=None):
    values = values
    results = result
    plt.plot(values, results, c='r')
    plt.xlabel("K Values (Integers)")
    plt.ylabel("Time Spent (Seconds)")
    plt.show()


def calculate_time(k):
    """
    This function calculate the time taken for a given n.
    :param k: the number you want to test with.
    :return: the time spent to make the combination with 14.
    """
    start = t.time()
    result = combination(24, k)
    end = t.time()
    total = end - start
    print("The Result of combination of k = {} and n = 14 is {}".format(k, result))
    print("The Time taken for k = {} and n = 14 is {}".format(k, total))
    print("-------------------------------------------------------")
    return total


def combination(n, k):
    """
    Function That calculate the combination of two numbers.
    :param n: The N number of combination.
    :param k: The K number of combination.
    :return: the result of combination of two number k and n.
    """
    if n < k:
        print("Error : n < k")
        return None
    elif k == 0 or k == n:
        return 1
    return combination(n - 1, k - 1) + combination(n - 1, k)


def main():
    """
    function that do the ain work.
    :return: just nothing.
    """
    values = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24]
    results = []
    for i in values:
        results.append(calculate_time(i))

    # plotting data data
    plot_result(values, results)


# starting the experimentation.
main()
