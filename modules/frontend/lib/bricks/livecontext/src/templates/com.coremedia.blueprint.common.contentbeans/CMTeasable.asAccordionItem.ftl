<#-- @ftlvariable name="self" type="com.coremedia.blueprint.common.contentbeans.CMTeasable" -->

<li class="cm-collection__item">
  <div class="cm-accordion-item"<@preview.metadata self.content />>
    <div class="cm-accordion-item__header"<@preview.metadata "properties.title" />><i class="icon-arrow-right"></i><i class="icon-arrow-down"></i><span> ${self.title!""}</span></div>
    <div class="cm-accordion-item__content"<@preview.metadata "properties.detailText" />><@cm.include self=self.detailText!cm.UNDEFINED /></div>
  </div>
</li>