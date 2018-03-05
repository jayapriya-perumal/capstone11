package com.coremedia.blueprint.elastic.social.cae.view;

import com.coremedia.blueprint.base.elastic.social.configuration.ElasticSocialConfiguration;
import com.coremedia.blueprint.elastic.social.cae.controller.ContributionResult;
import com.coremedia.blueprint.elastic.social.cae.controller.ReviewsResult;
import com.coremedia.objectserver.view.events.ViewHookEventListener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;

import static com.coremedia.blueprint.base.cae.web.taglib.ViewHookEventNames.VIEW_HOOK_SEARCH;
import static com.coremedia.blueprint.base.cae.web.taglib.ViewHookEventNames.VIEW_HOOK_TEASER;


/**
 * A {@link ViewHookEventListener} that
 * is responsible for adding the average rating widget to rendered views.
 */
@Named
public class TeaserReviewViewHookEventListener extends AbstractESViewHookEventListener {

  @Nonnull
  @Override
  protected List<String> getSupportedViewHookEventIds() {
    return Arrays.asList(VIEW_HOOK_TEASER, VIEW_HOOK_SEARCH);
  }

  @Override
  protected boolean isEnabled(@Nonnull ElasticSocialConfiguration elasticSocialConfiguration) {
    return elasticSocialConfiguration.isReviewingEnabled();
  }

  @Nullable
  @Override
  protected List<String> getWhitelistTypes(@Nonnull ElasticSocialConfiguration elasticSocialConfiguration) {
    return elasticSocialConfiguration.getReviewDocumentTypes();
  }

  @Nullable
  @Override
  protected ContributionResult getContribution(@Nonnull Object target) {
    return new ReviewsResult(target);
  }

  @Nullable
  @Override
  protected String getView() {
    return "asAverageRating";
  }

  @Override
  public int getOrder() {
    return DEFAULT_ORDER;
  }
}
