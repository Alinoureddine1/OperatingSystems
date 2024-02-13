package main;
import master.Master;
import worker.Worker;

public class Main {
    public static void main(String[] args) {
        Master master = new Master();
        master.start();
    }


}
