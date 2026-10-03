package com.example.lecano

object MascotaData {

    fun allPets(): MutableList<Mascota> = mutableListOf(
        Mascota(1, "Luna", "🐶", R.color.pet_pink, 5),
        Mascota(2, "Milo", "🐱", R.color.pet_blue, 3),
        Mascota(3, "Rocky", "🐕", R.color.pet_orange, 4),
        Mascota(4, "Nala", "🐈", R.color.pet_green, 2),
        Mascota(5, "Toby", "🐶", R.color.pet_purple, 5),
        Mascota(6, "Kira", "🐱", R.color.pet_teal, 1),
        Mascota(7, "Max", "🐕", R.color.pet_peach, 4),
        Mascota(8, "Coco", "🐩", R.color.pet_gray, 3)
    )

    fun favoritePets(): MutableList<Mascota> = mutableListOf(
        Mascota(101, "Luna", "🐶", R.color.pet_pink, 5),
        Mascota(102, "Rocky", "🐕", R.color.pet_orange, 4),
        Mascota(103, "Toby", "🐶", R.color.pet_purple, 5),
        Mascota(104, "Milo", "🐱", R.color.pet_blue, 3),
        Mascota(105, "Coco", "🐩", R.color.pet_gray, 3)
    )
}
