export interface Prestamo {
  id: number;
  estudiante?: { id: number; nombre: string };
  libro?: { id: number; titulo: string };
  cantidad: number;
  fechaPrestamo?: string;
  fechaDevolucion?: string;
  estado: 'activo' | 'devuelto' | 'retrasado';
}
