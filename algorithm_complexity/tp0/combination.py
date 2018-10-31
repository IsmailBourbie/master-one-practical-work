import time as t
import matplotlib.pyplot as plt


def factorial(number):
    if number == 0:
        return 1
    return number * factorial(number - 1)


def plot_result(values=None, result=None):
    values = values
    results = result
    plt.plot(values, results, c='r')
    plt.xlabel("n Values (Integers)")
    plt.ylabel("Time Spent (Seconds)")
    plt.show()


def calculate_time(n):
    """
    This function calculate the time taken for a given n.
    :param n: the number you want to test with.
    :return: the time spent to make the combination with 14.
    """
    start = t.time()
    result = combination(24, n)
    end = t.time()
    total = end - start
    print("The Result of combination of n = {} and k = 14 is {}".format(n, result))
    print("The Time taken for n = {} and k = 14 is {}".format(n, total))
    print("-------------------------------------------------------")
    return total


def calculate_time_factorial(n):
    """
    This function calculate the time taken for a given n.
    :param n: the number you want to test with.
    :return: the time spent to make the combination with 14.
    """
    start = t.time()
    result = combination_fast(n, 14)
    end = t.time()
    total = end - start
    print("The Result of combination of n = {} and k = 14 is {}".format(n, result))
    print("The Time taken for n = {} and k = 14 is {}".format(n, total))
    print("-------------------------------------------------------")
    return total


def combination_fast(n, k):
    return factorial(n) / (factorial(n - k) * factorial(k))


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
    plot_result(values, results)


# starting the experimentation.
# calculate_time_factorial(43)

main()
# plotting saved data
