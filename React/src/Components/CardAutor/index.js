import './style.css'

import { Link } from 'react-router-dom'

import { HiOutlineArrowNarrowRight } from 'react-icons/hi'

export default function CardAutor(props){

    const { id, nome, imagem } = props

    return(
        <article className="card card_autor" key={id}>
            <Link to={"/autor/" + id} >
                <img src={imagem} alt={`Imagem de ${nome}`} />
            </Link>

            <h3> {nome} </h3>

            <Link to={"/autor/" + id} className="btn"> Saiba mais <HiOutlineArrowNarrowRight /> </Link>
        </article>
    )
}