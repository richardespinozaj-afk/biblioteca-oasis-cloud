import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="login-container">
      <div class="card" style="max-width: 400px; margin: 100px auto;">
        <h2 style="text-align: center; margin-bottom: 20px;">Biblioteca Oasis</h2>
        <div class="form-group">
          <label>Usuario</label>
          <input type="text" class="form-control" [(ngModel)]="credentials.usuario" placeholder="admin">
        </div>
        <div class="form-group">
          <label>Contraseña</label>
          <input type="password" class="form-control" [(ngModel)]="credentials.clave" placeholder="admin123">
        </div>
        <button class="btn btn-primary" style="width: 100%;" (click)="login()" [disabled]="loading">
          {{ loading ? 'Cargando...' : 'Iniciar sesión' }}
        </button>
        <p *ngIf="error" class="error-msg" style="text-align: center; margin-top: 15px;">{{ error }}</p>
      </div>
    </div>
  `
})
export class LoginComponent {
  credentials = { usuario: '', clave: '' };
  loading = false;
  error = '';

  constructor(private authService: AuthService, private router: Router) {}

  login(): void {
    this.loading = true;
    this.error = '';
    this.authService.login(this.credentials).subscribe({
      next: () => this.router.navigate(['/']),
      error: (err) => {
        this.error = err.error?.mensaje || 'Error al iniciar sesión';
        this.loading = false;
      }
    });
  }
}
