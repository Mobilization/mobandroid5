package pl.mobilization.conference2015.sponsor;

import android.net.Uri;

import com.google.common.base.Optional;

import lombok.Getter;
import lombok.experimental.Builder;
import pl.mobilization.conference2015.sponsor.rest.Sponsor;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestServiceRetrofit;

/**
 * Created by msaramak on 31.07.15.
 */
@Builder
@Getter
public class SponsorViewModel {

    public String displayName;
    public Optional<Uri> logoUrl;
    public Optional<String> description;
    public Optional<Uri> link;
    public Level level;

    public boolean isTitle() {
        return false;
    }

    public static SponsorViewModel convert(Sponsor sponsor, Level level) {
        SponsorViewModelBuilder builder = SponsorViewModel.builder()
                .displayName(sponsor.name)
                .description(Optional.fromNullable(sponsor.description_html));
        if (sponsor.link == null) {
            builder.link(Optional.<Uri>absent());
        } else {
            builder.link(Optional.fromNullable(Uri.parse(sponsor.link)));
        }
        builder.logoUrl(Optional.fromNullable(Uri.parse(SponsorRestServiceRetrofit.CONFERENCE_MAIN_URL + sponsor.logo_url)))
                .level(level);
        return builder.build();
    }

    public static SponsorViewModel title(Level level) {
        return new SponsorViewModelTitle(level.name(), level);
    }

    private static class SponsorViewModelTitle extends SponsorViewModel {
        SponsorViewModelTitle(String displayName, Level level) {
            super(displayName, null, null, null, level);
        }

        @Override
        public boolean isTitle() {
            return true;
        }
    }

    public static enum Level {
        DIAMOND, PLATINIUM, GOLD, SILVER
    }
}
