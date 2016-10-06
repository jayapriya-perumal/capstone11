package com.coremedia.livecontext.ecommerce.ibm.storeinfo;

import com.coremedia.livecontext.ecommerce.ibm.common.AbstractWcWrapperService;
import com.coremedia.livecontext.ecommerce.ibm.common.StoreContextHelper;
import com.coremedia.livecontext.ecommerce.ibm.common.WcRestConnector;
import com.coremedia.livecontext.ecommerce.ibm.common.WcRestServiceMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.util.Collections;
import java.util.Map;

/**
 * A service that uses the getRestConnector() to get all store infos in wcs.
 */
public class WcStoreInfoWrapperService extends AbstractWcWrapperService {

  private static final Logger LOG = LoggerFactory.getLogger(WcStoreInfoWrapperService.class);

  private static final WcRestServiceMethod<Map, Void>
          GET_STORE_INFO = WcRestConnector.createServiceMethod(HttpMethod.GET, "coremedia/storeinfo", false, false, Map.class);

  public Map<String, Object> getStoreInfos() {
    Map<String, Object> result;
    try {
      result = getRestConnector().callServiceInternal(GET_STORE_INFO, Collections.<String>emptyList(), Collections.<String, String[]>emptyMap(),
              null, StoreContextHelper.getCurrentContext(), null);
    } catch (Exception e) {
      LOG.warn("Error occurred while calling the store info handler. Is the WCS available? ({})", e.getMessage());
      return null;
    }
    if (result == null || result.isEmpty()) {
      LOG.warn("No store info available from WCS.");
    }
    return result;
  }

}
