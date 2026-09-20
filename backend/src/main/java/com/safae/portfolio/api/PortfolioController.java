package com.safae.portfolio.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PortfolioController {
  private final PortfolioData portfolioData;
  private final GitHubService gitHubService;

  public PortfolioController(PortfolioData portfolioData, GitHubService gitHubService) {
    this.portfolioData = portfolioData;
    this.gitHubService = gitHubService;
  }

  @GetMapping("/portfolio")
  public PortfolioData.PortfolioResponse portfolio() { return portfolioData.getPortfolio(); }

  @GetMapping("/github/repositories")
  public ResponseEntity<List<GitHubService.Repository>> repositories() {
    return ResponseEntity.ok(gitHubService.pinnedRepositories());
  }
}
