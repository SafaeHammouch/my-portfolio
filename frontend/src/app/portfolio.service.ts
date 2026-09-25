import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

export interface Link {
  label: string;
  url: string;
}

export interface Profile {
  name: string;
  title: string;
  bio: string;
  links: Link[];
}

export interface ExperienceItem {
  period: string;
  role: string;
  company: string;
  description: string;
}

export interface Post {
  title: string;
  excerpt: string;
  date: string;
}

export interface Portfolio {
  profile: Profile;
  experience: ExperienceItem[];
  posts: Post[];
}

export interface Repository {
  name: string;
  description: string | null;
  url: string;
  language: string | null;
  stars: number;
}

interface GitHubRepoResponse {
  name: string;
  description: string | null;
  html_url: string;
  language: string | null;
  stargazers_count: number;
  fork: boolean;
}

const GITHUB_USERNAME = 'SafaeHammouch';
const REPO_COUNT = 6;

@Injectable({ providedIn: 'root' })
export class PortfolioService {
  constructor(private http: HttpClient) {}

  getPortfolio(): Observable<Portfolio> {
    const portfolio: Portfolio = {
      profile: {
        name: 'Safae Hammouch',
        title: 'Software & Intelligent Systems Engineer',
bio: "Junior software engineer, early career — wiring Java and Spring Boot together, occasionally poking around the AI bubble to see what's real. Just wrapped an internship on OCI at <a href=\"https://www.oracle.com\" target=\"_blank\" rel=\"noreferrer\">Oracle</a>, with stops in document intelligence and anomaly detection before that. Open to full-time opportunities, based in Morocco but open to relocating. Constantly learning, constantly building.",        links: [
          { label: 'Email', url: 'mailto:safaehammouch5@gmail.com' },
          { label: 'GitHub', url: 'https://github.com/SafaeHammouch' },
          { label: 'LinkedIn', url: 'https://www.linkedin.com/in/safae-hammouch/' }
        ]
      },
      experience: [
        {
          period: 'Feb 2026 — Aug 2026',
          role: 'Software Engineering Intern — OCI IAM Feature Development & Automation',
          company: 'Oracle, Casablanca, Morocco',
          description:
            'Developed and maintained TypeScript features for OCI Identity Domains within an international UX team, applying Oracle Redwood standards and integrating IAM administration workflows with Java backend components and REST APIs. Contributed to code quality through defect resolution, testing, code reviews, and deployment across the delivery lifecycle, and designed containerized MCP-based workflows giving on-call engineers traceable technical analysis.'
        },
        {
          period: 'Dec 2025 — Feb 2026',
          role: 'Software Engineering Intern — NLP, LLM & Document Intelligence',
          company: 'AriMayi, Remote',
          description:
            'Contributed to modular document-processing components for business workflows, covering ingestion, LLM-assisted synthesis, validation, traceability, and structured outputs.'
        },
        {
          period: 'Aug 2025 — Oct 2025',
          role: 'Software Engineering Intern — AI Development & Anomaly Detection',
          company: 'Oracle, Casablanca, Morocco',
          description:
            'Built a modular Python anomaly-detection tool for multi-format data, with configurable preprocessing, report generation, visualizations, and automated tests.'
        }
      ],
      posts: []
    };

    return of(portfolio);
  }

  getRepositories(): Observable<Repository[]> {
    return this.http.get<Repository[]>('http://localhost:8080/api/github/repositories');
}
}