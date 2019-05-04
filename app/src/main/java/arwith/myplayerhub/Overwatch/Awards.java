package arwith.myplayerhub.Overwatch;

public class Awards
{
    private int medals;

    private int cards;

    private int medalsBronze;

    private int medalsGold;

    private int medalsSilver;

    public int getMedals ()
    {
        return medals;
    }

    public void setMedals (int medals)
    {
        this.medals = medals;
    }

    public int getCards ()
    {
        return cards;
    }

    public void setCards (int cards)
    {
        this.cards = cards;
    }

    public int getMedalsBronze ()
    {
        return medalsBronze;
    }

    public void setMedalsBronze (int medalsBronze)
    {
        this.medalsBronze = medalsBronze;
    }

    public int getMedalsGold ()
    {
        return medalsGold;
    }

    public void setMedalsGold (int medalsGold)
    {
        this.medalsGold = medalsGold;
    }

    public int getMedalsSilver ()
    {
        return medalsSilver;
    }

    public void setMedalsSilver (int medalsSilver)
    {
        this.medalsSilver = medalsSilver;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [medals = "+medals+", cards = "+cards+", medalsBronze = "+medalsBronze+", medalsGold = "+medalsGold+", medalsSilver = "+medalsSilver+"]";
    }
}
