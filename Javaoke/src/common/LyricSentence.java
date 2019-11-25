package common;

import java.util.regex.Pattern;
import java.io.Serializable;
import java.util.regex.Matcher;

public class LyricSentence implements Serializable {

    private static final long serialVersionUID = 8013251675825431155L;

    private long date; // millisecond
    private int idSinger;
    private LyricType type;
    private String text;

    public LyricSentence(String line) {
        try {
            // A line is parsed like that : [mm:ss.xx] lyric # idSinger # type
            // Regex used : \[([0-9]{2}\:[0-9]{2}\.[0-9]{2})?\]([\w:\s]+)
        
            Pattern p = Pattern.compile(Constants.FileReading.LRC_REGEX);  // Pattern 
            Matcher m = p.matcher(line);  
            if(!m.find())
                throw new IllegalArgumentException("No matches for this regex"); 


            // Date management :
            
            String notParsedDate = m.group(1);
            
            String[] parsedDate = notParsedDate.split(":");
            String[] parsedSecAndCent = parsedDate[1].split("\\.");

            // date = mm + ss + xx (convert as millisecond)
            date = Long.parseLong(parsedDate[0]) * 60 * 1000 + Long.parseLong(parsedSecAndCent[0]) * 1000 + Long.parseLong(parsedSecAndCent[1]) * 10;


            // Lyric management :

            String parsedLyric = m.group(2);
            String parsedIdSinger = m.group(3);
            String parsedType = m.group(4);

            text = parsedLyric;
            idSinger = Integer.parseInt(parsedIdSinger.trim());
            type = LyricType.valueOf(parsedType.trim().toUpperCase());

        }
        catch (IllegalArgumentException e ) {
            e.printStackTrace();
        }

    }

    public String getText() {
        return text;
    }

    public int getIdSinger() {
        return idSinger;
    }

    public String getType() {
        return type.toString();
    }

    public long getDate() {
        return date;
    }
}