package pt.unl.fct.iadi.novaevents.service

import pt.unl.fct.iadi.novaevents.model.Club
import pt.unl.fct.iadi.novaevents.model.ClubCategory
import org.springframework.stereotype.Service

@Service
class ClubService {

    private val clubs = mutableListOf(
        Club(1, "Chess Club", "From beginner to advanced, our Chess Club welcomes all levels.", ClubCategory.ACADEMIC),
        Club(2, "Robotics Club", "The Robotics Club is the place to turn ideas into machines", ClubCategory.TECHNOLOGY),
        Club(3, "Photography Club", "We are a community of visual storytellers passionate about photography.", ClubCategory.ARTS),
        Club(4, "Hiking & Outdoors Club", "The Hiking & Outdoors Club organises regular excursions into nature.", ClubCategory.SPORTS),
        Club(5, "Film Society", "The Film Society screens a curated selection of films from around the world.", ClubCategory.CULTURAL)
    )

    fun getAll(): List<Club> = clubs

    fun getById(id: Long): Club =
        clubs.find { it.id == id } ?: throw NoSuchElementException("Club not found: $id")
}