const _url = 'api/monitoramento-barragens'

export default class MonitoramentoBarragensService {
  constructor (http) {
    this._http = http
  }
  getMonitoramentos () {
    return this._http.get(_url + '/monitoramento-barragens/barragem/5d45f1e737eae52f043b44a7').then(res => res.json())
  }
}
