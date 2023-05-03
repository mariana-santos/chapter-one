import { Link, NavLink } from 'react-router-dom'
import './style.css'

import categorias from '../../Data/categorias.json'

export default function Header(){
    return(
        <header>
            <div id='logo'>
                <Link to='/' > <h1>ChapterOne</h1></Link>
            </div>

            <nav>
                <ul>
                    <MenuItem path="/" label="início"/>
                    <MenuItem path="/loja" label="loja" />
                    <MenuItem path="/contato" label="contato" />
                    <MenuItem path="/carrinho" label="carrinho" />
                    {/* <MenuItem path="#" label="categorias" subItems={categorias} /> */}
                </ul>
            </nav>
        </header>
    )
}

function MenuItem(props){
    const { path, label, subItems } = props
    return(
        <li>
            <NavLink to={path}>{label}</NavLink>
            {/* { subItems && 
                subItems.map((item) => {
                    return(
                        <ul>
                            <li><NavLink to={path}>{item.nome}</NavLink></li>
                        </ul>
                    )
                }) 
            } */}
        </li>
    )
}