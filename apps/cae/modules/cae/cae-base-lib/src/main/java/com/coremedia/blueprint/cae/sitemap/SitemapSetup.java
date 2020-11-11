package com.coremedia.blueprint.cae.sitemap;

import com.coremedia.blueprint.cae.config.BlueprintCaeSitemapConfigurationProperties;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

/**
 * Container for all beans needed to configure a SitemapGenerationController.
 * <p>
 * While this indirection looks unnecessarily complex for handlers with fix
 * configurations, it allows for an easy configuration switch
 * (e.g. request/site specific) in more generic handlers (namely the
 * {@link com.coremedia.blueprint.cae.sitemap.SitemapGenerationHandler}).
 */
public class SitemapSetup {
  private List<SitemapUrlGenerator> urlGenerators;
  private SitemapRendererFactory sitemapRendererFactory;

  private final BlueprintCaeSitemapConfigurationProperties blueprintCaeSitemapConfigurationProperties;

  public SitemapSetup(BlueprintCaeSitemapConfigurationProperties blueprintCaeSitemapConfigurationProperties) {
    this.blueprintCaeSitemapConfigurationProperties = blueprintCaeSitemapConfigurationProperties;
  }

  // --- configuration ----------------------------------------------

  @Required
  public void setUrlGenerators(List<SitemapUrlGenerator> urlGenerators) {
    this.urlGenerators = urlGenerators;
  }

  @Required
  public void setSitemapRendererFactory(SitemapRendererFactory sitemapRendererFactory) {
    this.sitemapRendererFactory = sitemapRendererFactory;
  }

  // --- features ---------------------------------------------------

  List<SitemapUrlGenerator> getUrlGenerators() {
    return urlGenerators;
  }

  SitemapRendererFactory getSitemapRendererFactory() {
    return sitemapRendererFactory;
  }

  String getProtocol() {
    return blueprintCaeSitemapConfigurationProperties.getProtocol();
  }
}
