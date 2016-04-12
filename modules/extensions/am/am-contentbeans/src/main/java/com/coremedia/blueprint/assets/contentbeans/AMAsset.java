package com.coremedia.blueprint.assets.contentbeans;

import com.coremedia.blueprint.common.contentbeans.CMLocTaxonomy;
import com.coremedia.blueprint.common.contentbeans.CMTaxonomy;
import com.coremedia.blueprint.common.datevalidation.ValidityPeriod;
import com.coremedia.cap.common.Blob;
import com.coremedia.cap.struct.Struct;
import com.coremedia.objectserver.beans.ContentBean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * <p>Represents the document type {@link #NAME AMAsset}.</p>
 */
public interface AMAsset extends ContentBean, ValidityPeriod {
  /**
   * {@link com.coremedia.cap.content.ContentType#getName() Name of the ContentType} 'AMAsset'.
   */
  String NAME = "AMAsset";

  /**
   * Name of the document property 'original'.
   */
  String ORIGINAL = "original";

  /**
   * Name of the document property 'thumbnail'.
   */
  String THUMBNAIL = "thumbnail";

  /**
   * Name of the document property 'assetTaxonomy'.
   */
  String ASSET_TAXONOMY = "assetTaxonomy";

  /**
   * Name of the document property 'keywords'.
   */
  String KEYWORDS = "keywords";

  /**
   * Name of the document property 'subjectTaxonomy'.
   */
  String SUBJECT_TAXONOMY = "subjectTaxonomy";

  /**
   * Name of the document property 'locationTaxonomy'.
   */
  String LOCATION_TAXONOMY = "locationTaxonomy";

  String METADATA = "metadata";

  Blob getThumbnail();

  /**
   * returns the CoreMedia internal id of the underlying {@link com.coremedia.cap.content.Content}
   *
   * @return the id as int (without any prefix)
   */
  int getContentId();

  /**
   * Returns the title of the asset
   * @return the title of the asset
   */
  String getTitle();

  /**
   * Returns the value of the document property {@link #ASSET_TAXONOMY}.
   *
   * @return the value of the document property {@link #ASSET_TAXONOMY}
   */
  List<AMTaxonomy> getAssetCategories();

  /**
   * Returns the primary category of the asset which basically is the first
   * category that has been added to the list of asset categories.
   * @return the primary category of the asset or <code>null</code>
   */
  @Nullable
  AMTaxonomy getPrimaryCategory();

  /**
   * Returns the value of the document property {@link #KEYWORDS}.
   *
   * @return the value of the document property {@link #KEYWORDS}
   */
  String getKeywords();

  /**
   * Returns the value of the document property {@link #SUBJECT_TAXONOMY}.
   *
   * @return a list of {@link com.coremedia.blueprint.common.contentbeans.CMTaxonomy} objects
   */
  List<CMTaxonomy> getSubjectTaxonomy();

  /**
   * Returns the value of the document property {@link #LOCATION_TAXONOMY}.
   *
   * @return a list of {@link com.coremedia.blueprint.common.contentbeans.CMLocTaxonomy} objects
   */
  List<CMLocTaxonomy> getLocationTaxonomy();

  /**
   * Returns the list of all subject taxonomy nodes directly and indirectly linked to this asset.
   * @return the list of all subjects directly and indirectly linked to this asset.
   */
  @Nonnull
  List<CMTaxonomy> getAllSubjects();

  /**
   * Returns the asset's information metadata (e.g. copyright)
   * @return the asset's information metadata
   */
  @Nullable
  Struct getMetadata();

  /**
   * The list of all published and non-published asset renditions.
   * @return all published and non-published asset renditions or an empty list
   */
  @Nonnull
  List<AMAssetRendition> getRenditions();

  /**
   * Returns the list of all published renditions
   * @return the list of all published renditions or an empty list
   */
  @Nonnull
  List<AMAssetRendition> getPublishedRenditions();
}
