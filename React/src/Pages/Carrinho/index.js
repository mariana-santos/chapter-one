import React from 'react';
import { Link } from 'react-router-dom';

import Header from '../../Components/Header';

import './style.css'

const CarrinhoPage = () => {
  return (
    <>
      <Header />
      <div className="page-content"></div>
      <div className="carrinho-container">
        <h2>Meu Carrinho</h2>

        <div className="carrinho-itens">
          <div className="carrinho-item">
            <img src="imagem-livro-1.jpg" alt="Livro 1" />
            <div className="carrinho-item-info">
              <h3>Livro 1</h3>
              <p>Descrição do livro 1</p>
              <p>R$ 29,90</p>
            </div>
            <div className="carrinho-item-quantidade">
              <button className="diminuir-qtd">-</button>
              <span>1</span>
              <button className="aumentar-qtd">+</button>
            </div>
          </div>

          <div className="carrinho-item">
            <img src="imagem-livro-2.jpg" alt="Livro 2" />
            <div className="carrinho-item-info">
              <h3>Livro 2</h3>
              <p>Descrição do livro 2</p>
              <p>R$ 19,90</p>
            </div>
            <div className="carrinho-item-quantidade">
              <button className="diminuir-qtd">-</button>
              <span>1</span>
              <button className="aumentar-qtd">+</button>
            </div>
          </div>
        </div>

        <div className="carrinho-total">
          <h4>Total:</h4>
          <p>R$ 49,80</p>
        </div>

        <div className="carrinho-botoes">
          <Link to="/loja" className="carrinho-continuar-comprando">
            Continuar Comprando
          </Link>
          <button className="carrinho-finalizar-compra">Finalizar Compra</button>
        </div>
      </div>
    </>
  );
};

export default CarrinhoPage;
