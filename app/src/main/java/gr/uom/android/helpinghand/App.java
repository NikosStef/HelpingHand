package gr.uom.android.helpinghand;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        CustomDaoMaster.OpenHelper helper = new CustomDaoMaster.OpenHelper(this, "suggestions-db",null);
        Database db = helper.getWritableDb();
        daoSession = new CustomDaoMaster(db).newSession();

    }


    public DaoSession getDaoSession() {
        return daoSession;
    }
}