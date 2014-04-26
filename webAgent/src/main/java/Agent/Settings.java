package Agent;

/**
 * Created with IntelliJ IDEA.
 * User: Игорь
 * Date: 4/19/14
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class Settings {
    public String language;
    public int voice;
    public int speed;

    public Settings(String language, int voice, int speed) {
        this.language = language;
        this.voice = voice;
        this.speed = speed;
    }

    public Settings() {
        this.language = "US+English";
        this.voice = 9;
        this.speed = 0;
    }
}
