package arwith.myplayerhub.Overwatch;

public class CompetitiveStats
{
    private Awards awards;

    private Games games;

    public Awards getAwards ()
    {
        return awards;
    }

    public void setAwards (Awards awards)
    {
        this.awards = awards;
    }

    public Games getGames ()
    {
        return games;
    }

    public void setGames (Games games)
    {
        this.games = games;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [awards = "+awards+", games = "+games+"]";
    }
}
