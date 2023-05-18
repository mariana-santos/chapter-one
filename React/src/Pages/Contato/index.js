import React, { useState } from 'react';
import Header from '../../Components/Header';

import './style.css';

export default function Contato() {
  const [nome, setNome] = useState('');
  const [email, setEmail] = useState('');
  const [telefone, setTelefone] = useState('');
  const [mensagem, setMensagem] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Lógica de envio do formulário
    console.log('Formulário enviado!');
    // Limpar os campos do formulário após o envio
    setNome('');
    setEmail('');
    setTelefone('');
    setMensagem('');
  };

  return (
    <>
      <Header />
      <div className="contato">
        <h2>Entre em contato</h2>
        <form className="contato-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="nome">Nome:</label>
            <input
              type="text"
              id="nome"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="email">E-mail:</label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="telefone">Telefone:</label>
            <input
              type="tel"
              id="telefone"
              value={telefone}
              onChange={(e) => setTelefone(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label htmlFor="mensagem">Mensagem:</label>
            <textarea
              id="mensagem"
              value={mensagem}
              onChange={(e) => setMensagem(e.target.value)}
              required
            ></textarea>
          </div>
          <button className="btn-enviar" type="submit">Enviar</button>
        </form>
      </div>
    </>
  );
}
