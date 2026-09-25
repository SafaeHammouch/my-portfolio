import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Portfolio, PortfolioService, Repository } from './portfolio.service';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  portfolio?: Portfolio;
  repositories: Repository[] = [];
  repositoryError = false;
  active = 'home';


  constructor(private portfolioService: PortfolioService, private sanitizer: DomSanitizer) {}

  safeBio(html: string): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(html);
  }
  ngOnInit(): void {
    this.portfolioService.getPortfolio().subscribe({
      next: data => (this.portfolio = data)
    });

    this.portfolioService.getRepositories().subscribe({
      next: data => (this.repositories = data),
      error: () => (this.repositoryError = true)
    });
  }

  setActive(id: string): void {
    this.active = id;
    document.getElementById(id)?.scrollIntoView({ behavior: 'smooth' });
  }
}