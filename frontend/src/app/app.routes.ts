import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: 'login', loadComponent: () => import('./modules/auth/login.component').then(m => m.LoginComponent) },
  {
    path: '',
    canActivate: [authGuard],
    loadComponent: () => import('./modules/dashboard/dashboard.component').then(m => m.DashboardComponent),
    children: [
      { path: 'libros', loadComponent: () => import('./modules/libros/libro-list.component').then(m => m.LibroListComponent) },
      { path: 'prestamos', loadComponent: () => import('./modules/prestamos/prestamo-list.component').then(m => m.PrestamoListComponent) },
      { path: 'estudiantes', loadComponent: () => import('./modules/estudiantes/estudiante-list.component').then(m => m.EstudianteListComponent) },
      { path: 'ventas', loadComponent: () => import('./modules/ventas/venta-list.component').then(m => m.VentaListComponent) },
      { path: '', redirectTo: 'libros', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: 'login' }
];
