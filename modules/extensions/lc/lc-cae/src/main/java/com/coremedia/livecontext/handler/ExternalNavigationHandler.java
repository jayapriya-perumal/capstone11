package com.coremedia.livecontext.handler;

import com.coremedia.blueprint.base.links.PostProcessorPrecendences;
import com.coremedia.blueprint.cae.web.links.NavigationLinkSupport;
import com.coremedia.blueprint.common.contentbeans.CMNavigation;
import com.coremedia.blueprint.common.contentbeans.Page;
import com.coremedia.blueprint.common.navigation.Navigation;
import com.coremedia.cap.multisite.Site;
import com.coremedia.livecontext.contentbeans.CMExternalPage;
import com.coremedia.livecontext.contentbeans.LiveContextExternalChannel;
import com.coremedia.livecontext.context.CategoryInSite;
import com.coremedia.livecontext.context.LiveContextNavigation;
import com.coremedia.livecontext.ecommerce.catalog.Category;
import com.coremedia.livecontext.ecommerce.common.NotFoundException;
import com.coremedia.livecontext.navigation.LiveContextCategoryNavigation;
import com.coremedia.livecontext.product.ProductList;
import com.coremedia.livecontext.product.ProductListSubstitutionService;
import com.coremedia.objectserver.web.HandlerHelper;
import com.coremedia.objectserver.web.links.Link;
import com.coremedia.objectserver.web.links.LinkPostProcessor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.coremedia.blueprint.base.links.UriConstants.ContentTypes.CONTENT_TYPE_HTML;
import static com.coremedia.blueprint.base.links.UriConstants.Patterns.PATTERN_SEGMENTS;
import static com.coremedia.blueprint.base.links.UriConstants.Prefixes.PREFIX_SERVICE;
import static com.coremedia.blueprint.base.links.UriConstants.RequestParameters.VIEW_PARAMETER;
import static com.coremedia.blueprint.base.links.UriConstants.Segments.SEGMENT_REST;
import static com.coremedia.blueprint.cae.constants.RequestAttributeConstants.setPage;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.StringUtils.isEmpty;

@Link
@RequestMapping
@LinkPostProcessor
public class ExternalNavigationHandler extends LiveContextPageHandlerBase {
  public static final String LIVECONTEXT_POLICY_COMMERCE_CATEGORY_LINKS = "livecontext.policy.commerce-category-links";

  public static final String REQUEST_ATTRIBUTE_CATEGORY = "livecontext.category";
  private static final String SEGMENT_CATEGORY = "category";
  private static final String SITE_CHANNEL_ID = "siteChannelID";
  private static final String CATEGORY_PATH_VARIABLE = "categoryPath";
  private static final String CATEGORY_SEO_SEGMENT = "categorySeoSegment";
  private static final String PARAM_START = "start";
  private static final String PARAM_STEPS = "steps";
  private static final String PAGING_VIEW = "productPaging";
  private static final String DEFAULT_STEPS = "" + ProductListSubstitutionService.DEFAULT_STEPS;
  private static final String SEO_URI_PREFIX = "/{language}/{storeName}/";

  private ProductListSubstitutionService productListSubstitutionService;

  // e.g. /category/perfectchef/and/here/comes/a/category/path
  public static final String URI_PATTERN =
          "/" + SEGMENT_CATEGORY +
                  "/{" + SHOP_NAME_VARIABLE + "}" +
                  "/{" + CATEGORY_PATH_VARIABLE + ":" + PATTERN_SEGMENTS + "}";

  public static final String REST_URI_PATTERN = '/' + PREFIX_SERVICE +
          '/' + SEGMENT_REST +
          "/{" + SITE_CHANNEL_ID +
          "}/" + SEGMENT_CATEGORY +
          "/{" + CATEGORY_SEO_SEGMENT + "}";


  @RequestMapping({URI_PATTERN})
  public ModelAndView handleRequest(@PathVariable(SHOP_NAME_VARIABLE) final String shopSegment,
                                    @PathVariable(CATEGORY_PATH_VARIABLE) final String segment,
                                    @RequestParam(value = VIEW_PARAMETER, required = false) final String view) {
    // This handler is only responsible for CAE category links.
    // If the application runs in wcsCategoryLinks mode, we render native
    // WCS links, and this kind of link cannot occur.
    Site site = getSiteResolver().findSiteBySegment(shopSegment);
    if (useCommerceCategoryLinks(site)) {
      return HandlerHelper.notFound("Unsupported link format");
    }

    hasText(shopSegment, "No shop name provided.");
    hasText(segment, "No segment provided.");

    return createLiveContextPage(shopSegment, segment, view);
  }

  @RequestMapping(value = REST_URI_PATTERN, produces = CONTENT_TYPE_HTML, method = RequestMethod.GET)
  @ResponseBody
  public ModelAndView getProducts(@PathVariable(SITE_CHANNEL_ID) CMNavigation context,
                                  @PathVariable(CATEGORY_SEO_SEGMENT) String categorySeoSegment,
                                  @RequestParam(value = PARAM_START, required = false, defaultValue = "0") Integer start,
                                  @RequestParam(value = PARAM_STEPS, required = false, defaultValue = DEFAULT_STEPS) Integer steps) {
    LiveContextNavigation navigation = getLiveContextNavigationFactory().createNavigationBySeoSegment(context.getContent(), categorySeoSegment);
    ProductList productList = productListSubstitutionService.getProductList(navigation, start, steps);
    Page page = asPage(context, context);
    ModelAndView modelAndView = HandlerHelper.createModelWithView(productList, PAGING_VIEW);
    setPage(modelAndView, page);

    //we need to apply the navigation here, otherwise the template lookup can't decide which context to use
    NavigationLinkSupport.setNavigation(modelAndView, page.getNavigation().getRootNavigation());
    return modelAndView;
  }

  @Link(type = LiveContextExternalChannel.class)
  public Object buildLinkForExternalChannel(
          final LiveContextExternalChannel navigation,
          final String viewName,
          final Map<String, Object> linkParameters) {
    // only responsible in non-preview mode
    if(!isPreview()) {
      return buildCatalogLink(navigation, viewName, linkParameters, false);
    }
    return null;
  }

  @Link(type = CMExternalPage.class)
  public Object buildLinkForExternalPage(
          final CMExternalPage navigation,
          final Map<String, Object> linkParameters) {
    return buildNonCatalogLink(navigation, linkParameters);
  }

  @Link(type = LiveContextCategoryNavigation.class)
  public Object buildLinkForCategoryImpl(
          final LiveContextCategoryNavigation navigation,
          final String viewName,
          final Map<String, Object> linkParameters) {
    return buildCatalogLink(navigation, viewName, linkParameters, false);
  }

  @Link(type = CategoryInSite.class)
  public Object buildLinkFor(CategoryInSite categoryInSite, String viewName, Map<String, Object> linkParameters, HttpServletRequest request) {
    return buildCatalogLink(getLiveContextNavigationFactory().createNavigation(categoryInSite.getCategory(), categoryInSite.getSite()), viewName, linkParameters, false);
  }

  @LinkPostProcessor(type = LiveContextExternalChannel.class, order = PostProcessorPrecendences.MAKE_ABSOLUTE)
  public Object makeAbsoluteUri(UriComponents originalUri, LiveContextExternalChannel liveContextNavigation, Map<String,Object> linkParameters, HttpServletRequest request) {
    return doMakeAbsoluteUri(originalUri, liveContextNavigation, linkParameters, request);
  }

  // --------------------  Helper ---------------------------

  public boolean useCommerceCategoryLinks(Site site) {
    return getSettingsService().settingWithDefault(LIVECONTEXT_POLICY_COMMERCE_CATEGORY_LINKS, Boolean.class, false, site);
  }

  private Object doMakeAbsoluteUri(UriComponents originalUri, LiveContextNavigation liveContextNavigation, Map<String,Object> linkParameters, HttpServletRequest request) {
    Site site = liveContextNavigation.getSite();

    // Native category links are absolute anyway, nothing more to do here.
    if (useCommerceCategoryLinks(site)) {
      return originalUri;
    }

    return absoluteUri(originalUri, liveContextNavigation, site, linkParameters, request);
  }

  private Object buildCatalogLink(LiveContextNavigation navigation, String viewName, Map<String, Object> linkParameters, boolean forceCommerceLink) {
    if (getStoreContextProvider().getCurrentContext() != null) {
      Site site = navigation.getSite();
      Category category;
      try {
        category = navigation.getCategory();
      } catch (NotFoundException e) {
        LOG.debug("ignoring commerce exception", e);
        return null;
      }
      if (forceCommerceLink || useCommerceCategoryLinks(site)) {
        String seoSegment = category.getSeoSegment();
        linkParameters = (Map<String, Object>) updateQueryParams(category, linkParameters, seoSegment);
        return buildCommerceLinkFor(null, seoSegment, linkParameters);
      } else {
        return buildCaeLinkForCategory(navigation, viewName, linkParameters);
      }
    }
    return UriComponentsBuilder.newInstance().build();
  }

  private Object buildNonCatalogLink(CMExternalPage navigation, Map<String, Object> linkParameters) {
    if (getStoreContextProvider().getCurrentContext() != null) {
      String urlTemplate = navigation.getExternalUriPath();
      if (isEmpty(navigation.getExternalUriPath())){
        urlTemplate = SEO_URI_PREFIX + navigation.getExternalId();
      }
      return buildCommerceLinkFor(urlTemplate, null, linkParameters);
    }

    return UriComponentsBuilder.newInstance().build();
  }

  private ModelAndView createLiveContextPage(
          @Nonnull final String shopSegment,
          @Nonnull final String segment,
          final String view) {
    Site site = getSiteResolver().findSiteBySegment(shopSegment);
    Navigation context = getNavigationContext(site, segment);
    if (context == null) {
      return HandlerHelper.notFound("No such category");
    }

    Page page = asPage(context, context);
    ModelAndView modelAndView = createModelAndView(page, view);
    modelAndView.addObject(REQUEST_ATTRIBUTE_CATEGORY, context);
    return modelAndView;
  }

  public UriComponents buildCaeLinkForCategory(
          final LiveContextNavigation navigation,
          final String viewName,
          final Map<String, Object> linkParameters) {

    // If there is no root navigation for the given category, it must be a category that is not reachable
    // via the (content based) navigation. This is not an invalid state. There might be another
    // link scheme that is able to produce links to categories, which are not part of the navigation. Hence
    // this link scheme returns null, so that the link formatter may choose a different link scheme.
    Site site = navigation.getSite();
    if (site != null) {
      String siteSegment = getSiteSegment(site);
      Category category;
      try {
        category = navigation.getCategory();
      } catch (NotFoundException e) {
        //if current category is only available in a workspace
        LOG.debug("ignoring commerce exception", e);
        return null;
      }
      if (category != null) {
        String navigationSegment = category.getSeoSegment();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .newInstance()
                .pathSegment(SEGMENT_CATEGORY)
                .pathSegment(siteSegment)
                .pathSegment(navigationSegment);
        addViewAndParameters(uriBuilder, viewName, linkParameters);
        return uriBuilder.build();
      }
    }

    return null;
  }

  // --------------- Config -------------------------

  @Required
  public void setProductListSubstitutionService(ProductListSubstitutionService productListSubstitutionService) {
    this.productListSubstitutionService = productListSubstitutionService;
  }
}
