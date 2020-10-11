package com.danieloloan.pbp_uts;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Booked.class}, version = 1)
public abstract class BookedDatabase extends RoomDatabase {
    public abstract BookedDao bookedDao();
}
