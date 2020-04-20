package dz.souhilazidane.booksrecommender;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int id_user;
    private String first_name;
    private String family_name;
    private String year;
    private String field;


    public User(int id, String first_name, String family_name, String year, String field) {
        this.id_user = id;
        this.first_name = first_name;
        this.family_name = family_name;
        this.year = year;
        this.field = field;
    }

    public int getId_user() {
        return this.id_user;
    }

    public String getFirst() { return this.first_name; }

    public String getFamily() {
        return this.family_name;
    }

    public String getYear() {
        return this.year;
    }

    public String getField() { return this.field; }

    public void setId_user(int id_user) { this.id_user=id_user; }

    public void setFirst(String first_name) { this.first_name=first_name; }

    public void setFamily(String family_name) {
        this.family_name=family_name;
    }

    public void setYear(String year) { this.year=year; }

    public void setField(String field) { this.field=field; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_user);
        dest.writeString(this.first_name);
        dest.writeString(this.family_name);
        dest.writeString(this.year);
        dest.writeString(this.field);
    }

    protected User(Parcel in) {
        this.id_user = in.readInt();
        this.first_name = in.readString();
        this.family_name = in.readString();
        this.year = in.readString();
        this.field = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

}
