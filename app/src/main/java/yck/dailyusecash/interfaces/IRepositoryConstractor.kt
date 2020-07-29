package yck.dailyusecash.interfaces

interface IRepositoryConstractor {

    fun getRepository(key: String):String?
    fun setRepository(key: String, value: String)
    fun initRepository()

}