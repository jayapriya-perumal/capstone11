package com.coremedia.blueprint.caas.commerce.model;

import com.coremedia.blueprint.base.livecontext.ecommerce.common.CommerceConnectionInitializer;
import com.coremedia.blueprint.base.livecontext.ecommerce.id.CommerceIdFormatterHelper;
import com.coremedia.blueprint.base.livecontext.ecommerce.id.CommerceIdParserHelper;
import com.coremedia.caas.model.error.SiteIdUndefined;
import com.coremedia.cap.multisite.Site;
import com.coremedia.cap.multisite.SitesService;
import com.coremedia.livecontext.ecommerce.catalog.Catalog;
import com.coremedia.livecontext.ecommerce.catalog.CatalogAlias;
import com.coremedia.livecontext.ecommerce.catalog.CatalogService;
import com.coremedia.livecontext.ecommerce.catalog.Category;
import com.coremedia.livecontext.ecommerce.catalog.Product;
import com.coremedia.livecontext.ecommerce.catalog.ProductVariant;
import com.coremedia.livecontext.ecommerce.common.CommerceBean;
import com.coremedia.livecontext.ecommerce.common.CommerceConnection;
import com.coremedia.livecontext.ecommerce.common.CommerceException;
import com.coremedia.livecontext.ecommerce.common.CommerceId;
import com.coremedia.livecontext.ecommerce.common.CommerceIdProvider;
import com.coremedia.livecontext.ecommerce.common.StoreContext;
import com.coremedia.livecontext.ecommerce.search.SearchFacet;
import com.coremedia.livecontext.ecommerce.search.SearchResult;
import edu.umd.cs.findbugs.annotations.DefaultAnnotation;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import graphql.execution.DataFetcherResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * A simple facade for commerce queries by site id.
 */
@DefaultAnnotation(NonNull.class)
public class CommerceFacade {

  private static final Logger LOG = LoggerFactory.getLogger(CommerceFacade.class);

  private CommerceConnectionInitializer commerceConnectionInitializer;
  private final SitesService sitesService;

  public CommerceFacade(CommerceConnectionInitializer commerceConnectionInitializer, SitesService sitesService) {
    this.commerceConnectionInitializer = commerceConnectionInitializer;
    this.sitesService = sitesService;
  }

  public DataFetcherResult<Product> getProduct(String externalId, String siteId) {
    DataFetcherResult.Builder<Product> builder = DataFetcherResult.<Product>newResult();
    if (siteId == null) {
      return builder.error(SiteIdUndefined.getInstance()).build();
    }
    return builder.data(parseId(externalId, siteId, Product.class)).build();
  }

  public DataFetcherResult<Product> getProductByTechId(String techId, String siteId) {
    DataFetcherResult.Builder<Product> builder = DataFetcherResult.<Product>newResult();
    if (siteId == null) {
      return builder.error(SiteIdUndefined.getInstance()).build();
    }
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return builder.build();
    }
    StoreContext storeContext = connection.getStoreContext();
    CommerceId productCommerceId = connection.getIdProvider().formatProductTechId(storeContext.getCatalogAlias(), techId);
    try {
      CatalogService catalogService = connection.getCatalogService();
      return builder.data(catalogService.findProductById(productCommerceId, storeContext)).build();
    } catch (CommerceException e) {
      LOG.warn("Could not retrieve product for techId {}", techId, e);
      return builder.build();
    }

  }

  public DataFetcherResult<Catalog> getCatalog(String catalogId, String siteId) {
    DataFetcherResult.Builder<Catalog> builder = DataFetcherResult.<Catalog>newResult();
    if (siteId == null) {
      return builder.error(SiteIdUndefined.getInstance()).build();
    }
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return builder.build();
    }
    if (catalogId == null) {
      return builder.data(getDefaultCatalog(siteId)).build();
    }
    return builder.data(parseId(catalogId, siteId, Catalog.class)).build();
  }

  public DataFetcherResult<List<Catalog>> getCatalogs(String siteId) {
    DataFetcherResult.Builder<List<Catalog>> builder = DataFetcherResult.<List<Catalog>>newResult();
    if (siteId == null) {
      return builder.error(SiteIdUndefined.getInstance()).build();
    }
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return builder.build();
    }

    try {
      CatalogService catalogService = connection.getCatalogService();
      return builder.data(catalogService.getCatalogs(connection.getStoreContext())).build();
    } catch (CommerceException e) {
      LOG.warn("Could not retrieve catalogs for siteId {}", siteId, e);
      return builder.build();
    }
  }

  public DataFetcherResult<Category> getCategory(String categoryId, String siteId) {
    DataFetcherResult.Builder<Category> builder = DataFetcherResult.<Category>newResult();
    if (siteId == null) {
      return builder.error(SiteIdUndefined.getInstance()).build();
    }
    return builder.data(parseId(categoryId, siteId, Category.class)).build();
  }

  public DataFetcherResult<Product> findProductBySeoSegment(String seoSegment, String siteId) {
    DataFetcherResult.Builder<Product> builder = DataFetcherResult.<Product>newResult();
    if (siteId == null) {
      return builder.error(SiteIdUndefined.getInstance()).build();
    }
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return builder.build();
    }
    StoreContext storeContext = connection.getStoreContext();
    try {
      CatalogService catalogService = connection.getCatalogService();
      return builder.data(catalogService.findProductBySeoSegment(seoSegment, storeContext)).build();
    } catch (CommerceException e) {
      LOG.warn("Could not retrieve product for seoSegment {}", seoSegment, e);
      return builder.build();
    }
  }

  @SuppressWarnings("unused")
  public DataFetcherResult<ProductVariant> getProductVariant(String productVariantId, String siteId) {
    DataFetcherResult.Builder<ProductVariant> builder = DataFetcherResult.<ProductVariant>newResult();
    if(siteId == null) {
      return builder.error(SiteIdUndefined.getInstance()).build();
    }
    return builder.data(parseId(productVariantId, siteId, ProductVariant.class)).build();
  }

  public DataFetcherResult<Category> findCategoryBySeoSegment(String seoSegment, String siteId) {
    DataFetcherResult.Builder<Category> builder = DataFetcherResult.<Category>newResult();
    if (siteId == null) {
      return builder.error(SiteIdUndefined.getInstance()).build();
    }
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return builder.build();
    }
    StoreContext storeContext = connection.getStoreContext();

    try {
      CatalogService catalogService = connection.getCatalogService();
      return builder.data(catalogService.findCategoryBySeoSegment(seoSegment, storeContext)).build();
    } catch (CommerceException e) {
      LOG.warn("Could not retrieve category by seoSegment {}", seoSegment, e);
      return builder.build();
    }
  }

  @Nullable
  public SearchResult<Product> searchProducts(String searchTerm, Map<String, String> searchParams, String siteId) {
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return null;
    }
    StoreContext storeContext = connection.getStoreContext();

    try {
      CatalogService catalogService = connection.getCatalogService();
      return catalogService.searchProducts(searchTerm, searchParams, storeContext);
    } catch (CommerceException e) {
      LOG.warn("Could not search products with searchTerm {}", searchTerm, e);
      return null;
    }
  }

  @Nullable
  public Map<String, List<SearchFacet>> getFacetsForProductSearch(String categoryId, String siteId) {
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return null;
    }
    StoreContext storeContext = connection.getStoreContext();
    CommerceIdProvider idProvider = connection.getIdProvider();
    CommerceId categoryCommerceId = idProvider.formatCategoryId(storeContext.getCatalogAlias(), categoryId);

    try {
      CatalogService catalogService = connection.getCatalogService();
      Category category = catalogService.findCategoryById(categoryCommerceId, storeContext);
      if (category == null) {
        return null;
      }
      return catalogService.getFacetsForProductSearch(category, storeContext);
    } catch (CommerceException e) {
      LOG.warn("Could not retrieve facets for categoryId {}", categoryId, e);
      return null;
    }
  }

  @Nullable
  public SearchResult<ProductVariant> searchProductVariants(String searchTerm, Map<String, String> searchParams, String siteId) {
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return null;
    }
    StoreContext storeContext = connection.getStoreContext();

    try {
      CatalogService catalogService = connection.getCatalogService();
      return catalogService.searchProductVariants(searchTerm, searchParams, storeContext);
    } catch (CommerceException e) {
      LOG.warn("Could not search product variants with searchTerm {}", searchTerm, e);
      return null;
    }
  }

  public DataFetcherResult<Catalog> getCatalogByAlias(String catalogAlias, String siteId) {
    DataFetcherResult.Builder<Catalog> builder = DataFetcherResult.<Catalog>newResult();
    if (siteId == null) {
      return builder.error(SiteIdUndefined.getInstance()).build();
    }
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return builder.build();
    }
    StoreContext storeContext = connection.getStoreContext();

    try {
      CatalogService catalogService = connection.getCatalogService();
      return builder.data(catalogService
              .getCatalog(CatalogAlias.of(catalogAlias), storeContext)
              .orElse(null)).build();
    } catch (CommerceException e) {
      LOG.warn("Could not retrieve catalog for catalogAlias {}", catalogAlias, e);
      return builder.build();
    }
  }

  @Nullable
  public Catalog getDefaultCatalog(String siteId) {
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return null;
    }
    StoreContext storeContext = connection.getStoreContext();

    try {
      CatalogService catalogService = connection.getCatalogService();
      return catalogService
              .getDefaultCatalog(storeContext)
              .orElse(null);
    } catch (CommerceException e) {
      LOG.warn("Could not retrieve default catalog", e);
      return null;
    }
  }

  @Nullable
  private CommerceConnection getCommerceConnection(String siteId) {
    try {
      Site site = sitesService.getSite(siteId);
      if (site == null) {
        LOG.info("Cannot find site for siteId {}.", siteId);
        return null;
      }
      CommerceConnection connection = commerceConnectionInitializer.findConnectionForSite(site).orElse(null);

      if (connection == null) {
        LOG.warn("Cannot find commerce connection for siteId {}", siteId);
        return null;
      }
      return connection;
    } catch (CommerceException e) {
      LOG.warn("Cannot find commerce connection for siteId {}", siteId, e);
      return null;
    }
  }

  @Nullable
  public String getExternalId(CommerceBean commerceBean) {
    return CommerceIdFormatterHelper.format(commerceBean.getId());
  }

  @Nullable
  private CommerceBean parseId(String id, CommerceConnection commerceConnection) {
    Optional<CommerceId> commerceIdOptional = CommerceIdParserHelper.parseCommerceId(id);
    if (commerceIdOptional.isEmpty()) {
      LOG.debug("unknown id: '{}'", id);
      return null;
    }

    CommerceId commerceId = commerceIdOptional.get();
    StoreContext storeContext = commerceConnection.getStoreContext();

    return commerceConnection.getCommerceBeanFactory().createBeanFor(commerceId, storeContext);
  }

  @Nullable
  private <T extends CommerceBean> T parseId(String id, String siteId, Class<T> expectedType) {
    CommerceConnection connection = getCommerceConnection(siteId);
    if (connection == null) {
      return null;
    }
    CommerceBean bean = parseId(id, connection);
    if (bean == null || !expectedType.isAssignableFrom(bean.getClass())) {
      return null;
    }
    try {
      bean.load();
      //noinspection unchecked
      return (T) bean;
    } catch (CommerceException e) {
      LOG.warn("Could not retrieve product for id {}", id, e);
      return null;
    }
  }
}
