@Configuration
public class CorsConfig implements WebMvcConfigurer {
  private final String[] allowedOrigins;

  public CorsConfig(@Value("${portfolio.allowed-origins}") String[] allowedOrigins) {
    this.allowedOrigins = allowedOrigins;
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
      .allowedOrigins(allowedOrigins)
      .allowedMethods("GET");
  }
}