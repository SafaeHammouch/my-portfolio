package com.safae.portfolio.api;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class GitHubService {
  private final RestClient client;
  private final String username;
  private final Duration cacheDuration;
  private volatile List<Repository> cached = List.of();
  private volatile Instant refreshedAt = Instant.EPOCH;

  public GitHubService(
      @Value("${portfolio.github-username}") String username,
      @Value("${portfolio.github-token}") String token,
      @Value("${portfolio.github-cache-minutes}") long cacheMinutes) {
    this.username = username;
    this.cacheDuration = Duration.ofMinutes(cacheMinutes);
    RestClient.Builder builder = RestClient.builder()
      .baseUrl("https://api.github.com/graphql")
      .defaultHeader(HttpHeaders.ACCEPT, "application/vnd.github+json")
      .defaultHeader(HttpHeaders.USER_AGENT, "safae-portfolio");
    if (!token.isBlank()) builder.defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    this.client = builder.build();
  }

  /** Retrieves only repositories pinned on the configured GitHub profile. */
  public synchronized List<Repository> pinnedRepositories() {
    if (Instant.now().isBefore(refreshedAt.plus(cacheDuration))) return cached;
    try {
      GraphQlResponse response = client.post()
        .body(new GraphQlRequest("""
          query($login: String!) {
            user(login: $login) {
              pinnedItems(first: 6, types: REPOSITORY) {
                nodes {
                  ... on Repository {
                    name description url stargazerCount updatedAt isArchived
                    primaryLanguage { name }
                  }
                }
              }
            }
          }
          """, Map.of("login", username)))
        .retrieve()
        .body(GraphQlResponse.class);
      if (response != null && response.data != null && response.data.user != null) {
        cached = response.data.user.pinnedItems.nodes.stream()
          .filter(repo -> !repo.isArchived)
          .map(repo -> new Repository(repo.name, repo.description, repo.url,
            repo.primaryLanguage == null ? null : repo.primaryLanguage.name,
            repo.stargazerCount, repo.updatedAt))
          .toList();
      }
      refreshedAt = Instant.now();
    } catch (Exception ignored) {
      refreshedAt = Instant.now();
    }
    return cached;
  }

  public record Repository(String name, String description, String url, String language, int stars, String updatedAt) {}
  private record GraphQlRequest(String query, Map<String, String> variables) {}
  private record GraphQlResponse(GraphQlData data) {}
  private record GraphQlData(GitHubUser user) {}
  private record GitHubUser(PinnedItems pinnedItems) {}
  private record PinnedItems(List<PinnedRepository> nodes) {}
  private record PinnedRepository(String name, String description, String url, int stargazerCount, String updatedAt, boolean isArchived, Language primaryLanguage) {}
  private record Language(String name) {}
}
