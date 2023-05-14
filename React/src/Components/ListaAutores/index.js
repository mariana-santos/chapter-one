import Card from "../CardAutor"

export default function ListaAutores(props){

    const { autores } = props

    return(
        <section className="lista">
            { autores.map((autor) => {
                return(
                    <Card 
                        id={autor.id} 
                        nome={autor.nome} 
                        imagem={autor.imagem}
                    />
                )
            }) }
        </section>
    )
}