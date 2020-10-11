package com.danieloloan.pbp_uts;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BookedDao {
    @Query("SELECT * FROM Booked")
    List<Booked> getAll();

    @Insert
    void insert(Booked employee);

    @Update
    void update(Booked employee);

    @Delete
    void delete(Booked employee);
}
