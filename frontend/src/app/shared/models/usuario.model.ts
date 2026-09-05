export interface Usuario {
  id: number;
  usuario: string;
  nombre: string;
  correo: string;
  rol: 'admin' | 'bibliotecario' | 'estudiante';
  estado: boolean;
}

export interface LoginRequest {
  usuario: string;
  clave: string;
}

export interface LoginResponse {
  token: string;
  tipo: string;
  id: number;
  usuario: string;
  nombre: string;
  correo: string;
  rol: string;
}
