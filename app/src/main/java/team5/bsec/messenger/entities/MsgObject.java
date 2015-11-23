package team5.bsec.messenger.entities;

/**
 * Created by sandman on 23/11/15.
 */
public class MsgObject {
    public String address;
    public String date;

    public MsgObject(String date, String address) {
        this.date = date;
        this.address = address;
    }

}
