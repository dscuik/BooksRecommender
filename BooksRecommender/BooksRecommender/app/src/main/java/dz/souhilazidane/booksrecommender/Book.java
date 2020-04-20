package dz.souhilazidane.booksrecommender;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private int id_book;
    private String ISBN;
    private String title;
    private String author;
    private int year_of_publication;
    private String publisher;
    private String imageURL_L;


    public Book(int id,String ISBN, String title,String author,int year_of_publication,String publisher,String imageURL_L) {
        this.id_book=id;
        this.ISBN=ISBN;
        this.title=title;
        this.author=author;
        this.year_of_publication=year_of_publication;
        this.publisher=publisher;
        this.imageURL_L=imageURL_L;
    }

    public int getId_book() {
        return this.id_book;
    }

    public String getISBN() { return this.ISBN; }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public int getYear_of_publication() {
        return this.year_of_publication;
    }

    public String getPublisher() { return this.publisher; }

    public String getImageURL_L() {
        return this.imageURL_L;
    }

    public void setId_book(int id_book) { this.id_book=id_book; }

    public void setISBN(String ISBN) { this.ISBN=ISBN; }

    public void setTitle(String title) {
        this.title=title;
    }

    public void setAuthor(String author) {
        this.author=author;
    }

    public void setYear_of_publication(int year_of_publication) { this.year_of_publication=year_of_publication; }

    public void setPublisher(String publisher) { this.publisher=publisher; }

    public void setImageURL_L(String imageURL_L) {
        this.imageURL_L=imageURL_L;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_book);
        dest.writeString(this.ISBN);
        dest.writeString(this.title);
        dest.writeString(this.author);
        dest.writeInt(this.year_of_publication);
        dest.writeString(this.publisher);
        dest.writeString(this.imageURL_L);
    }

    protected Book(Parcel in) {
        this.id_book = in.readInt();
        this.ISBN = in.readString();
        this.title = in.readString();
        this.author = in.readString();
        this.year_of_publication = in.readInt();
        this.publisher = in.readString();
        this.imageURL_L = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

}
