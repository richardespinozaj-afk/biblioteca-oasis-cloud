import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-venta-list',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div class="card">
      <h2>Ventas</h2>
      <p>Módulo de ventas de material bibliográfico. (Próximamente)</p>
    </div>
  `
})
export class VentaListComponent {}
