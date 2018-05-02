package com.coremedia.livecontext.ecommerce.sfcc.catalog;

import com.coremedia.blueprint.base.livecontext.ecommerce.common.CatalogAliasTranslationService;
import com.coremedia.blueprint.lc.test.CatalogServiceBaseTest;
import com.coremedia.livecontext.ecommerce.catalog.CatalogId;
import com.coremedia.livecontext.ecommerce.catalog.Category;
import com.coremedia.livecontext.ecommerce.catalog.Product;
import com.coremedia.livecontext.ecommerce.catalog.ProductAttribute;
import com.coremedia.livecontext.ecommerce.catalog.ProductVariant;
import com.coremedia.livecontext.ecommerce.sfcc.SfccTestInitializer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static com.coremedia.blueprint.lc.test.BetamaxTestHelper.useBetamaxTapes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceTestBaseConfiguration.class, initializers = SfccTestInitializer.class)
public class CatalogServiceImplIT extends CatalogServiceBaseTest {

  @MockBean
  private CatalogAliasTranslationService catalogAliasTranslationService;

  @Inject
  CatalogServiceImpl testling;

  @Value("${PRODUCT_CODE}")
  protected String PRODUCT_CODE;

  @Value("${PRODUCT_CODE_B2B}")
  protected String PRODUCT_CODE_B2B;

  @Value("${PRODUCT_NAME}")
  protected String PRODUCT_NAME;

  @Value("${PRODUCT_CODE_WITH_SLASH}")
  protected String PRODUCT_CODE_WITH_SLASH;

  @Value("${PRODUCT_SEO_SEGMENT}")
  protected String PRODUCT_SEO_SEGMENT;

  @Value("${CATEGORY_CODE}")
  protected String CATEGORY_CODE;

  @Value("${CATEGORY_CODE_B2B}")
  protected String CATEGORY_CODE_B2B;

  @Value("${CATEGORY_WITH_SLASH}")
  protected String CATEGORY_WITH_SLASH;

  @Value("${CATEGORY_SEO_SEGMENT}")
  protected String CATEGORY_SEO_SEGMENT;

  @Value("${CATEGORY_SEO_SEGMENT_DE}")
  protected String CATEGORY_SEO_SEGMENT_DE;

  @Value("${CATEGORY_NAME}")
  protected String CATEGORY_NAME;

  @Value("${PRODUCT_VARIANT_CODE}")
  protected String PRODUCT_VARIANT_CODE;

  @Value("${PRODUCT_VARIANT_WITH_SLASH}")
  protected String PRODUCT_VARIANT_WITH_SLASH;

  @Value("${SEARCH_TERM_1}")
  protected String SEARCH_TERM_1;

  @Value("${SEARCH_TERM_2}")
  protected String SEARCH_TERM_2;

  @Value("${SEARCH_TERM_3}")
  protected String SEARCH_TERM_3;

  @Value("${TOP_CATEGORY_NAME}")
  protected String TOP_CATEGORY_NAME;

  @Value("${LEAF_CATEGORY_CODE}")
  protected String LEAF_CATEGORY_CODE;

  @Value("${FILTER_NAME}")
  protected String FILTER_NAME;

  @Value("${FILTER_VALUE}")
  protected String FILTER_VALUE;

  @Before
  public void setup() {
    super.setup();
    when(catalogAliasTranslationService.getCatalogIdForAlias(any(), any())).thenReturn(Optional.of(CatalogId.of("storefront-catalog-non-en")));
  }

  @Test
  public void testProductVariantAttributes() {
    if (useBetamaxTapes()) {
      return;
    }

    Product tg250 = testling.findProductById(getIdProvider().formatProductId(null, "TG250"), getStoreContext());
    ProductVariant productVariantA = tg250.getVariants().get(0);
    ProductVariant productVariantB = tg250.getVariants().get(1);

    List<ProductAttribute> definingAttributesA = productVariantA.getDefiningAttributes();
    List<ProductAttribute> definingAttributesB = productVariantB.getDefiningAttributes();

    assertThat(definingAttributesA.size()).isEqualTo(definingAttributesB.size());

    String colorA = (String) productVariantA.getAttributeValue(definingAttributesA.get(0).getId());
    String colorB = (String) productVariantB.getAttributeValue(definingAttributesB.get(0).getId());
    assertThat(colorA).isEqualTo(colorB);
    assertThat(colorA).isEqualTo("Black");

    String sizeA = (String) productVariantA.getAttributeValue(definingAttributesA.get(1).getId());
    String sizeB = (String) productVariantB.getAttributeValue(definingAttributesB.get(1).getId());
    assertThat(sizeA).isNotEqualTo(sizeB);
  }

  @Test
  public void testFindProductByIdNotFound() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindProductByIdNotFound();
  }

  @Test
  public void testFindProductById() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindProductById();
  }

  @Test
  public void testFindProductVariantById() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindProductVariantById();
  }

  @Test
  public void testFindProductsByCategory() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindProductsByCategory();
  }

  @Test
  public void testFindProductsByCategoryIsEmpty() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindProductsByCategoryIsEmpty();
  }

  @Test
  public void testFindProductsByCategoryIsRoot() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindProductsByCategoryIsRoot();
  }

  @Test
  public void testFindTopCategories() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindTopCategories();
  }

  @Test
  public void testFindRootCategory() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindRootCategory();
  }

  @Test
  public void testFindSubCategories() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindSubCategories();
  }

  @Test
  public void testFindSubCategoriesIsEmpty() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindSubCategoriesIsEmpty();
  }

  @Test
  public void testFindCategoryById() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindCategoryById();
  }

  @Test
  public void testFindCategoryByIdIsNull() {
    if (useBetamaxTapes()) {
      return;
    }

    super.testFindCategoryByIdIsNull();
  }

  @Test
  public void testSearchProducts() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testSearchProducts();
  }

  @Test
  public void testSearchProductsWithOffset() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testSearchProductsWithOffset();
  }

  @Test
  public void testSearchProductVariants() throws Exception {
    if (useBetamaxTapes()) {
      return;
    }

    super.testSearchProductVariants();
  }

  @Test
  public void testWithStoreContext() {
    if (useBetamaxTapes()) {
      return;
    }

    super.testWithStoreContext();
  }

  protected void assertProduct(Product product) {
    assertThat(product).isNotNull();
    assertThat(product.getName()).isNotEmpty();
    assertThat(product.getDefaultImageUrl()).endsWith(".jpg");
    assertThat(product.getThumbnailUrl()).endsWith(".jpg");
    assertThat(product.getCategory()).isNotNull();

    // test attributes
    /*assertThat(product.getDefiningAttributes()).isNotEmpty();*/

    // test variants
    List<ProductVariant> variants = product.getVariants();
    assertThat(variants).isNotEmpty();

    // test axis filter
    /*List<String> variantAxisNames = product.getVariantAxisNames();
    if (!variantAxisNames.isEmpty()) {
      List<ProductVariant> filteredVariants = product.getVariants(new AxisFilter(variantAxisNames.get(0), "*"));
      assertThat(variants.size()).isGreaterThanOrEqualTo(filteredVariants.size());
    }*/
  }

  @Override
  protected void assertProductVariant(ProductVariant productVariant) {
    assertThat(productVariant).isNotNull();

    Product parentProduct = productVariant.getParent();
    assertThat(parentProduct).isNotNull();
    assertThat(productVariant.getName()).isNotNull();
    assertThat(productVariant.getDefaultImageUrl()).endsWith(".jpg");
    assertThat(productVariant.getThumbnailUrl()).endsWith(".jpg");
    // OfferPrice not yet available
    //assertThat(productVariant.getOfferPrice()).isNotNull();
  }

  @Override
  protected void assertCategory(Category category) {
    assertThat(category).isNotNull();
    assertThat(category.getExternalId()).isNotEmpty();
    assertThat(category.getName()).isNotEmpty();
    assertThat(category.getParent()).isNotNull();
    // do not chech image data, since images are not always given
    //assertThat(category.getDefaultImageUrl()).isNotNull();
    // we do not have test data with thumbnail
    //assertThat(category.getThumbnailUrl()).isNotNull();
    assertThat(category.getShortDescription()).isNotNull();
    assertThat(category.getChildren()).isNotNull();
    assertThat(category.getProducts()).isNotNull();

    List<Category> categoryBreadcrumb = category.getBreadcrumb();
    assertThat(categoryBreadcrumb).isNotEmpty();
    assertThat(categoryBreadcrumb.get(categoryBreadcrumb.size() - 1)).isEqualTo(category);
    assertThat(category.getLocale()).isNotNull();
  }
}
