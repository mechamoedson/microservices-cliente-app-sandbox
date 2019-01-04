import { Injectable } from '@angular/core';
import { Cliente } from '../model/Cliente';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const httpOptions = {
  headers: new HttpHeaders({ 'content-type': 'application/json' })
}

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  clienteUrl: string = 'http://localhost:8331/api/client/cliente'

  constructor(private http: HttpClient) { }

  getClientes(): Observable<Cliente[]> {

    return this.http.get<Cliente[]>(this.clienteUrl);
  }

  getCliente(id: number): Observable<Cliente> {
    
    const url = `${this.clienteUrl}/${id}`;
    return this.http.get<Cliente>(url);
  }

  saveCliente(cliente: Cliente): Observable<Cliente> {

    return this.http.post<Cliente>(this.clienteUrl, cliente, httpOptions);
  }

  updateCliente(cliente: Cliente): Observable<Cliente> {

    const url = `${this.clienteUrl}/${cliente.id}`;
    return this.http.put<Cliente>(url, cliente, httpOptions);
  }

  removeCliente(post: Cliente | number): Observable<Cliente>{
    
    const id = typeof post === 'number' ? post : post.id;
    const url = `${this.clienteUrl}/${id}`;

    return this.http.delete<Cliente>(url, httpOptions);
  }
}
