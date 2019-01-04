import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Cliente } from 'src/app/model/Cliente';
import { ClienteService } from 'src/app/services/cliente.service';

@Component({
  selector: 'app-cliente-form',
  templateUrl: './cliente-form.component.html',
  styleUrls: ['./cliente-form.component.css']
})
export class ClienteFormComponent implements OnInit {

  @Input() currentCliente: Cliente;
  @Input() isEdit: boolean;
  @Output() newCliente: EventEmitter<Cliente> = new EventEmitter();
  @Output() updatedCliente: EventEmitter<Cliente> = new EventEmitter();
  riscos: Array<Object> = [
    { tipo: 'A' }, { tipo: 'B' }, { tipo: 'C' }
  ];
  // riscoSelected = this.riscos[0];

  constructor(private _clienteService: ClienteService) { }

  ngOnInit() {
  }

  addCliente(cliente: Cliente) {
    if (!cliente || !cliente.nome || !cliente.limCredito || !cliente.risco) {
      alert('dados invalidos!');
    } else {

      // this.formatarLimCredito();
      console.log(this.currentCliente.limCredito)
      this._clienteService.saveCliente(this.currentCliente).subscribe(
        cliente => {
          this.newCliente.emit(cliente);
        });
    }
  }

  updateCliente() {
    this.formatarLimCredito();
    this._clienteService.updateCliente(this.currentCliente).subscribe(cliente => {
      this.isEdit = false;
      this.updatedCliente.emit();
    });
  }

  private formatarLimCredito() {
    let limiteCredito = new String(this.currentCliente.limCredito);
    let result = limiteCredito.replace(".", "");
    result = result.replace(",", ".");
    this.currentCliente.limCredito = Number(result);
    console.log('formatado valor: ',result)
  }

}
