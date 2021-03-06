package common;


public class Constants {

    public static class Networking {
        public static final int SERVER_LISTEN_PORT = 5678;

        // Les String composant une requete client devront être séparées par cette variable
        public static final String REQUEST_SEPARATOR = ":";

        public static final int STREAM_ARRAY_LENGTH = 4096;

        // Enumération représentant les types de requete transitant entre serveur et client
        public enum RequestType {
            // Les requêtes pour avoir toute la liste des musiques stockées
            REQ_LIST("Request-Full-Music-List"),
            // Les requêtes pour avoir toute le fichier d'une seule musique
            REQ_MUSIC("Request-Music-Class");

            RequestType(String text) {
                reqText = text;
            }

            // texte qui doit être transmi dans une requete
            private String reqText;

            // getter de reqText
            public String getText() { return reqText; }

            @Override
            public String toString() {
                return reqText;
            }

            // Obtention du type d'énumération en fonction d'un texte de requete
            public static RequestType parseRequestStr(String req) {
                String[] reducedReq = req.split(REQUEST_SEPARATOR);

                for(RequestType r : RequestType.values())
                    if(r.reqText.equals(reducedReq[0]))
                        return r;
                        
                return null;
            }
        }
    }

    public static class CLI {
        public static final String ARG_2_MUSIC_LIST = "--list";
        public static final String ARG_2_MUSIC_FILE = "--play";
        public static final String ARG_OPTION_SPEED = "--speed";
        public static final String ARG_OPTION_PITCH = "--pitch";
        public static final String ARG_OPTION_SINGER = "--singers";

        public static final String SERV_STOP_ARG = "stop";

        public static final String SERV_OUT = "[Server] ";
        public static final String HANDL_OUT = "[Request Handler] ";
        public static final String CLIENTK_OUT = "[Client Karaoke] ";
        public static final String CLIENTL_OUT = "[Client List] ";
        public static final String CLIENT_OUT = "[Client] ";
    }

    public static class FileReading {
        public static final String RSRC_PATH = "files/";
        public static final String EXT_LYRICS = ".lrc";
        public static final String EXT_MIDI = ".mid";
        public static final String LRC_REGEX = "\\[([0-9]{2}\\:[0-9]{2}\\.[0-9]{2})?\\]([\\w!?‚,;:.$&'′\"\\-\\(\\)\\s]+)#(\\s*\\w*\\s*)#(\\s*\\w*\\s*)";
        
        public static final String STATS_PATH = "stats/stats.statson";
    }

    public static class GUI {
        public static final String BACKGROUND_PATH = "img/java.png";
        public static final String IMG_PATH = "img/";
        public static final String FONT_NAME = "Arial";
        public static final int FONT_SIZE = 30;
        public static final int WINDOW_W = 1040; //800
        public static final int WINDOW_H = 540;
    }


}