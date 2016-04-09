package com.sande.filist.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sandeep on 09-Apr-16.
 */
public class InstaFeed implements Parcelable{
    String imageStandard;
    String caption;
    String userName;
    String profPic;
    String postID;

    public InstaFeed( String imageStandard, String caption, String userName, String profPic, String postID) {
        this.imageStandard = imageStandard;
        this.caption = caption;
        this.userName = userName;
        this.profPic = profPic;
        this.postID = postID;
    }

    public String getImageStandard() {
        return imageStandard;
    }

    public String getCaption() {
        return caption;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfPic() {
        return profPic;
    }

    public String getPostID() {
        return postID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageStandard);
        dest.writeString(this.caption);
        dest.writeString(this.userName);
        dest.writeString(this.profPic);
        dest.writeString(this.postID);
    }

    protected InstaFeed(Parcel in) {
        this.imageStandard = in.readString();
        this.caption = in.readString();
        this.userName = in.readString();
        this.profPic = in.readString();
        this.postID = in.readString();
    }

    public static final Creator<InstaFeed> CREATOR = new Creator<InstaFeed>() {
        @Override
        public InstaFeed createFromParcel(Parcel source) {
            return new InstaFeed(source);
        }

        @Override
        public InstaFeed[] newArray(int size) {
            return new InstaFeed[size];
        }
    };
}
