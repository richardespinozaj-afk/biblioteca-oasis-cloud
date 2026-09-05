import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EstudianteService } from '../../core/services/estudiante.service';
import { Estudiante } from '../../shared/models/estudiante.model';

@Component({
  selector: 'app-estudiante-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="card">
      <h2>Estudiantes</h2>
      <table class="table">
        <thead>
          <tr>
            <th>Código</th>
            <th>Nombre</th>
            <th>Documento</th>
            <th>Carrera</th>
            <th>Teléfono</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let e of estudiantes">
            <td>{{ e.codigo }}</td>
            <td>{{ e.nombre }}</td>
            <td>{{ e.documento }}</td>
            <td>{{ e.carrera }}</td>
            <td>{{ e.telefono }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class EstudianteListComponent implements OnInit {
  estudiantes: Estudiante[] = [];

  constructor(private estudianteService: EstudianteService) {}

  ngOnInit(): void {
    this.estudianteService.listar().subscribe(data => this.estudiantes = data);
  }
}
