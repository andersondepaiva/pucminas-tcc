const _url = 'api/monitoramento-barragens'

export default class MonitoramentoBarragensService {
  constructor (http) {
    this._http = http
  }
  getMonitoramentos (idBarragem) {
    return this._http.get(_url + `/monitoramento-barragens/barragem/${idBarragem}`).then(res => res.json())
  }

  getBarragens (parameters) {
    return this._http
      .get(_url + '/barragens', { params: parameters })
      .then(res => res.json())
  }
}
