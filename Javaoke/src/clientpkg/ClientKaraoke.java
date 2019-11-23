package clientpkg;

import common.Song;

public class ClientKaraoke extends Client {

    String musicTitle;
    Song music;

    public ClientKaraoke(String musicTitle) {
        this.musicTitle = musicTitle;
    }

    @Override
    public void sendRequest() {
        // TODO Auto-generated method stub
    }

    @Override
    public void activate() {
        // TODO Auto-generated method stub

    }

}