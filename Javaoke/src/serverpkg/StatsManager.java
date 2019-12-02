package serverpkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import common.Constants.FileReading;

public abstract class StatsManager {

    private final static File file = new File(FileReading.STATS_PATH);
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;
    private static Stats stats;

    public static void main(String[] args) {
        Stats newStats = new Stats();

        file.delete();
        try {
            file.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(file));

            oos.writeObject(newStats);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public static Stats getStats() {
        return stats;
    }

    public static void close() {

        file.delete();
        try {
            file.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(file));

            oos.writeObject(stats);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Stats open() {

        if(!file.exists() || file.isDirectory() || !file.canRead())
            System.err.println("The stats file name is incorrect or the file doesn't exist.");

        try {
            ois = new ObjectInputStream(new FileInputStream(file));

            stats = (Stats) ois.readObject();

            ois.close();
        } catch(IOException | ClassCastException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return stats;
    }

}