import Pessoa from './Pessoa'

export default class User {
  constructor (login = null,
    senha = null,
    tipoUsuario = null,
    pessoa = new Pessoa(),
    id = null) {
    this.login = login
    this.senha = senha
    this.tipoUsuario = tipoUsuario
    this.pessoa = pessoa
    this.id = id
  }
}
