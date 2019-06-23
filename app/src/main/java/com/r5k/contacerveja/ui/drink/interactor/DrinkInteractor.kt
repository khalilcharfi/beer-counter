package com.r5k.contacerveja.ui.drink.interactor

import com.r5k.contacerveja.data.database.repository.drink.Drink
import com.r5k.contacerveja.data.database.repository.drink.DrinksRepository
import com.r5k.contacerveja.ui.base.BaseInteractor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject

class DrinkInteractor @Inject internal
        constructor(private val drinkRepoHelper : DrinksRepository)
    :BaseInteractor(),DrinkMVPInteractor{

    override suspend fun updateDrinkInDb(drink: Drink) = GlobalScope.async {
        drinkRepoHelper.updateDrink(drink)
    }

    override fun plusQntForDrink(drink: Drink): Drink {
        drink.qnt = drink.qnt + 1
        return drink
    }

    override fun negQntForDrink(drink: Drink): Drink? {
        if(isValidQuantity(drink)){
            drink.qnt = drink.qnt - 1
            return drink
        }
        return null
    }

    private fun isValidQuantity(drink: Drink): Boolean {
        return when{
            drink.qnt > 0 -> true
            else -> false
        }
    }

    override suspend fun deleteDrink(drink: Drink) = GlobalScope.async {
        drinkRepoHelper.deleteDrink(drink)
    }

    override suspend fun changeDrinkAmount(drink: Drink, amount: Int) = GlobalScope.async{
        drink.qnt = amount
        drinkRepoHelper.updateDrink(drink)
    }
}