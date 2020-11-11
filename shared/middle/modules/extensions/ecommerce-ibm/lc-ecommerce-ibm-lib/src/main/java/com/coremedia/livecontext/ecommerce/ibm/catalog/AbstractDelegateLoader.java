package com.coremedia.livecontext.ecommerce.ibm.catalog;


import org.apache.commons.collections4.Transformer;

import java.util.Map;

/**
 * @deprecated This class is part of the legacy Blueprint commerce integration and has been deprecated
 * in favour of the Commerce Hub integration.
 */
@Deprecated
abstract class AbstractDelegateLoader implements Transformer {

  private Map<String, Object> delegateFromCache;

  @Override
  public final Object transform(Object input) {
    if (null == delegateFromCache) {
      delegateFromCache = getDelegateFromCache();
    }
    //noinspection SuspiciousMethodCalls
    return delegateFromCache.get(input);
  }

  abstract Map<String, Object> getDelegateFromCache();

}
