package com.coremedia.livecontext.ecommerce.ibm.common;

import com.google.common.annotations.VisibleForTesting;
import edu.umd.cs.findbugs.annotations.NonNull;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @deprecated This class is part of the legacy Blueprint commerce integration and has been deprecated
 * in favour of the Commerce Hub integration.
 */
@Deprecated
class UriEncodingHelper {

  private static final Pattern PLUS = Pattern.compile("\\+");

  private UriEncodingHelper() {
  }

  @NonNull
  @VisibleForTesting
  static UriComponents fixPlusEncoding(@NonNull UriComponents encodedUriComponents) {
    UriComponentsBuilder builder = UriComponentsBuilder.newInstance().uriComponents(encodedUriComponents);

    encodedUriComponents.getQueryParams().forEach((key, value) -> replaceEncodedQueryParam(builder, key, value));

    return builder.build(true);
  }

  private static void replaceEncodedQueryParam(UriComponentsBuilder builder, String paramName, List<String> paramValues) {

    Object[] strings = paramValues.stream()
            .map(s -> PLUS.matcher(s).replaceAll("%2B"))
            .toArray();
    builder.replaceQueryParam(paramName, strings);
  }

}
