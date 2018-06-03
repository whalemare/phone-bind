/**
 * @since 2018
 * @author Anton Vlasov - whalemare
 */
fun main(args: Array<String>) {
    val phones = getPhones()

    val sources = listOf<Searchable>(
        TrueCaller("KUU55m0Z83")
    )

    phones.forEach { phone ->
        sources.forEach { source ->
            val userInfo = source.search(phone)
            println(userInfo)
        }
    }
}

fun getPhones(): List<String> {
    return listOf(
        "79133702249"
//        "79684923023"
    )
}
