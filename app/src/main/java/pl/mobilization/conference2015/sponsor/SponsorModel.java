package pl.mobilization.conference2015.sponsor;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.net.URL;

/**
 * Created by msaramak on 29.07.15.
 */
@DatabaseTable(tableName = "sponsors")
public class SponsorModel {

    @DatabaseField(id = true)
    public String name;

    @DatabaseField
    public String descriptionHtml;

    @DatabaseField
    public URL url;

    @DatabaseField
    public URL logo;


}
