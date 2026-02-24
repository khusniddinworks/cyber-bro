package com.eps.android.data;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class UrlScanCacheDao_Impl implements UrlScanCacheDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UrlScanCache> __insertionAdapterOfUrlScanCache;

  private final SharedSQLiteStatement __preparedStmtOfClearOldCache;

  public UrlScanCacheDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUrlScanCache = new EntityInsertionAdapter<UrlScanCache>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `url_scan_cache` (`url`,`isSecure`,`maliciousCount`,`scannerCount`,`timestamp`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @Nullable final UrlScanCache entity) {
        if (entity.getUrl() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getUrl());
        }
        final int _tmp = entity.isSecure() ? 1 : 0;
        statement.bindLong(2, _tmp);
        statement.bindLong(3, entity.getMaliciousCount());
        statement.bindLong(4, entity.getScannerCount());
        statement.bindLong(5, entity.getTimestamp());
      }
    };
    this.__preparedStmtOfClearOldCache = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM url_scan_cache WHERE timestamp < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertCache(final UrlScanCache cache,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfUrlScanCache.insert(cache);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object clearOldCache(final long expiryTime, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearOldCache.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, expiryTime);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearOldCache.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getCacheByUrl(final String url,
      final Continuation<? super UrlScanCache> $completion) {
    final String _sql = "SELECT * FROM url_scan_cache WHERE url = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (url == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, url);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<UrlScanCache>() {
      @Override
      @Nullable
      public UrlScanCache call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "url");
          final int _cursorIndexOfIsSecure = CursorUtil.getColumnIndexOrThrow(_cursor, "isSecure");
          final int _cursorIndexOfMaliciousCount = CursorUtil.getColumnIndexOrThrow(_cursor, "maliciousCount");
          final int _cursorIndexOfScannerCount = CursorUtil.getColumnIndexOrThrow(_cursor, "scannerCount");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final UrlScanCache _result;
          if (_cursor.moveToFirst()) {
            final String _tmpUrl;
            if (_cursor.isNull(_cursorIndexOfUrl)) {
              _tmpUrl = null;
            } else {
              _tmpUrl = _cursor.getString(_cursorIndexOfUrl);
            }
            final boolean _tmpIsSecure;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsSecure);
            _tmpIsSecure = _tmp != 0;
            final int _tmpMaliciousCount;
            _tmpMaliciousCount = _cursor.getInt(_cursorIndexOfMaliciousCount);
            final int _tmpScannerCount;
            _tmpScannerCount = _cursor.getInt(_cursorIndexOfScannerCount);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            _result = new UrlScanCache(_tmpUrl,_tmpIsSecure,_tmpMaliciousCount,_tmpScannerCount,_tmpTimestamp);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
