package com.nutridia.data

data class Food(val id: Int, val name: String, val symbol: String, val kcal: Double,
    val protein: Double, val carbs: Double, val fat: Double, val custom: Boolean = false)
data class MealItem(val food: Food, val grams: Double) {
    val kcal get() = food.kcal * grams / 100
    val protein get() = food.protein * grams / 100
    val carbs get() = food.carbs * grams / 100
    val fat get() = food.fat * grams / 100
}
data class Meal(val type: String, val symbol: String, val items: List<MealItem>) {
    val kcal get() = items.sumOf { it.kcal }
}
data class Comment(val author: String, val text: String, val time: String)
data class Post(val id: Int, val author: String, val group: String, val text: String,
    val time: String, val likes: Int, val comments: List<Comment> = emptyList(), val image: Boolean = false,
    val professional: Boolean = false, val liked: Boolean = false)
data class Group(val id: Int, val name: String, val symbol: String, val members: Int, val description: String, val joined: Boolean = false)
data class Challenge(val id: Int, val name: String, val symbol: String, val days: Int, val completed: Int,
    val participants: Int, val joined: Boolean, val description: String, val markedToday: Boolean = false)
data class ContentItem(val id: Int, val title: String, val symbol: String, val recipe: Boolean, val minutes: Int,
    val kcal: Int = 0, val tags: Set<String> = emptySet(), val body: String, val ingredients: List<String> = emptyList())
data class AppNotification(val id: Int, val title: String, val subtitle: String, val symbol: String,
    val today: Boolean, val target: String, val read: Boolean = false)

object DemoData {
    val restrictions = listOf("Sem lactose", "Sem glúten", "Vegetariano", "Vegano", "Diabético")
    val foods = listOf(
        Food(1, "Arroz branco cozido", "🍚", 130.0, 2.5, 28.0, .2),
        Food(2, "Feijão carioca", "🫘", 95.0, 4.8, 16.0, .5),
        Food(3, "Frango, peito, grelhado", "🍗", 165.0, 31.0, 0.0, 3.6),
        Food(4, "Frango, coxa, assada", "🍗", 215.0, 27.0, 0.0, 11.0),
        Food(5, "Frango, sobrecoxa, cozida", "🥘", 185.0, 25.0, 0.0, 8.0),
        Food(6, "Frango da vó", "👤", 240.0, 28.0, 8.0, 10.0, true),
        Food(7, "Pão integral, ovo, café", "☕", 320.0, 18.0, 38.0, 11.0),
        Food(8, "Maçã, iogurte", "🍎", 340.0, 12.0, 52.0, 9.0),
        Food(9, "Salada de vegetais", "🥗", 50.0, 2.0, 8.0, 1.0),
    )
    val meals = listOf(
        Meal("Café da manhã", "☕", listOf(MealItem(foods[6], 100.0))),
        Meal("Almoço", "🍽️", listOf(MealItem(foods[0], 150.0), MealItem(foods[1], 100.0), MealItem(foods[2], 170.0), MealItem(foods[8], 100.0))),
        Meal("Lanche", "🍎", listOf(MealItem(foods[7], 100.0))),
        Meal("Jantar", "🌙", emptyList()),
    )
    val comments = listOf(Comment("Carlos Dias", "Pasta de amendoim com banana resolve. Bate bem com café.", "há 20 h"),
        Comment("Dra. Beatriz", "Iogurte de coco ou de castanha também funcionam. Olhe o teor de açúcar no rótulo.", "há 18 h"))
    val posts = listOf(
        Post(1, "Carlos Dias", "Emagrecimento", "Fechei 30 dias seguidos registrando tudo. O que mudou mesmo foi deixar o almoço montado na noite anterior. 💪", "há 2 h", 42, comments, true),
        Post(2, "Dra. Beatriz", "Iniciantes", "Publiquei um artigo novo sobre leitura de rótulos. Qualquer dúvida, pergunta aqui embaixo.", "há 5 h", 67, comments, professional = true),
        Post(3, "Mariana Silva", "Iniciantes", "Alguém tem sugestão de lanche da tarde sem lactose? Enjoei da fruta pura...", "há 1 dia", 12, comments),
    )
    val groups = listOf(
        Group(1, "Iniciantes", "🌱", 1248, "Espaço para quem está nos primeiros 90 dias de reeducação alimentar. Pergunte sem medo."),
        Group(2, "Sem lactose", "🥛", 612, "Receitas e trocas para uma alimentação sem lactose."),
        Group(3, "Emagrecimento", "📉", 3407, "Apoio e hábitos sustentáveis para seu objetivo.", true),
        Group(4, "Veganismo", "🌿", 890, "Alimentação vegetal, receitas e apoio."),
        Group(5, "Diabetes tipo 2", "🩺", 1105, "Apoio à rotina alimentar com orientação profissional."),
    )
    val challenges = listOf(
        Challenge(1, "7 dias sem açúcar", "🍬", 7, 4, 342, true,
            "Sete dias sem açúcar de adição: refrigerante, doce, biscoito recheado e açúcar no café. Adoçante natural é permitido. Marque o dia toda noite."),
        Challenge(2, "2 litros por dia", "💧", 30, 12, 1087, true, "Registre sua hidratação e acompanhe sua constância por 30 dias."),
        Challenge(3, "Vegetal em toda refeição", "🥦", 14, 0, 98, false, "Inclua vegetais nas suas refeições por 14 dias."),
        Challenge(4, "Caminhada diária", "🚶", 21, 0, 125, false, "Encontre seu ritmo e registre uma caminhada diária."),
    )
    val contents = listOf(
        ContentItem(1, "Overnight oats sem lactose", "🥣", true, 10, 280, setOf("Sem lactose", "Lanche", "Vegetariano", "Vegano"),
            "Misture a aveia, a bebida vegetal e a chia num pote. Deixe na geladeira de um dia para o outro. Na hora de comer, acrescente a banana fatiada e a canela.",
            listOf("40 g de aveia em flocos", "150 ml de bebida vegetal de aveia", "1 colher de chia", "1/2 banana", "Canela a gosto")),
        ContentItem(2, "Como ler rótulos", "🏷️", false, 6, tags = setOf("Sem lactose"), body = "Comece pela lista de ingredientes e pelo tamanho da porção. Compare produtos usando a mesma quantidade. Observe os avisos de alergênicos e a presença de lactose e açúcares adicionados. Conteúdo demonstrativo; não substitui orientação profissional."),
        ContentItem(3, "Saladas que saciam", "🥗", true, 15, 320, setOf("Sem lactose", "Sem glúten", "Vegetariano", "Vegano"), "Combine as folhas, os legumes e a proteína. Tempere e sirva.", listOf("Folhas variadas", "Grão-de-bico cozido", "Tomate", "Azeite e limão")),
        ContentItem(4, "Vontade de doce", "🍫", false, 4, tags = setOf("Sem lactose"), body = "Observe os horários das refeições e procure identificar fome e vontade de comer. Planeje lanches e converse com um profissional para adaptar a rotina às suas necessidades. Conteúdo demonstrativo."),
        ContentItem(5, "Sopas de inverno", "🍲", true, 25, 210, setOf("Sem lactose", "Sem glúten", "Vegetariano", "Vegano"), "Cozinhe os legumes em água, tempere e sirva quente.", listOf("Abóbora", "Cenoura", "Cebola", "Água e temperos")),
    )
    val notifications = listOf(
        AppNotification(1, "Hora do almoço", "Registre o que você comeu · 12:30", "🍽️", true, "meal"),
        AppNotification(2, "Beba água", "Faltam 800 ml para a meta · 15:00", "💧", true, "water"),
        AppNotification(3, "Carlos curtiu sua publicação", "há 3 h", "❤️", true, "post", true),
        AppNotification(4, "Medalha conquistada", "7 dias seguidos registrando", "🏅", false, "profile", true),
        AppNotification(5, "Dra. Beatriz respondeu você", "No grupo Iniciantes", "💬", false, "post", true),
        AppNotification(6, "Desafio: faltam 2 dias", "7 dias sem açúcar", "🍬", false, "challenge", true),
    )
}
