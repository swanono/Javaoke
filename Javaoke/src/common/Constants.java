package common;

public class Constants {

    public class Networking {
        public static final int SERVER_LISTEN_PORT = 5678;
    }

    public static class FileReading {
        public static final String RSRC_LYRICS = "../../files/lrc/";
        public static final String RSRC_MIDI = "../../files/midi/";
        public static final String EXT_LYRICS = ".lrc";
        public static final String EXT_MIDI = ".mid";
        public static final String LRC_REGEX = "\\[([0-9]{2}\\:[0-9]{2}\\.[0-9]{2})?\\]([\\w:\\s]+)";
        
    }


}