package com.coremedia.livecontext.ecommerce.ibm.workspace;

import com.coremedia.livecontext.ecommerce.ibm.common.AbstractIbmCommerceBean;
import com.coremedia.livecontext.ecommerce.workspace.Workspace;

import java.util.Map;

/**
 * @deprecated This class is part of the legacy Blueprint commerce integration and has been deprecated
 * in favour of the Commerce Hub integration.
 */
@Deprecated
public class WorkspaceImpl extends AbstractIbmCommerceBean implements Workspace {

  private Map delegate;

  @Override
  public void setDelegate(Object delegate) {
    this.delegate = (Map) delegate;
  }

  @Override
  public String getName() {
    return (String) delegate.get("name");
  }

  @Override
  public String getDescription() {
    return (String) delegate.get("description");
  }

  @Override
  public String getExternalId() {
    return (String) delegate.get("identifier");
  }

  @Override
  public String getExternalTechId() {
    return (String) delegate.get("id");
  }

}
