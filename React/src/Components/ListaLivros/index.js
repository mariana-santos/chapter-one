import Card from "../CardLivro"

import './style.css'

export default function ListaLivros(props){

    const { livros } = props

    return(
        <section className="lista">
            { livros.map((livro) => {
                return(
                    <Card 
                        id={livro.id} 
                        titulo={livro.titulo} 
                        preco={livro.preco} 
                        desconto={livro.desconto} 
                        imagem={livro.imagem} 
                        autor={livro.autor[0].nome}
                    />
                )
            }) }
        </section>
    )
}