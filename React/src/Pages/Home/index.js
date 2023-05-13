import Card from "../../Components/CardLivro"
import Header from "../../Components/Header"

import ListaLivros from "../../Components/ListaLivros"

import livros from '../../Data/livros.json'

export default function Home(){

    livros = livros.slice(0, 4)

    console.log(livros)

    return(
        <>
            <Header />
            <div className="container">
                <ListaLivros livros={livros} />
            </div>
            
        </>
    )
}