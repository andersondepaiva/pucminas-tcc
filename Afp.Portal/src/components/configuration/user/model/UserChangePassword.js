export default class UserChangePassword {
  constructor (oldSenha = null,
    senha = null,
    id = null) {
    this.oldSenha = oldSenha
    this.senha = senha
    this.id = id
  }
}
