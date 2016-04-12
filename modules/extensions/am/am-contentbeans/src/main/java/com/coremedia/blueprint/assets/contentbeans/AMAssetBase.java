package com.coremedia.blueprint.assets.contentbeans;

import com.coremedia.blueprint.common.contentbeans.CMLocTaxonomy;
import com.coremedia.blueprint.common.contentbeans.CMTaxonomy;
import com.coremedia.cap.common.Blob;
import com.coremedia.cap.common.IdHelper;
import com.coremedia.cap.content.Content;
import com.coremedia.cap.struct.Struct;
import com.coremedia.objectserver.beans.AbstractContentBean;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Base class for beans of document type "AMAsset".
 */
public abstract class AMAssetBase extends AbstractContentBean implements AMAsset {

  @Override
  public int getContentId() {
    return IdHelper.parseContentId(getContent().getId());
  }

  protected Blob getOriginal() {
    return getContent().getBlobRef(ORIGINAL);
  }

  @Override
  public Blob getThumbnail() {
    return getContent().getBlobRef(THUMBNAIL);
  }

  @Override
  public String getTitle() {
    return getContent().getName();
  }

  @Override
  public List<AMTaxonomy> getAssetCategories() {
    List<Content> contents = getContent().getLinks(ASSET_TAXONOMY);
    return createBeansFor(contents, AMTaxonomy.class);
  }

  @Override
  public String getKeywords() {
    return getContent().getString(KEYWORDS);
  }

  @Override
  public List<CMTaxonomy> getSubjectTaxonomy() {
    List<Content> contents = getContent().getLinks(SUBJECT_TAXONOMY);
    return createBeansFor(contents, CMTaxonomy.class);
  }

  @Override
  public List<CMLocTaxonomy> getLocationTaxonomy() {
    List<Content> contents = getContent().getLinks(LOCATION_TAXONOMY);
    return createBeansFor(contents, CMLocTaxonomy.class);
  }

  /**
   * Returns the value of the document property {@link #METADATA}.
   *
   * @return a {@link Struct}
   */
  @Nullable
  @Override
  public Struct getMetadata() {
    return getContent().getStruct(METADATA);
  }
}
