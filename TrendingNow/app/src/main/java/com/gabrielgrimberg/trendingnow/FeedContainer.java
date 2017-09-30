package com.gabrielgrimberg.trendingnow;

/**
 * Created by GabrielGrimberg on 30/09/2017.
 */

public class FeedContainer
{
    private String name;
    private String artist;
    private String releaseData;
    private String summary;
    private String imageURL;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getArtist()
    {
        return artist;
    }

    public void setArtist(String artist)
    {
        this.artist = artist;
    }

    public String getReleaseData()
    {
        return releaseData;
    }

    public void setReleaseData(String releaseData)
    {
        this.releaseData = releaseData;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getImageURL()
    {
        return imageURL;
    }

    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    @Override
    public String toString()
    {
        return "name='" + name + '\n' +
                ", artist='" + artist + '\n' +
                ", releaseData='" + releaseData + '\n' +
                ", imageURL='" + imageURL + '\n';
    }
}
