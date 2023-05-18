import './style.css'
import { Link } from 'react-router-dom'
import { HiShoppingCart } from 'react-icons/hi2'
import { useState, useEffect, useContext } from 'react'

import { CarrinhoContext } from '../../App'; 

import { formatarPreco } from '../../Assets/Utils'

export default function Card(props) {
  const { id, titulo, imagem, preco, desconto, autores_id } = props
  const [autores, setAutores] = useState([])

  const { adicionarAoCarrinho, carrinho } = useContext(CarrinhoContext);

  useEffect(() => {
    const fetchAutores = async () => {
      try {
        const array_autores = await Promise.all(
          autores_id.map(async (autor) => {
            const response = await fetch(`http://localhost:8000/autor/${autor.id}`)
            const data = await response.json()
            return data
          })
        )

        setAutores(array_autores)
      } catch (error) {
        console.error(error)
      }
    }

    fetchAutores()
  }, [])

  return (
    <article className="card" key={id}>
      <Link to={"/livro/" + id}>
        <img src={imagem} alt={`Capa de ${titulo}`} />
      </Link>

      <h3>
        {titulo}

        {autores.map((autor, index) => (
          <a className="autor" href={"/autor/" + autor.id} target="_blank">
            { index === 0 && 'by' } {autor.nome}
            { (autores.length > 1 && index < autores.length - 1) && ', '}
          </a>
        ))}
      </h3>

      <p className="preco">
        {desconto > 0 && <span>{formatarPreco(preco)}</span>}
        {formatarPreco(preco - desconto)}
      </p>

      <Link to="/carrinho" className="btn" onClick={() => {adicionarAoCarrinho({...props, qtd: 1})}}>
        Adicionar ao carrinho
        <HiShoppingCart />
      </Link>
    </article>
  )
}
