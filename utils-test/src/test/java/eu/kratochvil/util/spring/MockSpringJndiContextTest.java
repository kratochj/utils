package eu.kratochvil.util.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.naming.Context;
import javax.naming.InitialContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jiri Kratochvil <jiri.kratochvil@topmonks.com>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/MockSpringJndiContextTestTestContext.xml"})
public class MockSpringJndiContextTest {

    public static final Logger logger = LoggerFactory.getLogger(MockSpringJndiContextTest.class);

    public static final String TEST_STRING_IN_JNDI_CONTEXT = "TestStringInJndiContext";
    Context context;

    @Before
    public void setUp() throws Exception {
        context = new InitialContext();

    }

    @Test
    public void testJndiLookup() throws Exception {
        assertNotNull("Could not lookup object in JNDI context", context.lookup("utils.TestQueue"));
    }

    @Test
    public void testCallOnJndiContext() throws Exception {
        JndiTestQueue queue = (JndiTestQueue) context.lookup("utils.TestQueue");
        assertNotNull("Could not lookup object in JNDI context", queue);

        logger.info("Setting new value on JNDI object");
        queue.setValue(TEST_STRING_IN_JNDI_CONTEXT);


        logger.info("Looking up test object in JNDI again");
        JndiTestQueue newQueue = (JndiTestQueue) context.lookup("utils.TestQueue");
        assertEquals("Wrong value in object called over jndi context!", TEST_STRING_IN_JNDI_CONTEXT, newQueue.getValue());
    }
}
