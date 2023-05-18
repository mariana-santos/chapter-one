import React from 'react';
import { Link } from 'react-router-dom';

import Header from '../../Components/Header';

import './style.css'

import { useContext, useState, useEffect } from 'react';

import { CarrinhoContext } from '../../App';

import { formatarPreco } from '../../Assets/Utils';

const CarrinhoPage = () => {
  
  const { carrinho } = useContext(CarrinhoContext);
  const [total, setTotal] = useState(0)

  useEffect(() => {
    let total = 0

    carrinho.forEach((livro) => {
      total += ((livro.preco - livro.desconto) * livro.qtd)
    })

    setTotal(total)
  }, [])

  return (
    <>
      <Header />
      {/* <div className="page-content"></div> */}
      <div className="container">
        <div id='carrinho-container'>
        <h2>Meu Carrinho</h2>

        <div className="carrinho-itens">

          { carrinho.map((livro) => {
            return(
              <CarrinhoItem livro={livro} />
            )
          }) }

        </div>

        <div className="carrinho-total">
          <h4>Total:</h4>
          <p>{ formatarPreco(total) } </p>
        </div>

        <div className="carrinho-botoes">
          <Link to="/loja" className="carrinho-continuar-comprando">
            Continuar Comprando
          </Link>
          <button className="carrinho-finalizar-compra">Finalizar Compra</button>
        </div>
      </div>
      </div>
    </>
  );
};

function CarrinhoItem(props){

  const { livro } = props
  const [qtd, setQtd] = useState(1)
  const [subtotal, setSubtotal] = useState(livro.preco - livro.desconto)
  const { removerDoCarrinho } = useContext(CarrinhoContext);

  useEffect(() => {

    setSubtotal((livro.preco - livro.desconto) * qtd)

  }, [qtd])

  return(
    <div className="carrinho-item">
      <img src={livro.imagem} alt={`Capa de ${livro.titulo}`} />

      <div className="carrinho-item-info">
        <h3>{livro.titulo}</h3>
        <p>{ formatarPreco(livro.preco - livro.desconto) }</p>
      </div>

      <div className="carrinho-item-quantidade">
        <div className='btns-qtd'>
          <button className="diminuir-qtd"onClick={() => {
            if(qtd > 1) setQtd(qtd - 1)
            else removerDoCarrinho(livro.id)
          }}>-</button>
          <span>{qtd}</span>
          <button className="aumentar-qtd" onClick={() => { setQtd(qtd + 1)}}>+</button>
        </div>
        <p className='subtotal'>{ formatarPreco(subtotal) }</p>
      </div>
    </div>
  )
}

export default CarrinhoPage;
