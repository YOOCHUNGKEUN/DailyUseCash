package yck.dailyusecash.data

data class EventData(
    var order:String,
    var position:Int,
    var appversion:String,
    var target_amount:String,
    var targetDateData:TargetDateData,
    var listAdapterData: ListAdapterData){
}