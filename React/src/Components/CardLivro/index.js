import './style.css'

import { Link } from 'react-router-dom'

import { HiShoppingCart } from 'react-icons/hi2'

export default function Card(props){

    const { id, titulo, imagem, preco, autor, desconto } = props

    return(
        <article className="card" key={id}>
            <Link to={"/livro/" + id} >
                <img src={imagem} alt={`Capa de ${titulo}`} />
            </Link>

            <h3>
                {titulo}
                <span>by {autor}</span>
            </h3>

            <p className="preco">
                <span>{formatarPreco(preco)}</span>
                {formatarPreco(preco - (preco * desconto))}
            </p>

            <a href="/carrinho" className="btn">
                Adicionar ao carrinho
                <HiShoppingCart />
            </a>
        </article>
    )
}

function formatarPreco(preco){
    return("R$ " + preco.toFixed(2).replace('.', ','))
}