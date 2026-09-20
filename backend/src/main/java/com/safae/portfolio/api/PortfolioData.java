package com.safae.portfolio.api;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PortfolioData {
  public PortfolioResponse getPortfolio() {
    return new PortfolioResponse(
      new Profile("Safae Hammouch", "Software engineer building thoughtful web experiences.",
        "I enjoy turning complex ideas into calm, useful products — with a particular interest in Java, Angular, and systems that make work easier.",
        List.of(new Link("GitHub", "https://github.com/SafaeHammouch"), new Link("LinkedIn", "https://linkedin.com/SafaeHammouch"), new Link("Email", "safaehammouch5@gmail.com"))),
      List.of(
        new Project("Your flagship project", "A concise description of the problem, your contribution, and the result. Replace this sample with your best work.", List.of("Angular", "Spring Boot"), "#", "#"),
        new Project("Another meaningful build", "Use this space for a project that demonstrates a different strength: product thinking, backend design, or collaboration.", List.of("Java", "REST API"), "#", "#")),
      List.of(new Experience("Your current or latest role", "Company name", "2024 — Present", "Write two or three lines about the scope of your work, the technologies you used, and an outcome you are proud of.")),
      List.of(new Post("Writing is on its way", "This is where notes on engineering, learning, and building things will live.", "Coming soon")));
  }

  public record PortfolioResponse(Profile profile, List<Project> projects, List<Experience> experience, List<Post> posts) {}
  public record Profile(String name, String title, String bio, List<Link> links) {}
  public record Link(String label, String url) {}
  public record Project(String name, String description, List<String> stack, String repositoryUrl, String liveUrl) {}
  public record Experience(String role, String company, String period, String description) {}
  public record Post(String title, String excerpt, String date) {}
}
