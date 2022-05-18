import java.io.Serializable;

public class Post implements Serializable { //Serializable for passing

    private String Post_id;
    private long created;
    private String author;
    private int conents;
    private String body;

    public Post(String post_id, long created, String author, int conents, String body) {
        Post_id = post_id;
        this.created = created;
        this.author = author;
        this.conents = conents;
        this.body = body;
    }

    public String getPost_id() {
        return Post_id;
    }

    public void setPost_id(String post_id) {
        Post_id = post_id;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getConents() {
        return conents;
    }

    public void setConents(int conents) {
        this.conents = conents;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }    
    
}
