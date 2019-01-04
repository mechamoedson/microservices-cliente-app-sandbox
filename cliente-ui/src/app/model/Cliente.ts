export interface Cliente {
    id: number
    nome: string,
    limCredito: number,
    risco:string,
    taxaRisco?:number
}