package com.coremedia.blueprint.assets.cae;

import com.coremedia.blueprint.assets.contentbeans.AMAsset;
import com.coremedia.blueprint.assets.contentbeans.AMTaxonomy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Represents the combination of an asset and one of its categories.
 */
public class AssetDetails implements DownloadPortalContext {

  private AMAsset asset;

  private AMTaxonomy category;

  private Map<String, String> metadataProperties;

  public AssetDetails(@Nonnull AMAsset asset, @Nullable AMTaxonomy category) {
    this.asset = asset;
    this.category = category;
  }

  @Nonnull
  public AMAsset getAsset() {
    return asset;
  }

  @Nullable
  public AMTaxonomy getCategory() {
    return category;
  }

  @Nullable
  public Map<String, String> getMetadataProperties() {
    return metadataProperties;
  }

  public void setMetadataProperties(Map<String, String> metadataProperties) {
    this.metadataProperties = metadataProperties;
  }

  @Override
  public String getSearchTerm() {
    return "";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    AssetDetails that = (AssetDetails) o;

    return asset.equals(that.asset) && (category == null ? that.category == null : category.equals(that.category));

  }

  @Override
  public int hashCode() {
    int result = asset.hashCode();
    result = 31 * result + (category != null ? category.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "{" +
            "asset=" + asset +
            ", category=" + category +
            '}';
  }

}
