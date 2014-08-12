package tuttifrutti.answers

abstract class Answer(val label: String?) {

}

class Text(val value: String, label: String? = null, val children: List<Answer> = listOf()) : Answer(label)
class Flag(val value: Boolean, label: String? = null, val children: List<Answer> = listOf()) : Answer(label)

