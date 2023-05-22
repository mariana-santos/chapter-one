import { Link, NavLink } from 'react-router-dom'
import './style.css'

import categorias from '../../Data/categorias.json'

import { useState, useEffect } from 'react'

export default function Header() {

    const [categorias, setCategorias] = useState([])

    useEffect(() => {

        fetch(`http://localhost:8000/categorias`)
            .then(resp => resp.json())
            .then(categorias => setCategorias(categorias))
            .catch(error => console.error(error))
    }, [])

    return (
        <header>
            <div id='logo'>
                <Link to='/' > <h1>ChapterOne</h1></Link>
            </div>

            <nav>
                <ul>
                    <MenuItem path="/" label="início" />
                    <MenuItem path="/loja" label="livros" />
                    <MenuItem path="/contato" label="contato" />
                    <MenuItem path="/carrinho" label="carrinho" />
                </ul>
            </nav>
        </header>
    )
}

function MenuItem(props) {
    const { path, label, subItems } = props

    const [isHovered, setIsHovered] = useState(false)

    return (
        <li
            onMouseEnter={() => setIsHovered(!isHovered)}
            onMouseLeave={() => setIsHovered(!isHovered)}
        >
            <NavLink to={path}>{label}</NavLink>
            {(subItems && isHovered) &&
                <ul className='sub-menu'>
                    {subItems.map((item) => {
                        return (
                            <li><NavLink to={`${path}?categoria=${item.id}`}>{item.nome.toLowerCase()}</NavLink></li>
                        )
                    })}
                </ul>
            }
        </li>
    )
}