export default class SolicitacaoMensagem {
  constructor (escoposId = [], mensagem = '', motivo = '') {
    this.escoposId = escoposId
    this.mensagem = mensagem
    this.motivo = motivo
  }
}
