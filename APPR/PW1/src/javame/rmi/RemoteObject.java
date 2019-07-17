package javame.rmi;

class RemoteObject implements RemoteInterface {

    @Override
    public void printMessage() {
        System.out.println("Hello world with RMI");
    }
}