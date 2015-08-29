package pl.mobilization.conference2015.sponsor.repository;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.Getter;
import lombok.NonNull;

import static com.google.common.base.Preconditions.checkArgument;
import static pl.mobilization.conference2015.base.MyPredicates.notNullOrEmpty;

/**
 * Created by msaramak on 29.07.15.
 */
@Getter
@DatabaseTable(tableName = "sponsors")
public class SponsorRepoModel {

    public static final SponsorRepoModel EMPTY = new SponsorRepoModel();

    private SponsorRepoModel(){

    }

    @DatabaseField(id = true)
    private String name;

    @DatabaseField
    private String descriptionHtml;

    @DatabaseField
    private String url;

    @DatabaseField
    private String logo;

    @DatabaseField
    private int level;

    public static Builder builder(String name){
        return new Builder(name);
    }
    public static class Builder {


        private String bName;
        private String bLogo;
        private String bUrl;
        private String bDescHtml;
        private int bLevel;

        public Builder(@NonNull String name) {
            this.bName = name;
        }

        public Builder logo(@NonNull String logo){
            this.bLogo = logo;
            return this;
        }

        public Builder url(@NonNull String url) {
            this.bUrl = url;
            return this;
        }
        public Builder description(@NonNull String html){
            this.bDescHtml = html;
            return this;
        }

        public Builder level(int val) {
            this.bLevel = val;
            return this;
        }

        public SponsorRepoModel build(){
            SponsorRepoModel model = new SponsorRepoModel();
            check(bName);
            check(bLogo);
            if (!notNullOrEmpty.apply(bUrl) && !notNullOrEmpty.apply(bDescHtml)) {
                throw new IllegalArgumentException("url or description html must be not empty");
            }

            checkArgument(bLevel >= 0 || bLevel < 5, "Wrong sponsor level " + bLevel);
            model.name = bName;
            model.url = bUrl;
            model.logo = bLogo;
            model.level = bLevel;
            model.descriptionHtml = bDescHtml;
            return model;
        }

        private void check(Object val){
            if (!notNullOrEmpty.apply(val)){
                throw new IllegalArgumentException("val can not be null or empty");
            }
        }


    }

}
