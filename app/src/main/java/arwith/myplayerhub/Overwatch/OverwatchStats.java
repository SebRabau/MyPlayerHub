package arwith.myplayerhub.Overwatch;

public class OverwatchStats {

    private boolean isprivate;

    private String endorsement;

    private int level;

    private String icon;

    private String rating;

    private String ratingIcon;

    private int prestige;

    private String gamesWon;

    private String prestigeIcon;

    private String endorsementIcon;

    private CompetitiveStats competitiveStats;

    private QuickPlayStats quickPlayStats;

    private String name;

    private String levelIcon;

    public boolean getPrivate ()
    {
        return isprivate;
    }

    public void setPrivate (boolean isprivate)
    {
        this.isprivate = isprivate;
    }

    public String getEndorsement ()
    {
        return endorsement;
    }

    public void setEndorsement (String endorsement)
    {
        this.endorsement = endorsement;
    }

    public int getLevel ()
    {
        return level;
    }

    public void setLevel (int level)
    {
        this.level = level;
    }

    public String getIcon ()
    {
        return icon;
    }

    public void setIcon (String icon)
    {
        this.icon = icon;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public String getRatingIcon ()
    {
        return ratingIcon;
    }

    public void setRatingIcon (String ratingIcon)
    {
        this.ratingIcon = ratingIcon;
    }

    public int getPrestige ()
    {
        return prestige;
    }

    public void setPrestige (int prestige)
    {
        this.prestige = prestige;
    }

    public String getGamesWon ()
    {
        return gamesWon;
    }

    public void setGamesWon (String gamesWon)
    {
        this.gamesWon = gamesWon;
    }

    public String getPrestigeIcon ()
    {
        return prestigeIcon;
    }

    public void setPrestigeIcon (String prestigeIcon)
    {
        this.prestigeIcon = prestigeIcon;
    }

    public String getEndorsementIcon ()
    {
        return endorsementIcon;
    }

    public void setEndorsementIcon (String endorsementIcon)
    {
        this.endorsementIcon = endorsementIcon;
    }

    public CompetitiveStats getCompetitiveStats ()
    {
        return competitiveStats;
    }

    public void setCompetitiveStats (CompetitiveStats competitiveStats)
    {
        this.competitiveStats = competitiveStats;
    }

    public QuickPlayStats getQuickPlayStats ()
    {
        return quickPlayStats;
    }

    public void setQuickPlayStats (QuickPlayStats quickPlayStats)
    {
        this.quickPlayStats = quickPlayStats;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getLevelIcon ()
    {
        return levelIcon;
    }

    public void setLevelIcon (String levelIcon)
    {
        this.levelIcon = levelIcon;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [privateimg = "+isprivate+", endorsement = "+endorsement+", level = "+level+", icon = "+icon+", rating = "+rating+", ratingIcon = "+ratingIcon+", prestige = "+prestige+", gamesWon = "+gamesWon+", prestigeIcon = "+prestigeIcon+", endorsementIcon = "+endorsementIcon+", competitiveStats = "+competitiveStats+", quickPlayStats = "+quickPlayStats+", name = "+name+", levelIcon = "+levelIcon+"]";
    }
}
