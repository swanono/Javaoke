package common;

import java.util.regex.Matcher;

public class LyricSentence {

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

            // Date management :
            
            String notParsedDate = m.group(1);
            String notParsedLyric = m.group(2);
            
            String[] parsedDate = notParsedDate.split(":");
            String[] parsedSecAndCent = parsedDate[1].split(".");

            // date = mm + ss + xx (convert as millisecond)
            date = Long.parseLong(parsedDate[0]) * 60 * 1000 + Long.parseLong(parsedSecAndCent[0]) * 1000 + Long.parseLong(parsedSecAndCent[1]) * 10;

            // Lyric management :

            String[] parsedLyric = notParsedLyric.split("#");

            text = parsedLyric[0];
            idSinger = Integer.parseInt(parsedLyric[1].trim());
            type = LyricType.valueOf(parsedLyric[2].trim().toUpperCase());
        }
        catch (PatternSyntaxException | NumberFormatException | IllegalArgumentException e ) {
            e.printStackTrace();
        }

    }
}