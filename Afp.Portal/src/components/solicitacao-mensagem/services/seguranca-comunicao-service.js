const _url = 'api/seguranca-comunicacao'

export default class MonitoramentoBarragensService {
  constructor (http) {
    this._http = http
  }

  postSolicitacaoMensagem (obj) {
    return this._http.post(_url + `/solicitacoes-mensagem`, obj).then(res => res.json())
  }

  getEscopos () {
    return this._http
      .get(_url + '/escopos')
      .then(res => res.json())
  }
}
