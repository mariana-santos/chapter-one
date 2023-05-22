import { useState, useContext, useEffect } from "react";

import { CarrinhoContext } from "../../App";

import { formatarPreco } from "../../Assets/Utils";

export default function CarrinhoItem(props){

    const { livro } = props
    const [qtd, setQtd] = useState(1)
    const [subtotal, setSubtotal] = useState(livro.preco - livro.desconto)
    const { removerDoCarrinho, atualizarLivro } = useContext(CarrinhoContext);
  
    useEffect(() => {
  
      setSubtotal((livro.preco - livro.desconto) * qtd)
      livro.qtd = qtd

      atualizarLivro(livro, livro.id)
  
    }, [qtd])
  
    return(
      <div className="carrinho-item">
        <img src={livro.imagem} alt={`Capa de ${livro.titulo}`} />
  
        <div className="carrinho-item-info">
          <h3>{livro.titulo}</h3>
          <p>Preço unitário: { formatarPreco(livro.preco - livro.desconto) }</p>
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
          <p className='subtotal'>
            <span>subtotal: </span>
            { formatarPreco(subtotal) }
          </p>
        </div>
      </div>
    )
  }