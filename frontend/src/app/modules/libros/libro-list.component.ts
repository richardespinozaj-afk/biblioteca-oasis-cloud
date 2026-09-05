import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LibroService } from '../../core/services/libro.service';
import { Libro } from '../../shared/models/libro.model';

@Component({
  selector: 'app-libro-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  template: `
    <div class="card">
      <h2>Catálogo de Libros</h2>
      <div class="form-group" style="margin-top: 15px;">
        <input type="text" class="form-control" [(ngModel)]="busqueda" placeholder="Buscar por título..." (keyup.enter)="buscar()">
      </div>
      <table class="table">
        <thead>
          <tr>
            <th>Título</th>
            <th>Autor</th>
            <th>Editorial</th>
            <th>Materia</th>
            <th>Stock</th>
            <th>Mínimo</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let libro of libros">
            <td>{{ libro.titulo }}</td>
            <td>{{ libro.autor?.nombre }}</td>
            <td>{{ libro.editorial?.nombre }}</td>
            <td>{{ libro.materia?.nombre }}</td>
            <td>{{ libro.cantidad }}</td>
            <td>{{ libro.stockMinimo }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class LibroListComponent implements OnInit {
  libros: Libro[] = [];
  busqueda = '';

  constructor(private libroService: LibroService) {}

  ngOnInit(): void {
    this.cargarLibros();
  }

  cargarLibros(): void {
    this.libroService.listar().subscribe(data => this.libros = data);
  }

  buscar(): void {
    if (this.busqueda.trim()) {
      this.libroService.buscar(this.busqueda).subscribe(data => this.libros = data);
    } else {
      this.cargarLibros();
    }
  }
}
