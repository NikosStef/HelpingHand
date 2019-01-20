package gr.uom.android.helpinghand;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Suggestions {

    @Id(autoincrement = true)
    private Long id;

    @NotNull
    private String suggestion;
    @NotNull
    private String rate;
    
    private int checkpoint;

    @Generated(hash = 1062202017)
    public Suggestions(Long id, @NotNull String suggestion, @NotNull String rate,
            int checkpoint) {
        this.id = id;
        this.suggestion = suggestion;
        this.rate = rate;
        this.checkpoint = checkpoint;
    }

    @Generated(hash = 471916638)
    public Suggestions() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuggestion() {
        return this.suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public String getRate() {
        return this.rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public int getCheckpoint() {
        return this.checkpoint;
    }

    public void setCheckpoint(int checkpoint) {
        this.checkpoint = checkpoint;
    }

}


