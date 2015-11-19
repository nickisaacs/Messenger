package team5.bsec.messenger.entities;

/**
 * Created by sandman on 18/11/15.
 */
public class MsgSnippet {
    public String sender;
    public String time;
    public String snippet;

    public MsgSnippet(String sender, String snippet, String time) {
        this.sender = sender;
        this.snippet = snippet;
        this.time = time;
    }
}
