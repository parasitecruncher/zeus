package info.company.zeus.Models;

/**
 * Created by mithun on 26-03-2017.
 */
public class Scloud {
    public int id;
    public String title;
    public String stream_url;
    public String artwork_url;

    public Scloud(int id1,String Title1,String stream_url,String artwork_url)
    {

        id=id1;
        title=Title1;
        this.stream_url=stream_url;
        this.artwork_url=artwork_url;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStream_url() {
        return stream_url;
    }

    public void setStream_url(String stream_url) {
        this.stream_url = stream_url;
    }

    public String getArtwork_url() {
        return artwork_url;
    }

    public void setArtwork_url(String artwork_url) {
        this.artwork_url = artwork_url;
    }
}
