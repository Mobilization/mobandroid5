package pl.mobilization.conference2015.sponsor.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import pl.mobilization.conference2015.sponsor.SponsorRepoModel;
import pl.mobilization.conference2015.sponsor.SponsorViewModel;
import pl.mobilization.conference2015.sponsor.rest.Sponsor;
import pl.mobilization.conference2015.sponsor.rest.Sponsors;
import rx.Observable;

/**
 * Created by msaramak on 29.07.15.
 */

public class SponsorRepositoryOrmLite extends OrmLiteSqliteOpenHelper implements SponsorRepository {

    private static final String DATABASE_NAME = "sponsors.db";
    private static final int DATABASE_VERSION = 1;

    public SponsorRepositoryOrmLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(SponsorRepositoryOrmLite.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, SponsorRepoModel.class);
        } catch (SQLException e) {
            Log.e(SponsorRepositoryOrmLite.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(SponsorRepositoryOrmLite.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, SponsorRepoModel.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(SponsorRepositoryOrmLite.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Observable<List<SponsorRepoModel>> getSponsors() {
        try {
            List<SponsorRepoModel> sponsorsFromDB = getDao(SponsorRepoModel.class).queryForAll();

            return Observable.just(sponsorsFromDB);
        } catch (SQLException e) {
            e.printStackTrace();
            return Observable.empty();
        }
    }

    @Override
    public void saveSponsors(List<SponsorRepoModel> models) {
        try {
            for (SponsorRepoModel m : models) {
                getDao(SponsorRepoModel.class).createOrUpdate(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }



}
