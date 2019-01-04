import { Component, OnInit } from '@angular/core';
import { Cliente } from 'src/app/model/Cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {

  clientes: Cliente[];
  currentCliente: Cliente = {
    id: null,
    nome: '',
    limCredito: null,
    risco: ''
  };
  isEdit: boolean = false;

  constructor(private _clienteService: ClienteService) { }

  ngOnInit() {
    this.getAllClientes();
  }

  getAllClientes() {
    this._clienteService.getClientes().subscribe(clientes => {
      // console.log('getAllClientes \n'+clientes)
      this.clientes = clientes;
      this.isEdit = false;
      this.currentCliente = {
        id: null,
        nome: '',
        limCredito: null,
        risco: ''
      }
    });
  }

  onUpdatedCliente() {

    this.getAllClientes();

  }

  onNewCliente(cliente: Cliente) {

    null != this.clientes ? this.clientes.unshift(cliente) : this.clientes = [cliente];
    this.currentCliente = {
      id: null,
      nome: '',
      limCredito: null,
      risco: ''
    }
  }

  editCliente(cliente: Cliente) {
    this.currentCliente = cliente;
    this.isEdit = true;
    // console.log(this.currentCliente);
  }

  removeCliente(cliente: Cliente) {
    // console.log(cliente);
    if (confirm('Are you sure to remove ' + cliente.nome + '?')) {
      this._clienteService.removeCliente(cliente.id as number).subscribe(cliente => {

        // console.log('cliente removed ' + cliente.nome)
        this.isEdit = false;
        this.currentCliente = {
          id: null,
          nome: '',
          limCredito: null,
          risco: ''
        };

        this.getAllClientes();
      });
    }

  }

}
