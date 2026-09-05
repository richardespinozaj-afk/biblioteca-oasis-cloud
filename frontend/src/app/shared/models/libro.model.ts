export interface Libro {
  id: number;
  titulo: string;
  editorial?: { id: number; nombre: string };
  autor?: { id: number; nombre: string };
  materia?: { id: number; nombre: string };
  cantidad: number;
  stockMinimo: number;
  numPag?: number;
  anioEdicion?: number;
  fechaRegistro?: string;
}
