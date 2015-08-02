package pl.mobilization.conference2015.sponsor;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by msaramak on 29.07.15.
 */
@DatabaseTable(tableName = "sponsors")
public class SponsorRepoModel {

    @DatabaseField(id = true)
    public String name;

    @DatabaseField
    public String descriptionHtml;

    @DatabaseField
    public String url;

    @DatabaseField
    public String logo;

    @DatabaseField
    public int level;

}
