import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Libro } from '../../shared/models/libro.model';

@Injectable({ providedIn: 'root' })
export class LibroService {
  private apiUrl = 'http://localhost:8080/api/libros';

  constructor(private http: HttpClient) {}

  listar(): Observable<Libro[]> {
    return this.http.get<Libro[]>(this.apiUrl);
  }

  buscar(titulo: string): Observable<Libro[]> {
    return this.http.get<Libro[]>(`${this.apiUrl}/buscar?titulo=${titulo}`);
  }

  guardar(libro: Libro): Observable<Libro> {
    return this.http.post<Libro>(this.apiUrl, libro);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
