package serverpkg;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Stats implements Serializable {

    private static final long serialVersionUID = -4879098827817165092L;

    private Map<String, Integer> userRequest;
    private Map<String, Integer> musicRequest;

    public Stats() {
        userRequest = new HashMap<String, Integer>();
        musicRequest = new HashMap<String, Integer>();
    }

    private static synchronized void addOne(Map<String, Integer> request, String key) {
        request.putIfAbsent(key, 0);
        request.computeIfPresent(key, (name, value) -> value + 1);
    }

    private static synchronized String getMax(Map<String, Integer> request) {
        if(request.isEmpty())
            return "None";
        return Collections.max(request.entrySet(), (r1, r2) -> { return r1.getValue() - r2.getValue(); }).getKey();
    }

    public void addOneToUser(String userName) {
        addOne(userRequest, userName);
    }

    public void addOneToMusic(String musicName) {
        addOne(musicRequest, musicName);
    }

    public String getBestMusic() {
        return getMax(musicRequest);
    }

    public String getBestClient() {
        return getMax(userRequest);
    }

}