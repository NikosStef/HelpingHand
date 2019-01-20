package gr.uom.android.helpinghand;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

public class CustomDaoMaster extends DaoMaster {


    public CustomDaoMaster(Database db) {
        super(db);
    }

    public static class OpenHelper extends DaoMaster.OpenHelper{
        public OpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory){
            super(context, name, factory);
        }

        @Override
        public void onCreate(Database db) {
            super.onCreate(db);
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Watch TV/Movie', '0.2 - 0.4', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Go to the gym/Exercise', '0.4 - 0.6', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Go to the gym/Exercise', '0.2 - 0.4', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Meet and talk to a friend/relative', '0.0 - 0.2', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Read a book', '0.6 - 0.8', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Try meditation or relaxing yoga exercises', '0.0 - 0.2', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Play video-games', '0.2 - 0.4', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Listen to music', '0.4 - 0.6', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Listen to a podcast', '0.0 - 0.2', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Go outside for a coffee/walk', '0.6 - 0.8', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Make some progress on your working/school tasks', '0.8 - 1.0', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Make some progress on your working/school tasks', '0.6 - 0.8', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Start a new TV series', '0.4 - 0.6', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Cook for upcoming meals', '0.8 - 1.0', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Clean your house/room', '0.0 - 0.2', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Call your parents/relatives to check on them', '0.8 - 1.0', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Organize a board-games night with friends', '0.8 - 1.0', 0)");
            db.execSQL("INSERT INTO " + SuggestionsDao.TABLENAME + " (" +
                    SuggestionsDao.Properties.Suggestion.columnName + ", " +
                    SuggestionsDao.Properties.Rate.columnName + ", " +
                    SuggestionsDao.Properties.Checkpoint.columnName + ") VALUES ('Go for a drink/coffee', '0.4 - 0.6', 0)");


        }
    }
}
