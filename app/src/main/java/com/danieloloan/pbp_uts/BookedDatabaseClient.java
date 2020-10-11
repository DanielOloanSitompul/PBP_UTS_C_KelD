package com.danieloloan.pbp_uts;

import android.content.Context;

import androidx.room.Room;

public class BookedDatabaseClient {
    private Context context;
    private static BookedDatabaseClient bookeddatabaseClient;

    private BookedDatabase booked_database;

    private BookedDatabaseClient(Context context) {
        this.context=context;
        booked_database = Room.databaseBuilder(context, BookedDatabase.class, "booked").build();
    }

    public static synchronized BookedDatabaseClient getInstance(Context context) {
        if(bookeddatabaseClient==null) {
            bookeddatabaseClient= new BookedDatabaseClient(context);
        }
        return bookeddatabaseClient;
    }

    public BookedDatabase getDatabase() {return booked_database;}
}
