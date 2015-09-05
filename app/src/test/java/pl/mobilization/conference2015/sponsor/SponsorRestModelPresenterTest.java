package pl.mobilization.conference2015.sponsor;

import android.content.Context;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import lombok.extern.slf4j.Slf4j;
import pl.mobilization.conference2015.sponsor.events.OnSponsorClickEvent;
import pl.mobilization.conference2015.sponsor.events.SponsorUpdatedEvent;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepoModel;
import pl.mobilization.conference2015.sponsor.repository.SponsorRepository;
import pl.mobilization.conference2015.sponsor.rest.SponsorRestService;
import pl.mobilization.conference2015.sponsor.rest.SponsorListRestModel;
import pl.mobilization.conference2015.sponsor.view.SponsorsView;
import pl.mobilization.conference2015.sponsor.view.SponsorsListViewModel;
import rx.Observable;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created by msaramak on 19.08.15.
 */
@Slf4j
public class SponsorRestModelPresenterTest {


    @Mock
    SponsorRestService sponsorRestService;

    @Mock
    EventBus eventBus;
    @Mock
    SponsorsView view;

    @Mock
    SponsorRepository sponsorRepository;
    @Mock
    Context context;
    private SponsorPresenter testedSp;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        //GIVEN a sponsor presenter..
        testedSp = new SponsorPresenter(sponsorRepository, eventBus);
        List<SponsorRepoModel> l = new ArrayList<>();
        when(sponsorRepository.getSponsors()).thenReturn(Observable.<List<SponsorRepoModel>>just(l));


    }

    @After
    public void tearDown() throws Exception {

    }

    @SuppressWarnings("ResourceType")
    @Test
    public void testOnBindView() throws Exception {
        //GIVEN a sponsor presenter
        verify(eventBus).register(testedSp);
        //WHEN bind view
        testedSp.onBindView(context, view);

        //THEN check if background service is setup
        verify(context).bindService(any(Intent.class), any(), eq(Context.BIND_AUTO_CREATE));

    }

    @Test
    public void shouldDisplayDialogWhenOnSponsorClickEventCalled() throws Exception {
        //GIVEN a tested sponsor presenter with binded view
        testedSp.onBindView(context, view);
        //WHEN event come
        OnSponsorClickEvent event = new OnSponsorClickEvent(null);
        testedSp.onEvent(event);
        //THEN
        verify(view).showSponsorDialog(event);
    }

    @Test
    public void testOnUpdateSponsorList() throws Exception {
        //GIVEN a tested sponsor presenter with binded view
        testedSp.onBindView(context, view);
        //WHEN sponsors list is updated
        SponsorUpdatedEvent event = new SponsorUpdatedEvent();
        testedSp.onEvent(event);
        //THEN
        verify(view).updateSponsors(any(SponsorsListViewModel.class));
    }
}