import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet, RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink],
  template: `
    <div class="navbar">
      <div><strong>Biblioteca Oasis</strong></div>
      <div>
        <a routerLink="/libros">Libros</a>
        <a routerLink="/prestamos">Préstamos</a>
        <a routerLink="/estudiantes">Estudiantes</a>
        <a routerLink="/ventas">Ventas</a>
        <a href="#" (click)="logout()" style="color: #ff5252;">Cerrar sesión</a>
      </div>
    </div>
    <div class="container">
      <router-outlet></router-outlet>
    </div>
  `
})
export class DashboardComponent {
  constructor(private authService: AuthService) {}

  logout(): void {
    this.authService.logout();
    window.location.href = '/login';
  }
}
