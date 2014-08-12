package tuttifrutti.mapping

import tuttifrutti.answers.Answer

/**
 * Form -> Facts
 *
 * Traverse form, if a node has a mapping label copy node value into json node equal to mapping label,
 * then map json tree to facts.
 *
 * Q: How do we map multiple nodes into single node? Eg. Day, Month, Year to DDMMYYYY
 * Q: How do we map collections?
 *
 * Facts -> Form
 *
 * Map facts to JSON tree. Traverse form, if a node has a mapping label copy section from Json Tree to facts.
 *
 */

  val answers: List<Answer> = arrayListOf()
