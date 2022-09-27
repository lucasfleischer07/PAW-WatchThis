package ar.itba.edu.paw.services;

import ar.edu.itba.paw.persistance.ContentDao;
import ar.edu.itba.paw.services.ContentServiceImpl;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class ContentServiceImplTest {
    @InjectMocks
    private ContentServiceImpl cs;

    @Mock
    private ContentDao contentDao;

    @Before
    public void setUp() {
        cs=new ContentServiceImpl(contentDao);
    }
}
