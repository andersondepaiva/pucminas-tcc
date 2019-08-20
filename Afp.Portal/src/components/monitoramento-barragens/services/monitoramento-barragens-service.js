const _url = 'api/monitoramento-barragens'

export default class MonitoramentoBarragensService {
  constructor (http) {
    this._http = http
  }
  getMonitoramentos () {
    return this._http.get(_url + '/monitoramento-barragens').then(res => res.json())
  }
}
