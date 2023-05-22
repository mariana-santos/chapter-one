export function formatarPreco(preco) {
    return "R$ " + preco.toFixed(2).replace(".", ",")
}