const _url = 'api/auth/login'

export default class LoginService {
  constructor (http) {
    this._http = http
  }
  login (parameters) {
    return this._http.post(_url, parameters)
  }
}
