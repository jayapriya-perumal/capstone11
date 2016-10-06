/**
 *  CoreMedia Namespace
 */
var coremedia = (function (module) {
  return module;
}(coremedia || {}));
coremedia.preview = (function (module) {
  return module;
}(coremedia.preview || {}));

/**
 * livecontext-theme specific preview javascript
 *
 * Knows modules coremedia.preview and coremedia.blueprint.basic
 */
coremedia.preview.livecontext = (function (module) {
  var $ = coremedia.preview.$;
  $(window).on('message', postMessageHandler);

  const PLACEMENT_EMPTY_CSS = 'cm-lc-fragment-highlighting-green';
  const PLACEMENT_NOT_IN_LAYOUT_CSS = 'cm-lc-fragment-highlighting-orange';
  const PLACEMENT_HAS_ITEMS_CSS = 'cm-lc-fragment-highlighting-blue';
  const PLACEMENT_EMPTY_CSS_TEXT = 'cm-lc-fragment-highlighting-green-text';
  const PLACEMENT_NOT_IN_LAYOUT_CSS_TEXT = 'cm-lc-fragment-highlighting-orange-text';
  const PLACEMENT_EMPTY_CSS_OVERLAY = 'cm-lc-fragment-highlighting-green-overlay';
  const PLACEMENT_NOT_IN_LAYOUT_CSS_OVERLAY = 'cm-lc-fragment-highlighting-orange-overlay';
  const PLACEMENT_HAS_ITEMS_CSS_OVERLAY = 'cm-lc-fragment-highlighting-blue-overlay';
  const BORDER_LEFT_CSS = 'cm-lc-fragment-border-left';
  const BORDER_RIGHT_CSS = 'cm-lc-fragment-border-right';
  const BORDER_TOP_CSS = 'cm-lc-fragment-border-top';
  const BORDER_BOTTOM_CSS = 'cm-lc-fragment-border-bottom';

  const ADD_HIGHLIGHT_MESSAGE_TYPE = 'lc.fragments.addHighlight';
  const REMOVE_HIGHLIGHT_MESSAGE_TYPE = 'lc.fragments.removeHighlight';

  const EMPTY_PLACEMENT_LOCALIZER = 'FragmentHighlighting_emptyPlacement';
  const NOTINLAYOUT_LOCALIZER = 'FragmentHighlighting_notInLayout';
  const PLACEMENTNAME_REPLACER = '(PLACEMENTNAME)';

  function postMessageHandler(event) {
    var msg = event.originalEvent.data;
    var origin = event.originalEvent.origin;
    if (origin === window.com_coremedia_pbe_studioUrl || window.com_coremedia_pbe_studioUrl === "*") {
      var msgJson = undefined;
      try {
        msgJson = JSON.parse(msg);
      } catch (err) {
      }
      if (msgJson) {
        if (msgJson.type === ADD_HIGHLIGHT_MESSAGE_TYPE) {
          var localizationMap = msgJson.body;
          addHighlight(localizationMap);
        } else if (msgJson.type === REMOVE_HIGHLIGHT_MESSAGE_TYPE) {
          removeHighlight();
        }
      }
    }
  }

  function removeHighlight() {
    var relevantItems = getRelevantItems();

    for (i = 0; i < relevantItems.length; i++) {
      var currentItem = relevantItems[i];
      var classList = currentItem.classList;
      var overlayName;
      var textName;
      if(classList.contains(PLACEMENT_NOT_IN_LAYOUT_CSS)) {
        classList.remove(PLACEMENT_NOT_IN_LAYOUT_CSS);
        overlayName = PLACEMENT_NOT_IN_LAYOUT_CSS_OVERLAY;
        textName = PLACEMENT_NOT_IN_LAYOUT_CSS_TEXT;
      } else if(classList.contains(PLACEMENT_EMPTY_CSS)) {
        classList.remove(PLACEMENT_EMPTY_CSS);
        overlayName = PLACEMENT_EMPTY_CSS_OVERLAY;
        textName = PLACEMENT_EMPTY_CSS_TEXT;
      } else if(classList.contains(PLACEMENT_HAS_ITEMS_CSS)) {
        classList.remove(PLACEMENT_HAS_ITEMS_CSS);
        var borderToRemove = currentItem.getElementsByClassName(BORDER_LEFT_CSS);
        for (j = 0; j < borderToRemove.length; j++) {
          currentItem.removeChild(borderToRemove[j]);
        }
        borderToRemove = currentItem.getElementsByClassName(BORDER_TOP_CSS);
        for (j = 0; j < borderToRemove.length; j++) {
          currentItem.removeChild(borderToRemove[j]);
        }
        borderToRemove = currentItem.getElementsByClassName(BORDER_RIGHT_CSS);
        for (j = 0; j < borderToRemove.length; j++) {
          currentItem.removeChild(borderToRemove[j]);
        }
        borderToRemove = currentItem.getElementsByClassName(BORDER_BOTTOM_CSS);
        for (j = 0; j < borderToRemove.length; j++) {
          currentItem.removeChild(borderToRemove[j]);
        }
        overlayName = PLACEMENT_HAS_ITEMS_CSS_OVERLAY;
      }
      // Remove Overlay
      var childsToRemove = currentItem.getElementsByClassName(overlayName);
      for (k = 0; k < childsToRemove.length; k++) {
        currentItem.removeChild(childsToRemove[k]);
      }
      // Remove Text
      childsToRemove = currentItem.getElementsByClassName(textName);
      for (k = 0; k < childsToRemove.length; k++) {
        currentItem.removeChild(childsToRemove[k]);
      }
    }
  }

  function addHighlight(localizationMap) {
    var relevantItems = getRelevantItems();

    for (i = 0; i < relevantItems.length; i++) {
      var currentItem = relevantItems[i];
      var attributeList = $(currentItem).attr('data-cm-metadata');
      var attributes = JSON.parse(attributeList);
      var hasItems;
      var isInLayout;
      var placementName;

      for (var key in attributes) {
        var metadata = attributes[key]['fragmentRequest'];
        if (metadata !== undefined) {
          hasItems = (Boolean)(metadata[0].hasItems);
          isInLayout = (Boolean)(metadata[0].isInLayout);
          placementName = translate(localizationMap, (String)(metadata[0].placementName));
          break;
        }
      }
      var element = document.createElement("div");

      if (!isInLayout) {
        currentItem.classList.add(PLACEMENT_NOT_IN_LAYOUT_CSS);
        element.classList.add(PLACEMENT_NOT_IN_LAYOUT_CSS_OVERLAY);
        var textElement = document.createElement("div")
        var textNode =  document.createTextNode(translate(localizationMap, NOTINLAYOUT_LOCALIZER).replace(PLACEMENTNAME_REPLACER, placementName));
        textElement.appendChild(textNode);
        textElement.classList.add(PLACEMENT_NOT_IN_LAYOUT_CSS_TEXT);
        currentItem.appendChild(textElement);
      } else if (!hasItems) {
        element.classList.add(PLACEMENT_EMPTY_CSS_OVERLAY);
        currentItem.classList.add(PLACEMENT_EMPTY_CSS);
        currentItem.style.paddingLeft = element.width;
        var textElement = document.createElement("div")
        var textNode =  document.createTextNode(translate(localizationMap, EMPTY_PLACEMENT_LOCALIZER));
        textElement.appendChild(textNode);
        textElement.classList.add(PLACEMENT_EMPTY_CSS_TEXT);
        currentItem.appendChild(textElement);
      } else {
        var borderLeft = document.createElement("div");
        borderLeft.classList.add(BORDER_LEFT_CSS);
        currentItem.appendChild(borderLeft);
        var borderRight = document.createElement("div");
        borderRight.classList.add(BORDER_RIGHT_CSS);
        currentItem.appendChild(borderRight);
        var borderTop = document.createElement("div");
        borderTop.classList.add(BORDER_TOP_CSS);
        currentItem.appendChild(borderTop);
        var borderBottom = document.createElement("div");
        borderBottom.classList.add(BORDER_BOTTOM_CSS);
        currentItem.appendChild(borderBottom);
        element.classList.add(PLACEMENT_HAS_ITEMS_CSS_OVERLAY);
        currentItem.classList.add(PLACEMENT_HAS_ITEMS_CSS);
      }
      var placementTextNode =  document.createTextNode(placementName);
      element.appendChild(placementTextNode);
      currentItem.appendChild(element);

      if (!hasItems) {
        currentItem.style.paddingLeft = (element.offsetWidth + 8) + "px";
      }
      currentItem.style.minHeight = (element.offsetHeight) +"px";
    }
  }

  function translate(localizationMap, key) {
    return localizationMap[key];
  }

  function getRelevantItems() {
    return $("[data-cm-metadata*='fragmentRequest']");
  }
  return module;
}(coremedia.preview.livecontext || {}));