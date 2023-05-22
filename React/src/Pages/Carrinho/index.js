import React, { useContext, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';

import Header from '../../Components/Header';
import CarrinhoItem from '../../Components/CarrinhoItem';

import './style.css';

import { CarrinhoContext } from '../../App';
import { formatarPreco } from '../../Assets/Utils';
import { HiOutlineArrowNarrowRight } from 'react-icons/hi';

const CarrinhoPage = () => {
  const { carrinho } = useContext(CarrinhoContext);
  const [total, setTotal] = useState(0);

  useEffect(() => {
    let newTotal = 0;

    carrinho.forEach((livro) => {
      newTotal += (livro.preco - livro.desconto) * livro.qtd;
    });

    setTotal(newTotal);
  }, [carrinho]);

  return (
    <>
      <Header />
      <div className="container">
        <div id="carrinho-container">
          <h2>Meu Carrinho</h2>

          <div className="carrinho-itens">
            {carrinho.map((livro) => (
              <CarrinhoItem livro={livro} key={livro.id} />
            ))}
          </div>

          <div className="carrinho-total">
            <h4>Total:</h4>
            <p>{formatarPreco(total)}</p>
          </div>

          <div className="carrinho-botoes">
            <Link to="/loja" className="btn btn-secondary">
              Continuar Comprando
            </Link>
            <button className="btn carrinho-finalizar-compra">
              Finalizar Compra
              <HiOutlineArrowNarrowRight />
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default CarrinhoPage;