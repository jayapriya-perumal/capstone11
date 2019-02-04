<#-- @ftlvariable name="self" type="com.coremedia.blueprint.common.contentbeans.CMImageMap" -->
<#-- @ftlvariable name="imagemap_use_quickinfo" type="java.lang.Boolean" -->

<#import "../../freemarkerLibs/image-map.ftl" as imageMap />

<#-- support legacy parameter "imagemap_use_quickinfo" -->
<#assign useQuickinfo=cm.localParameter("useQuickinfo", imagemap_use_quickinfo!true)/>

<#assign imageMapParams=imageMap.generateIds(self)/>
<#assign link=cm.localParameter("renderLink", self.teaserSettings.renderLinkToDetailPage)?then(cm.getLink(self.target!cm.UNDEFINED), "") />

<div class="cm-imagemap" <@cm.dataAttribute name="data-cm-imagemap" data={"coordsBaseWidth": bp.IMAGE_TRANSFORMATION_BASE_WIDTH, "defaultLink": link} />>

  <@cm.include self=self view="_picture" params={
    "blockClass": cm.localParameter("heroBlockClass", "cm-hero"),
    "renderDimmer": cm.localParameter("renderDimmer", true),
    "renderEmptyImage": cm.localParameter("renderEmptyImage", true),
    "limitAspectRatios": cm.localParameter("limitAspectRatios", []),
    "useQuickinfo": useQuickinfo
  } + imageMapParams
  />

  <#--include imagemap quick icons-->
  <#if useQuickinfo>
    <@cm.include self=self view="_areasQuickInfo" params=imageMapParams/>
  </#if>

</div>
