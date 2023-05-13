import './style.css'

import { Link } from 'react-router-dom'

export default function Card(props){

    const { id, titulo, id_categoria, imagem, preco } = props

    return(
        <article className="card" key={id}>
            <img src={imagem} alt={`Capa de ${titulo}`} />
            <h3>{titulo}</h3>
            <p className="preco">{formatarPreco(preco)}</p>
            {/* <Link to={{ pathname: '/livro', state: props }}>{titulo}</Link> */}
            <a href="/carrinho" className="btn">Adicionar ao carrinho</a>
        </article>
    )
}

function formatarPreco(preco){
    return("R$ " + preco.toFixed(2).replace('.', ','))
}