package common;


public class Constants {

    public static class Networking {
        public static final int SERVER_LISTEN_PORT = 5678;

        // Les String composant une requete client devront être séparées par cette variable
        public static final String REQUEST_SEPARATOR = ":";

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

}