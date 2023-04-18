public class Film {
    static Film film;
    private final int duration;
    private final String name;
    private final String trailerPath;

    public Film(int duration, String name, String trailerPath) {
        this.duration = duration;
        this.name = name;
        this.trailerPath = trailerPath;
    }

    @Override
    public String toString() {
        return "film\t" +
                name + '\t' +
                trailerPath + '\t' +
                duration + '\t' +
                '\n';
    }

    public int getDuration() {
        return duration;
    }
    public String getName() {
        return name;
    }
    public String getTrailerPath() {
        return trailerPath;
    }
}
