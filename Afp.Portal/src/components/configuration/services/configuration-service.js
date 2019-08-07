const _urlProjectResource = 'api/katalon-integration/project'
const _urlKatalonStudioResource = 'api/katalon-integration/katalon-studio'
const _urlPessoaResource = 'api/pessoa/usuario'

export default class ConfigurationService {
  constructor (http) {
    this._http = http
  }

  // #region Project
  getProjects (parameters) {
    return this._http
      .get(_urlProjectResource, { params: parameters })
      .then(res => res.json())
  }

  getBranches (parameters) {
    return this._http
      .get(`${_urlProjectResource}/branches`, { params: parameters })
      .then(res => res.json())
  }

  getProjectById (id) {
    return this._http
      .get(`${_urlProjectResource}/${id}`)
      .then(res => res.json())
  }

  getProjectItemsById (id) {
    return this._http
      .get(`${_urlProjectResource}/items/${id}`)
      .then(res => res.json())
  }

  mergeProject (obj) {
    if (obj.id) {
      return this._http.put(_urlProjectResource, obj).then(res => res.json())
    } else {
      return this._http.post(_urlProjectResource, obj).then(res => res.json())
    }
  }

  deleteProject (id) {
    if (id) {
      return this._http.delete(`${_urlProjectResource}/${id}`)
    }
  }

  // #endregion Project

  // #region Katalon Studio
  getKatalonStudioParameters () {
    return this._http.get(_urlKatalonStudioResource).then(res => {
      if (res.body) return res.json()
    })
  }

  mergeKatalonStudio (obj) {
    if (obj.id) {
      return this._http
        .put(_urlKatalonStudioResource, obj)
        .then(res => res.json())
    } else {
      return this._http
        .post(_urlKatalonStudioResource, obj)
        .then(res => res.json())
    }
  }

  // #endregion Katalon Studio

  // #region User
  getUsers (parameters) {
    return this._http
      .get(_urlPessoaResource, { params: parameters })
      .then(res => res.json())
  }

  getUserById (id) {
    return this._http
      .get(`${_urlPessoaResource}/${id}`)
      .then(res => res.json())
  }

  mergeUser (obj) {
    if (obj.id) {
      return this._http.put(_urlPessoaResource, obj).then(res => res.json())
    } else {
      return this._http.post(_urlPessoaResource, obj).then(res => res.json())
    }
  }

  updateProfile (obj) {
    if (obj.id) {
      return this._http.put(`${_urlPessoaResource}/profile`, obj).then(res => res.json())
    }
  }

  changePassword (obj) {
    if (obj) {
      return this._http.put(`${_urlPessoaResource}/password`, obj)
    }
  }

  deleteUser (id) {
    if (id) {
      return this._http.delete(`${_urlPessoaResource}/${id}`)
    }
  }
  // #endregion User
}
