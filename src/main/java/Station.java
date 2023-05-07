public class Station {

    private String line;
    private String name;
    private String hasConnection;
    private String date="";
    private String depth="";
    public boolean getHasConnection() {
        if (hasConnection.isEmpty()){
            return false;
        }
        return true;
    }

    public String getDate() {
        return date;
    }

    public String getDepth() {
        return depth;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void setDepth(String depth) {
        this.depth = depth;
    }

    public Station(String name, String line,String hasConnection)
    {
        this.name = name;
        this.line = line;
        this.hasConnection = hasConnection;
    }

    public String getLine()
    {
        return line;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
