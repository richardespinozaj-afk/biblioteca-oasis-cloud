import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PrestamoService } from '../../core/services/prestamo.service';
import { Prestamo } from '../../shared/models/prestamo.model';

@Component({
  selector: 'app-prestamo-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card">
      <h2>Gestión de Préstamos</h2>
      <table class="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Estudiante</th>
            <th>Libro</th>
            <th>Cantidad</th>
            <th>Fecha Préstamo</th>
            <th>Estado</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let p of prestamos">
            <td>{{ p.id }}</td>
            <td>{{ p.estudiante?.nombre }}</td>
            <td>{{ p.libro?.titulo }}</td>
            <td>{{ p.cantidad }}</td>
            <td>{{ p.fechaPrestamo }}</td>
            <td>
              <span [style.color]="p.estado === 'activo' ? '#d32f2f' : '#388e3c'">{{ p.estado }}</span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class PrestamoListComponent implements OnInit {
  prestamos: Prestamo[] = [];

  constructor(private prestamoService: PrestamoService) {}

  ngOnInit(): void {
    this.prestamoService.listar().subscribe(data => this.prestamos = data);
  }
}
