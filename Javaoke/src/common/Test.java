package common;

public class Test {
    public static void main(String[] args) {
        LyricSentence lyric = new LyricSentence("[00:29.48]I got a fe,e'l;:ing ! # 1 # TREMOLO");
        System.out.println(lyric.date);
        System.out.println(lyric.idSinger);
        System.out.println(lyric.text);
        System.out.println(lyric.type);
    }
}