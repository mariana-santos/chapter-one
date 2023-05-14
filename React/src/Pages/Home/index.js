import Card from "../../Components/CardLivro"
import Header from "../../Components/Header"

import ListaLivros from "../../Components/ListaLivros"

import img from '../../Assets/illustr_qs.svg'

import livros from '../../Data/livros.json'
import autores from '../../Data/autores.json'

import { BsChevronCompactDown } from 'react-icons/bs'

import './style.css'
import ListaAutores from "../../Components/ListaAutores"

export default function Home(){

    livros = livros.slice(0, 4)

    return(
        <>
            <Header />
            <div className="container">

                <Sobre />

                <a id="saiba_mais" href="#livros">
                    Deslize para ver mais
                    <BsChevronCompactDown />
                </a>

                <h2 id="livros"> Mais populares </h2>
                <ListaLivros livros={livros} />

                <h2> Nossos autores </h2>
                <ListaAutores autores={autores} />
            </div>
        </>
    )
}

function Sobre(){
    return(
        <section id="sobre">
            <div className="coluna img">
                <img src={img} />
            </div>

            <div className="coluna">
                <h2>Quem somos?</h2>
                <p>Chapter One é uma livraria encantadora fundada por <strong>Kaue Caponero Figueiredo</strong> e <strong>Mariana Santos Fernandes de Sousa</strong> em 2023. Desde então, nossa livraria vem crescendo e se destacando cada vez mais no mercado. </p>

                <p>Conhecida por seu acervo diversificado de livros de todos os gêneros imagináveis, desde clássicos literários até as obras mais recentes de autores renomados, temos preços acessíveis e <strong>descontos de até 40%!</strong> </p>
            </div>
        </section>
    )
}