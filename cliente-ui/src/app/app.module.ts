import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms'
import { AppComponent } from './app.component';
import { ClienteFormComponent } from './componentes/cliente-form/cliente-form.component';
import { ClientesComponent } from './componentes/clientes/clientes.component';
import { registerLocaleData } from '@angular/common';
import { NavBarComponent } from './componentes/nav-bar/nav-bar.component';
import ptBr from '@angular/common/locales/pt';
registerLocaleData(ptBr)

@NgModule({
  declarations: [
    AppComponent,
    ClienteFormComponent,
    ClientesComponent,
    NavBarComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
