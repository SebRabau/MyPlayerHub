package arwith.myplayerhub.Overwatch;

public class Games
{
    private int won;

    private int played;

    public int getWon ()
    {
        return won;
    }

    public void setWon (int won)
    {
        this.won = won;
    }

    public int getPlayed ()
    {
        return played;
    }

    public void setPlayed (int played)
    {
        this.played = played;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [won = "+won+", played = "+played+"]";
    }
}
