package pl.mobilization.conference2015.sponsor.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import pl.mobilization.conference2015.R;
import pl.mobilization.conference2015.sponsor.SponsorModel;
import pl.mobilization.conference2015.sponsor.storage.SponsorStorage;

/**
 * Created by msaramak on 29.07.15.
 */

public class SponsorStorageOrmLite extends OrmLiteSqliteOpenHelper implements SponsorStorage {

    private static final String DATABASE_NAME = "sponsors.db";
    private static final int DATABASE_VERSION = 1;

    public SponsorStorageOrmLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(SponsorStorageOrmLite.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, SponsorModel.class);
        } catch (SQLException e) {
            Log.e(SponsorStorageOrmLite.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(SponsorStorageOrmLite.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, SponsorModel.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(SponsorStorageOrmLite.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }
}
