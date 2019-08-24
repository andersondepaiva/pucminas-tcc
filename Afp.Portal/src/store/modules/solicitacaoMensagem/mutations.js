import SolicitacaoMensagem from '../../../components/solicitacao-mensagem/model/solicitacao-mensagem'

const mutations = {
  destructor (state) {
    state.solicitacaoMensagem = null
    state.isChangeSolicitacao = false
  },
  constructor (state, { mensagem, motivo, escopos }) {
    state.solicitacaoMensagem = new SolicitacaoMensagem()
    state.solicitacaoMensagem.mensagem = mensagem
    state.solicitacaoMensagem.motivo = motivo
    state.solicitacaoMensagem.escopos = escopos
  },
  setChangeSolicitacao (state, { isChange }) {
    state.isChangeSolicitacao = isChange
  }
}

export default mutations
