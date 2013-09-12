/*
 * Created on 29.3.2011
 * (C) 2011 ICZ, a.s.
 */
package eu.kratochvil.util.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple implementation of JNDI context that should be used in unit-tests
 * within Spring context.
 * <p/>
 * Basic usage is simple:
 * <pre>
 * &lt;bean id="jndiContext" class="cz.isvs.reg.ros.ws.repository.MockSpringJndiContext"&gt;
 *   &lt;property name="jndiContext"&gt;
 *      &lt;map&gt;
 *          &lt;entry key="ros.VstupniFronta" value-ref="destinationVstupniFronta"/&gt;
 *          &lt;entry key="ros.VystupniFronta" value-ref="destinationVystupniFronta"/&gt;
 *          &lt;entry key="weblogic.jms.XAConnectionFactory" value-ref="activeMQConnectionFactory"/&gt;
 *      &lt;/map&gt;
 *   &lt;/property&gt;
 * &lt;/bean&gt;
 * </pre>
 *
 * There isn't needet to use something like <code>jndi.properties</code> because jndi context is define
 * directly inside spring context.
 *
 * TODO Tests!
 *
 * @author Jiri Kratochvil (jiri.kratochvil@jetminds.com)
 * @version $Revision:$
 */
public class MockSpringJndiContext implements InitializingBean {

    public static final Logger logger = LoggerFactory.getLogger(MockSpringJndiContext.class);

    Map<String, Object> jndiContext = new HashMap<String, Object>();

    public void setJndiContext(Map<String, Object> jndiContext) {
        this.jndiContext = jndiContext;
    }

    public void afterPropertiesSet() throws Exception {
        logger.info("Initializing JNDI context");
        SimpleNamingContextBuilder builder = SimpleNamingContextBuilder.emptyActivatedContextBuilder();
        for (Map.Entry entry : jndiContext.entrySet()) {
            logger.debug("Binding {} in JNDI context", entry.getKey().toString());
            builder.bind(entry.getKey().toString(), entry.getValue());
        }
    }
}
