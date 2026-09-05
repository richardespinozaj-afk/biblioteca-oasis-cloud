import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Prestamo } from '../../shared/models/prestamo.model';

@Injectable({ providedIn: 'root' })
export class PrestamoService {
  private apiUrl = 'http://localhost:8080/api/prestamos';

  constructor(private http: HttpClient) {}

  listar(): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(this.apiUrl);
  }

  listarActivos(): Observable<Prestamo[]> {
    return this.http.get<Prestamo[]>(`${this.apiUrl}/activos`);
  }

  crear(prestamo: any, idUsuario: number): Observable<Prestamo> {
    return this.http.post<Prestamo>(`${this.apiUrl}?idUsuario=${idUsuario}`, prestamo);
  }

  devolver(id: number, idUsuario: number): Observable<Prestamo> {
    return this.http.post<Prestamo>(`${this.apiUrl}/devolver/${id}?idUsuario=${idUsuario}`, {});
  }
}
