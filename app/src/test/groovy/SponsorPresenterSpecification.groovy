import android.content.Context
import com.google.common.base.Optional
import de.greenrobot.event.EventBus
import pl.mobilization.conference2015.sponsor.SponsorPresenter
import pl.mobilization.conference2015.sponsor.events.SponsorUpdatedEvent
import pl.mobilization.conference2015.sponsor.repository.SponsorRepoModel
import pl.mobilization.conference2015.sponsor.repository.SponsorRepository
import pl.mobilization.conference2015.sponsor.view.SponsorViewModel
import pl.mobilization.conference2015.sponsor.view.SponsorsListViewModel
import pl.mobilization.conference2015.sponsor.view.SponsorsView
import rx.Observable
import spock.lang.Specification


/**
 * Created by mario on 29.08.15.
 */
class SponsorPresenterSpecification extends Specification {

    def "Should convert Repo model to view model"() {
        given: "I have mocked repo and event bus"
        SponsorRepository sponsorRepoMock = getMockRepo()
        def eventBusMock = Mock(EventBus)
        and: "view where i check a return value"
        def viewMock = Mock(SponsorsView)
        def sponsorPresenter = new SponsorPresenter(sponsorRepoMock, eventBusMock);
        sponsorPresenter.onBindView(Mock(Context), viewMock)
        when: "update view "
        sponsorPresenter.onEvent(Mock(SponsorUpdatedEvent))
        then: "view show me list of sponsors"
        def viewModel = new SponsorsListViewModel()
        def sponsorViewModel = SponsorViewModel.builder().level(SponsorViewModel.Level.PLATINIUM).displayName("Mobica").description(Optional.fromNullable("desc")).build();
        viewModel.addSponsor(sponsorViewModel)
        1*viewMock.updateSponsors(!null as SponsorsListViewModel)
        //1*viewMock.updateSponsors(_)
    }

    private SponsorRepository getMockRepo() {
        def sponsorListRepoModel = new ArrayList<SponsorRepoModel>();
        def sponsorRepoModel = new SponsorRepoModel()
        sponsorRepoModel.name = "Mobica"
        sponsorRepoModel.descriptionHtml = "desc"
        sponsorRepoModel.level = SponsorViewModel.Level.PLATINIUM.ordinal()
        sponsorRepoModel.logo = "http://www.mobilca.com/logo.png"
        sponsorListRepoModel.add(sponsorRepoModel)
        def sponsorRepoMock = Mock(SponsorRepository) {
            getSponsors() >> Observable.<List<SponsorRepoModel>> just(sponsorListRepoModel)
        }
        sponsorRepoMock
    }
}