import './style.css'

import { Link } from 'react-router-dom'

import { HiShoppingCart } from 'react-icons/hi2'

import { useState, useEffect } from 'react'

export default function Card(props){

    const { id, titulo, imagem, preco, desconto, id_autor } = props

    const [autor, setAutor] = useState({})
    const [categorias, setCategorias] = useState([])

    useEffect(() => {
        fetch(`http://localhost:8000/autor/${id_autor}`)
        .then(resp => resp.json())
        .then(autor => setAutor(autor))
        .catch(error => console.error(error))
    }, [])

    return(
        <article className="card" key={id}>
            <Link to={"/livro/" + id} >
                <img src={imagem} alt={`Capa de ${titulo}`} />
            </Link>

            <h3>
                {titulo}
                <a className='autor'
                    href={'/autor/' + autor.id} 
                    target='_blank'>
                        by {autor.nome}
                </a>
            </h3>

            <p className="preco">
                <span>{formatarPreco(preco)}</span>
                {formatarPreco(preco - desconto)}
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