package com.coremedia.livecontext.ecommerce.ibm.event;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.coremedia.livecontext.ecommerce.common.CommerceException;
import com.coremedia.livecontext.ecommerce.ibm.common.AbstractServiceTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = AbstractServiceTest.LocalConfig.class)
@ActiveProfiles(AbstractServiceTest.LocalConfig.PROFILE)
public class WcCacheWrapperServiceIT extends AbstractServiceTest {
  private static long TIME_STAMP = 1399641181799L;

  @Inject
  WcCacheWrapperService testling;

  String origServiceEndpoint;

  @Before
  public void setup() {
    super.setup();
    origServiceEndpoint = testling.getRestConnector().getServiceEndpoint();
  }

  @After
  public void reset() {
    testling.getRestConnector().setServiceEndpoint(origServiceEndpoint);
  }

  @Test
  public void testGetCacheInvalidations() throws Exception {
    if (!"*".equals(System.getProperties().get("betamax.ignoreHosts"))) {
      return;
    }
    long latestTimestamp = testling.getLatestTimestamp();
    Map<String, Object> cacheInvalidations = testling.getCacheInvalidations(latestTimestamp);
    assertNotNull(cacheInvalidations);
  }

  @Betamax(tape = "cache_testGetCacheInvalidationsUnknownHost", match = {MatchRule.path, MatchRule.query})
  @Test(expected = CommerceException.class)
  public void testGetCacheInvalidationsUnknownHost() throws Exception {
    testling.getRestConnector().setServiceEndpoint("http://does.not.exists/blub");
    testling.getCacheInvalidations(TIME_STAMP);
  }

  @Betamax(tape = "cache_testGetCacheInvalidationsError404", match = {MatchRule.path, MatchRule.query})
  @Test(expected = CommerceException.class)
  public void testGetCacheInvalidationsError404() throws Exception {
    testling.getRestConnector().setServiceEndpoint(testling.getRestConnector().getServiceEndpoint() + "/blub");
    testling.getCacheInvalidations(TIME_STAMP);
  }

  @Betamax(tape = "cache_testGetLatestTimestamp", match = {MatchRule.path, MatchRule.query})
  @Test
  public void testGetLatestTimestamp() throws Exception {
    long latestTimestamp = testling.getLatestTimestamp();
    assertTrue(latestTimestamp > 0);
  }

  @Betamax(tape = "cache_testRequestLatestInvalidationTimeStampUnknownHost", match = {MatchRule.path, MatchRule.query})
  @Test(expected = CommerceException.class)
  public void testRequestLatestInvalidationTimeStampUnknownHost() throws Exception {
    testling.getRestConnector().setServiceEndpoint("http://does.not.exists/blub");
    testling.getLatestTimestamp();
  }

  @Betamax(tape = "cache_testRequestLatestInvalidationTimeStampError404", match = {MatchRule.path, MatchRule.query})
  @Test(expected = CommerceException.class)
  public void testRequestLatestInvalidationTimeStampError404() throws Exception {
    testling.getRestConnector().setServiceEndpoint(testling.getRestConnector().getServiceEndpoint() + "/blub");
    testling.getLatestTimestamp();
  }

}
