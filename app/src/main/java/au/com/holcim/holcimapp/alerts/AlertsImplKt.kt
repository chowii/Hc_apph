package au.com.holcim.holcimapp.alerts

import com.google.gson.annotations.SerializedName

/**
 * Created by chowii on 26/05/17.
 */

class AlertsImplKt {

    @SerializedName("delivery_address")
    internal var deliveryAddress: String? = null

    @SerializedName("order_cancelation_reason_code")
    internal var orderCancelationCode: String? = null

    @SerializedName("additional_message")
    internal var additionalMessage: String? = null

    internal var status: String? = null
    internal var confirmed: Boolean? = null


}
