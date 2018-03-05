package com.coremedia.livecontext.ecommerce.sfcc.ocapi;

import com.coremedia.blueprint.base.livecontext.ecommerce.common.CommercePropertyHelper;
import com.coremedia.blueprint.base.livecontext.ecommerce.common.CurrentCommerceConnection;
import com.coremedia.livecontext.ecommerce.common.CommerceConnection;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.charset.Charset;
import java.util.Optional;

/**
 * Salesforce Commerce Cloud Open Commerce API OAuth connector.
 */
@Service("sfccOAuthConnector")
public class OAuthConnector {

  private static final Logger LOG = LoggerFactory.getLogger(OAuthConnector.class);

  // --- HTTP Header constants ---
  private static final String AUTHORIZATION_HEADER = "Authorization";

  private final String protocol;
  private final String host;
  private final String path;
  private final String clientId;
  private final String password;

  private final RestTemplate restTemplate = new RestTemplate();

  private volatile AccessToken accessToken;

  OAuthConnector(@Nonnull SfccOAuthConfigurationProperties properties) {
    protocol = properties.getProtocol();
    host = properties.getHost();
    path = properties.getPath();
    clientId = properties.getClientId();
    password = properties.getClientPassword();
  }

  /**
   * Requests an access token from the configured Account Manager host using the configured clientId and password.
   *
   * @return AccesToken
   */
  @Nullable
  private AccessToken requestAccessToken() {
    String url = buildRequestUrl();
    HttpEntity<String> requestEntity = buildRequestEntity(getClientId(), getPassword());

    LOG.info("Requesting access token. {}", url);
    try {
      ResponseEntity<AccessToken> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity,
              AccessToken.class);

      if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
        LOG.warn("Unable to request authentication token. Check credentials!");
        return null;
      }

      AccessToken result = responseEntity.getBody();
      LOG.info("Fetched access token. {}", result);
      return result;
    } catch (HttpClientErrorException e) {
      LOG.error("Token request '{}' returned with HTTP status code {} ({})", url, e.getStatusCode(),
              e.getLocalizedMessage());
      return null;
    }
  }

  @Nonnull
  private String buildRequestUrl() {
    return UriComponentsBuilder.newInstance()
            .scheme(protocol)
            .host(host)
            .path(path)
            .build().toString();
  }

  @Nonnull
  private static HttpEntity<String> buildRequestEntity(@Nonnull String clientId, @Nonnull String password) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    headers.add(AUTHORIZATION_HEADER, createAuthorizationHeaderValue(clientId, password));

    return new HttpEntity<>("grant_type=client_credentials", headers);
  }

  @Nonnull
  private static String createAuthorizationHeaderValue(@Nonnull String clientId, @Nonnull String password) {
    // Create basic auth header
    String auth = clientId + ":" + password;

    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));

    return "Basic " + new String(encodedAuth);
  }

  @Nonnull
  public Optional<AccessToken> getOrRequestAccessToken() {
    if (accessToken == null || accessToken.isExpired()) {
      accessToken = requestAccessToken();
    }

    return Optional.ofNullable(accessToken);
  }

  private String getClientId() {
    return CommercePropertyHelper.replaceTokens(clientId,
            CurrentCommerceConnection.find().map(CommerceConnection::getStoreContext).orElse(null));
  }

  private String getPassword() {
    return CommercePropertyHelper.replaceTokens(password,
            CurrentCommerceConnection.find().map(CommerceConnection::getStoreContext).orElse(null));
  }
}
