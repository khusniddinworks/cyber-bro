package com.eps.android.data;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class EpsDatabase_Impl extends EpsDatabase {
  private volatile ThreatEventDao _threatEventDao;

  private volatile BlacklistDao _blacklistDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `threat_events` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `type` TEXT NOT NULL, `severity` TEXT NOT NULL, `source` TEXT NOT NULL, `details` TEXT NOT NULL, `isResolved` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `blacklist` (`domain` TEXT NOT NULL, `reason` TEXT NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`domain`))");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '3a5f12165d8a9687cc35088873139371')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `threat_events`");
        db.execSQL("DROP TABLE IF EXISTS `blacklist`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsThreatEvents = new HashMap<String, TableInfo.Column>(7);
        _columnsThreatEvents.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThreatEvents.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThreatEvents.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThreatEvents.put("severity", new TableInfo.Column("severity", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThreatEvents.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThreatEvents.put("details", new TableInfo.Column("details", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsThreatEvents.put("isResolved", new TableInfo.Column("isResolved", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysThreatEvents = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesThreatEvents = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoThreatEvents = new TableInfo("threat_events", _columnsThreatEvents, _foreignKeysThreatEvents, _indicesThreatEvents);
        final TableInfo _existingThreatEvents = TableInfo.read(db, "threat_events");
        if (!_infoThreatEvents.equals(_existingThreatEvents)) {
          return new RoomOpenHelper.ValidationResult(false, "threat_events(com.eps.android.data.ThreatEvent).\n"
                  + " Expected:\n" + _infoThreatEvents + "\n"
                  + " Found:\n" + _existingThreatEvents);
        }
        final HashMap<String, TableInfo.Column> _columnsBlacklist = new HashMap<String, TableInfo.Column>(3);
        _columnsBlacklist.put("domain", new TableInfo.Column("domain", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlacklist.put("reason", new TableInfo.Column("reason", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlacklist.put("addedAt", new TableInfo.Column("addedAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBlacklist = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBlacklist = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBlacklist = new TableInfo("blacklist", _columnsBlacklist, _foreignKeysBlacklist, _indicesBlacklist);
        final TableInfo _existingBlacklist = TableInfo.read(db, "blacklist");
        if (!_infoBlacklist.equals(_existingBlacklist)) {
          return new RoomOpenHelper.ValidationResult(false, "blacklist(com.eps.android.data.BlacklistedDomain).\n"
                  + " Expected:\n" + _infoBlacklist + "\n"
                  + " Found:\n" + _existingBlacklist);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "3a5f12165d8a9687cc35088873139371", "b25c9eb58d53c1f46a9d8995995b2259");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "threat_events","blacklist");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `threat_events`");
      _db.execSQL("DELETE FROM `blacklist`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(ThreatEventDao.class, ThreatEventDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BlacklistDao.class, BlacklistDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public ThreatEventDao threatEventDao() {
    if (_threatEventDao != null) {
      return _threatEventDao;
    } else {
      synchronized(this) {
        if(_threatEventDao == null) {
          _threatEventDao = new ThreatEventDao_Impl(this);
        }
        return _threatEventDao;
      }
    }
  }

  @Override
  public BlacklistDao blacklistDao() {
    if (_blacklistDao != null) {
      return _blacklistDao;
    } else {
      synchronized(this) {
        if(_blacklistDao == null) {
          _blacklistDao = new BlacklistDao_Impl(this);
        }
        return _blacklistDao;
      }
    }
  }
}
