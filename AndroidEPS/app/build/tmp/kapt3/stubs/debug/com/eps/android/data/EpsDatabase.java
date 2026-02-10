package com.eps.android.data;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, d2 = {"Lcom/eps/android/data/EpsDatabase;", "Landroidx/room/RoomDatabase;", "()V", "blacklistDao", "Lcom/eps/android/data/BlacklistDao;", "threatEventDao", "Lcom/eps/android/data/ThreatEventDao;", "app_debug"})
@androidx.room.Database(entities = {com.eps.android.data.ThreatEvent.class, com.eps.android.data.BlacklistedDomain.class}, version = 2, exportSchema = false)
public abstract class EpsDatabase extends androidx.room.RoomDatabase {
    
    public EpsDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public abstract com.eps.android.data.ThreatEventDao threatEventDao();
    
    @org.jetbrains.annotations.NotNull
    public abstract com.eps.android.data.BlacklistDao blacklistDao();
}