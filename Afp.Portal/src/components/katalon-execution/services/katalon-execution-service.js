const _url = 'api/katalon-integration/katalon-execution'

export default class KatalonExecutionService {
  constructor (http) {
    this._http = http
  }
  get (parameters) {
    return this._http.get(_url, { params: parameters }).then(res => res.json())
  }

  queueExecution (obj) {
    return this._http.post(_url, obj)
  }

  getReportTestExecution (id) {
    if (id) {
      return this._http
        .get(_url + '/report-test/' + id, { responseType: 'arraybuffer' })
        .then(response => {
          return {
            blobFile: new Blob([response.data], {
              type: response.headers.get('content-type')
            }),
            fileName: response.headers.get('file-name')
          }
        })
    }
  }

  getLogFileExecution (id) {
    if (id) {
      return this._http.get(_url + '/log/' + id).then(res => res.body)
    }
  }
}
