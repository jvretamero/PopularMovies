package br.com.joaoretamero.popularmovies.model.local;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "app_configuration", id = "_id")
public class AppSettings extends Model {

    @Column(name = "sort_order")
    public String sortOrder;

    public AppSettings() {
        super();
    }

    public static AppSettings get() {
        AppSettings settings = new Select().from(AppSettings.class).limit(1).executeSingle();

        if (settings == null) {
            settings = new AppSettings();
            settings.sortOrder = Movie.DEFAULT_ORDER;
            settings.save();
        }

        return settings;
    }
}
