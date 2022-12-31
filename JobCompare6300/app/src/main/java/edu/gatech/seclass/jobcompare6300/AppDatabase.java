package edu.gatech.seclass.jobcompare6300;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Job.class, ComparisonEditor.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract JobComparisonAppDao jobComparisonAppDao();
}