package serverpkg;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Stats implements Serializable {

    private static final long serialVersionUID = -4879098827817165092L;

    private Map<String, AtomicInteger> userRequest;
    private Map<String, AtomicInteger> musicRequest;

    public Stats() {
        userRequest = new HashMap<String, AtomicInteger>();
        musicRequest = new HashMap<String, AtomicInteger>();
    }

    private synchronized void addOne(Map<String, AtomicInteger> request, String key) {
        request.putIfAbsent(key, new AtomicInteger(0));
        request.computeIfPresent(key, (name, value) -> {
            value.addAndGet(1);
            return value;
        });
    }

    private synchronized String getMax(Map<String, AtomicInteger> request) {
        return Collections.max(request.entrySet(), (r1, r2) -> { return r1.getValue().get() - r2.getValue().get(); }).getKey();
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